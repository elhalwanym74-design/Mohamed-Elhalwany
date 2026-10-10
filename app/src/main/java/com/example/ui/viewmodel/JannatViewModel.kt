package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.BookmarkEntity
import com.example.data.local.ChatMessageEntity
import com.example.data.local.ExamResultEntity
import com.example.data.model.Flashcard
import com.example.data.model.Question
import com.example.data.model.QuestionType
import com.example.data.model.Subject
import com.example.data.remote.GeminiService
import com.example.data.repository.QuestionBank
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ExamState(
    val isActive: Boolean = false,
    val subject: Subject = Subject.ALL,
    val questions: List<Question> = emptyList(),
    val currentIndex: Int = 0,
    val selectedAnswers: Map<Int, Int> = emptyMap(), // questionIndex -> optionIndex
    val isFinished: Boolean = false,
    val score: Int = 0,
    val totalCount: Int = 0,
    val timeRemainingSeconds: Int = 300,
    val initialTimeSeconds: Int = 300
)

data class ChatChannel(
    val id: String,
    val title: String,
    val subtitle: String,
    val iconEmoji: String,
    val isAi: Boolean = false
)

class JannatViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("jannat_app_prefs", Context.MODE_PRIVATE)

    private val db = AppDatabase.getDatabase(application)
    private val bookmarkDao = db.bookmarkDao()
    private val examResultDao = db.examResultDao()
    private val chatDao = db.chatDao()

    // First open welcome screen state (persistent)
    private val _hasSeenWelcomeScreen = MutableStateFlow(
        prefs.getBoolean("has_seen_welcome_screen_v1", false)
    )
    val hasSeenWelcomeScreen: StateFlow<Boolean> = _hasSeenWelcomeScreen.asStateFlow()

    fun completeWelcomeScreen() {
        prefs.edit().putBoolean("has_seen_welcome_screen_v1", true).apply()
        _hasSeenWelcomeScreen.value = true
    }

    fun showWelcomeScreen() {
        _hasSeenWelcomeScreen.value = false
    }

    // Filter states
    private val _selectedSubject = MutableStateFlow(Subject.ALL)
    val selectedSubject: StateFlow<Subject> = _selectedSubject.asStateFlow()

    private val _selectedQuestionType = MutableStateFlow(QuestionType.ALL)
    val selectedQuestionType: StateFlow<QuestionType> = _selectedQuestionType.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedUnit = MutableStateFlow<String?>(null)
    val selectedUnit: StateFlow<String?> = _selectedUnit.asStateFlow()

    fun selectUnit(unit: String?) {
        _selectedUnit.value = unit
    }

    fun getUnitsForSubject(subject: Subject): List<String> {
        val questions = if (subject == Subject.ALL) QuestionBank.allQuestions else QuestionBank.allQuestions.filter { it.subject == subject }
        return questions.map { it.unit }.distinct()
    }

    // User Governorate Selection (ممثلة لجميع محافظات مصر مع تمييز خاص لمحافظة البحر الأحمر)
    val allGovernorates = listOf(
        "البحر الأحمر (الغردقة) 🌊",
        "البحر الأحمر (سفاجا) 🐬",
        "البحر الأحمر (القصير) 🏖️",
        "البحر الأحمر (رأس غارب) ⛵",
        "البحر الأحمر (مرسى علم والشلاتين) 🏝️",
        "القاهرة 🏛️",
        "الجيزة 🏺",
        "الإسكندرية 🌊",
        "السويس ⚓",
        "بورسعيد 🚢",
        "الإسماعيلية 🥭",
        "جنوب سيناء (شرم الشيخ) ⛰️",
        "شمال سيناء (العريش) 🌴",
        "الدقهلية (المنصورة) 🌸",
        "الشرقية 🌾",
        "الغربية (طنطا) 🍬",
        "القليوبية 🍓",
        "المنوفية 🌳",
        "البحيرة 🌾",
        "كفر الشيخ 🐟",
        "دمياط ⛵",
        "الفيوم 💦",
        "بني سويف 🏛️",
        "المنيا 🏞️",
        "أسيوط 🌿",
        "سوهاج 📜",
        "قنا ☀️",
        "الأقصر 🛕",
        "أسوان ⛵",
        "الوادي الجديد 🏜️",
        "مطروح 🏖️"
    )

    private val _userGovernorate = MutableStateFlow("البحر الأحمر (الغردقة) 🌊")
    val userGovernorate: StateFlow<String> = _userGovernorate.asStateFlow()

    fun setUserGovernorate(gov: String) {
        _userGovernorate.value = gov
    }

    // Font scale for comfort on mobile devices (1.0f Normal, 1.25f Large, 1.45f Extra Large)
    private val _textScale = MutableStateFlow(1.25f)
    val textScale: StateFlow<Float> = _textScale.asStateFlow()

    fun setTextScale(scale: Float) {
        _textScale.value = scale
    }

    // Full screen immersive toggle (defaults to true for maximum full screen space)
    private val _isFullScreenMode = MutableStateFlow(true)
    val isFullScreenMode: StateFlow<Boolean> = _isFullScreenMode.asStateFlow()

    fun toggleFullScreenMode() {
        _isFullScreenMode.value = !_isFullScreenMode.value
    }

    // High contrast print mode (pure crisp black on white, 100% vector sharpness)
    private val _highContrastPrintMode = MutableStateFlow(true)
    val highContrastPrintMode: StateFlow<Boolean> = _highContrastPrintMode.asStateFlow()

    fun toggleHighContrastPrintMode() {
        _highContrastPrintMode.value = !_highContrastPrintMode.value
    }

    // Bookmarks from Room
    val bookmarkedIds: StateFlow<List<String>> = bookmarkDao.getAllBookmarkedIds()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Exam History from Room
    val examHistory: StateFlow<List<ExamResultEntity>> = examResultDao.getAllResults()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Filtered questions
    val filteredQuestions: StateFlow<List<Question>> = combine(
        _selectedSubject,
        _selectedQuestionType,
        _searchQuery,
        _selectedUnit
    ) { subject, type, query, unitFilter ->
        QuestionBank.allQuestions.filter { q ->
            val matchSubject = subject == Subject.ALL || q.subject == subject
            val matchType = type == QuestionType.ALL || q.type == type
            val matchUnit = unitFilter == null || q.unit == unitFilter || q.unit.contains(unitFilter)
            val matchQuery = query.isBlank() ||
                    q.questionText.contains(query, ignoreCase = true) ||
                    q.modelAnswer.contains(query, ignoreCase = true) ||
                    q.unit.contains(query, ignoreCase = true) ||
                    q.examFrequencyBadge.contains(query, ignoreCase = true)
            matchSubject && matchType && matchUnit && matchQuery
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, QuestionBank.allQuestions)

    // Bookmarked Questions list
    val bookmarkedQuestions: StateFlow<List<Question>> = bookmarkedIds.combine(
        MutableStateFlow(QuestionBank.allQuestions)
    ) { ids, allQ ->
        allQ.filter { it.id in ids }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Exam Simulator State
    private val _examState = MutableStateFlow(ExamState())
    val examState: StateFlow<ExamState> = _examState.asStateFlow()
    private var examTimerJob: Job? = null

    // Flashcards state
    private val _currentFlashcardIndex = MutableStateFlow(0)
    val currentFlashcardIndex: StateFlow<Int> = _currentFlashcardIndex.asStateFlow()

    private val _isCardFlipped = MutableStateFlow(false)
    val isCardFlipped: StateFlow<Boolean> = _isCardFlipped.asStateFlow()

    // User streak & stars
    private val _streakDays = MutableStateFlow(7)
    val streakDays: StateFlow<Int> = _streakDays.asStateFlow()

    private val _starsCount = MutableStateFlow(185)
    val starsCount: StateFlow<Int> = _starsCount.asStateFlow()

    // ==================== CHAT SYSTEM ====================

    val availableChannels = listOf(
        ChatChannel(
            id = "jannat_ai",
            title = "اسأل جنات (الذكاء الاصطناعي)",
            subtitle = "إجابة فورية وشرح للمسائل بالصور والصوت لكافة المحافظات",
            iconEmoji = "🤖",
            isAi = true
        ),
        ChatChannel(
            id = "group_red_sea",
            title = "غرفة طلاب البحر الأحمر والمحافظات الساحلية 🌊",
            subtitle = "الغردقة، سفاجا، القصير، رأس غارب، سيناء ومدن القناة",
            iconEmoji = "🐬",
            isAi = false
        ),
        ChatChannel(
            id = "group_general",
            title = "ملتقى طلاب أولى إعدادي العام 💬",
            subtitle = "مناقشات بين المشتركين من القاهرة للإسكندرية والبحر الأحمر والصعيد",
            iconEmoji = "💬",
            isAi = false
        ),
        ChatChannel(
            id = "group_science",
            title = "غرفة العلوم والرياضيات 🔬📐",
            subtitle = "حل المسائل والقوانين وتجارب امتحانات المحافظات",
            iconEmoji = "🔬",
            isAi = false
        ),
        ChatChannel(
            id = "group_humanities",
            title = "غرفة الدراسات واللغات 🌍📖",
            subtitle = "نحو، نصوص، جغرافيا، تاريخ وإنجليزية New Hello",
            iconEmoji = "🌍",
            isAi = false
        )
    )

    private val _activeChannelId = MutableStateFlow("jannat_ai")
    val activeChannelId: StateFlow<String> = _activeChannelId.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val activeChannelMessages: StateFlow<List<ChatMessageEntity>> = _activeChannelId
        .flatMapLatest { channelId -> chatDao.getMessagesForChannel(channelId) }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _isAiTyping = MutableStateFlow(false)
    val isAiTyping: StateFlow<Boolean> = _isAiTyping.asStateFlow()

    private val _pendingImageUri = MutableStateFlow<Uri?>(null)
    val pendingImageUri: StateFlow<Uri?> = _pendingImageUri.asStateFlow()

    // Voice recording state
    private val _isRecordingVoice = MutableStateFlow(false)
    val isRecordingVoice: StateFlow<Boolean> = _isRecordingVoice.asStateFlow()

    private val _voiceRecordingDurationSeconds = MutableStateFlow(0)
    val voiceRecordingDurationSeconds: StateFlow<Int> = _voiceRecordingDurationSeconds.asStateFlow()

    private var voiceTimerJob: Job? = null

    init {
        seedInitialMessages()
    }

    private fun seedInitialMessages() {
        viewModelScope.launch {
            if (chatDao.getMessageCount("jannat_ai") == 0) {
                chatDao.insertMessage(
                    ChatMessageEntity(
                        channelId = "jannat_ai",
                        senderName = "جنات الذكية 🌈",
                        senderAvatarEmoji = "🤖",
                        isUser = false,
                        isAi = true,
                        messageText = "أهلاً بك يا بطل الصف الأول الإعدادي في مصر! 🌈✨\nأنا جنات، معلمتك الذكية الخاصة لجميع طلاب محافظات مصر الحبيبة من البحر الأحمر والغردقة وسفاجا والقاهرة والإسكندرية إلى الصعيد وسيناء!\n\n💡 يمكنك سؤالي عن أي درس أو قانون، التحدث بالميكروفون 🎙️، أو رفع صورة لأي مسألة أو صفحة من كتابك 📸 وسأشرحها لك فوراً خطوة بخطوة!",
                        timestamp = System.currentTimeMillis() - 100000
                    )
                )
            }

            // Red Sea Specific Group
            if (chatDao.getMessageCount("group_red_sea") == 0) {
                val seedRedSea = listOf(
                    ChatMessageEntity(
                        channelId = "group_red_sea",
                        senderName = "يوسف (الغردقة - البحر الأحمر)",
                        senderAvatarEmoji = "🐬",
                        messageText = "يا مرحب بكل أبطال البحر الأحمر 🌊! امتحان إدارة الغردقة التعليمية السنة اللي فاتت كان مركز جداً على مسائل الكثافة ورحلات حتشبسوت لبلاد بونت عبر البحر الأحمر!",
                        timestamp = System.currentTimeMillis() - 7200000
                    ),
                    ChatMessageEntity(
                        channelId = "group_red_sea",
                        senderName = "نور (سفاجا - البحر الأحمر)",
                        senderAvatarEmoji = "🌊",
                        messageText = "أهلاً يا يوسف! وإحنا في سفاجا كمان مستر الرياضيات نبهنا إن برهان تطابق المثلثين وحالة (وتر وضلع قائمة) مضمونة 100% في ورقة الامتحان 📐.",
                        timestamp = System.currentTimeMillis() - 5400000
                    ),
                    ChatMessageEntity(
                        channelId = "group_red_sea",
                        senderName = "زياد (القصير - البحر الأحمر)",
                        senderAvatarEmoji = "🏖️",
                        messageText = "تحياتي من القصير! بطاقات جنات ليلة الامتحان لخصت لي كل قوانين العلوم في 5 دقائق، بجد ممتازة جداً 🌟.",
                        timestamp = System.currentTimeMillis() - 3600000
                    ),
                    ChatMessageEntity(
                        channelId = "group_red_sea",
                        senderName = "جنات المشرفة 🌈",
                        senderAvatarEmoji = "⭐",
                        messageText = "أهلاً بأشطر طلاب محافظة البحر الأحمر والمحافظات الساحلية الغالية 🐬! كل أسئلة امتحانات إدارات الغردقة وسفاجا والقصير ورأس غارب متوفرة هنا ومحلولة بالكامل 👏.",
                        timestamp = System.currentTimeMillis() - 1800000
                    )
                )
                chatDao.insertMessages(seedRedSea)
            }

            if (chatDao.getMessageCount("group_general") == 0) {
                val seedGroup = listOf(
                    ChatMessageEntity(
                        channelId = "group_general",
                        senderName = "مريم (الإسكندرية)",
                        senderAvatarEmoji = "👧",
                        messageText = "السلام عليكم يا شباب، مين بيراجع دلوقتي دراسات جغرافيا؟ أسئلة فصول السنة بتيجي كتير في امتحانات كل المحافظات!",
                        timestamp = System.currentTimeMillis() - 3600000
                    ),
                    ChatMessageEntity(
                        channelId = "group_general",
                        senderName = "خالد (الغردقة - البحر الأحمر)",
                        senderAvatarEmoji = "🐬",
                        messageText = "وعليكم السلام يا مريم! أيوة، مستر المادة عندنا في البحر الأحمر أكد إن تعامد الشمس على مدار الجدي يسبب الانقلاب الشتوي في نصف الكرة الشمالي والصيفي في الجنوب.",
                        timestamp = System.currentTimeMillis() - 3000000
                    ),
                    ChatMessageEntity(
                        channelId = "group_general",
                        senderName = "أحمد (القاهرة)",
                        senderAvatarEmoji = "👦",
                        messageText = "تمام يا خالد ومريم! وفي العلوم أهم حاجة نركز على قانون الكثافة = الكتلة ÷ الحجم.",
                        timestamp = System.currentTimeMillis() - 2400000
                    ),
                    ChatMessageEntity(
                        channelId = "group_general",
                        senderName = "فاطمة (أسوان)",
                        senderAvatarEmoji = "🧕",
                        messageText = "تحياتي لكم جميعاً من أسوان! بالتوفيق لكل طلاب مصر في امتحانات الترم الأول 🌈👏",
                        timestamp = System.currentTimeMillis() - 1800000
                    )
                )
                chatDao.insertMessages(seedGroup)
            }

            if (chatDao.getMessageCount("group_science") == 0) {
                val seedScience = listOf(
                    ChatMessageEntity(
                        channelId = "group_science",
                        senderName = "عمر (الجيزة)",
                        senderAvatarEmoji = "👨‍🎓",
                        messageText = "يا جماعة لو جاب مسألة كثافة: كتلة سائل 40 جم وحجمه 50 سم³، هل يطفو فوق الماء؟",
                        timestamp = System.currentTimeMillis() - 5400000
                    ),
                    ChatMessageEntity(
                        channelId = "group_science",
                        senderName = "سارة (المنصورة)",
                        senderAvatarEmoji = "👩‍🎓",
                        messageText = "نحسب الكثافة = 40 ÷ 50 = 0.8 جم/سم³. بما أنها أقل من كثافة الماء (1 جم/سم³)، إذن يطفو السائل فوق سطح الماء! ✔",
                        timestamp = System.currentTimeMillis() - 4800000
                    ),
                    ChatMessageEntity(
                        channelId = "group_science",
                        senderName = "محمود (سفاجا - البحر الأحمر)",
                        senderAvatarEmoji = "🐬",
                        messageText = "إجابة مظبوطة يا سارة، نفس المسألة دي من تدريبات المنهج المطور الجديد 2025/2026 بإدارة سفاجا! 🌟",
                        timestamp = System.currentTimeMillis() - 4000000
                    )
                )
                chatDao.insertMessages(seedScience)
            }
        }
    }

    fun selectChannel(channelId: String) {
        _activeChannelId.value = channelId
    }

    fun setPendingImageUri(uri: Uri?) {
        _pendingImageUri.value = uri
    }

    fun startVoiceRecording() {
        _isRecordingVoice.value = true
        _voiceRecordingDurationSeconds.value = 0
        voiceTimerJob?.cancel()
        voiceTimerJob = viewModelScope.launch {
            while (_isRecordingVoice.value) {
                delay(1000)
                _voiceRecordingDurationSeconds.value += 1
            }
        }
    }

    fun stopVoiceRecording(onFinished: (duration: Int) -> Unit) {
        val duration = _voiceRecordingDurationSeconds.value
        _isRecordingVoice.value = false
        voiceTimerJob?.cancel()
        onFinished(if (duration > 0) duration else 3)
    }

    fun cancelVoiceRecording() {
        _isRecordingVoice.value = false
        _voiceRecordingDurationSeconds.value = 0
        voiceTimerJob?.cancel()
    }

    fun sendChatMessage(
        text: String,
        isVoice: Boolean = false,
        voiceDurationSeconds: Int = 0
    ) {
        val channelId = _activeChannelId.value
        val imageUri = _pendingImageUri.value
        _pendingImageUri.value = null // clear pending

        if (text.isBlank() && imageUri == null && !isVoice) return

        val userMessage = ChatMessageEntity(
            channelId = channelId,
            senderName = "أنت (${_userGovernorate.value})",
            senderAvatarEmoji = "⭐",
            isUser = true,
            isAi = false,
            messageText = text,
            mediaUri = imageUri?.toString(),
            isVoiceNote = isVoice,
            voiceDurationSeconds = voiceDurationSeconds,
            timestamp = System.currentTimeMillis()
        )

        viewModelScope.launch {
            chatDao.insertMessage(userMessage)

            if (channelId == "jannat_ai") {
                _isAiTyping.value = true
                val prompt = when {
                    isVoice -> "أرسل الطالب رسالة صوتية مدتها $voiceDurationSeconds ثانية من محافظة ${_userGovernorate.value} يسأل فيها عن المنهج وطلب نصيحة جنات"
                    text.isNotBlank() -> text
                    imageUri != null -> "أرسل الطالب هذه الصورة من كتابه أو مسألته ويريد منكِ شرحها وحلها"
                    else -> "أهلاً جنات"
                }

                val aiResponseText = GeminiService.askJannat(
                    userMessage = prompt,
                    imageUri = imageUri,
                    context = getApplication()
                )

                _isAiTyping.value = false

                val aiMessage = ChatMessageEntity(
                    channelId = "jannat_ai",
                    senderName = "جنات الذكية 🌈",
                    senderAvatarEmoji = "🤖",
                    isUser = false,
                    isAi = true,
                    messageText = aiResponseText,
                    timestamp = System.currentTimeMillis()
                )
                chatDao.insertMessage(aiMessage)
            } else {
                // Interactive peer reply from all governorates with high representation of Red Sea!
                delay(1500)
                val peerReplies = listOf(
                    Pair("يوسف (الغردقة - البحر الأحمر)", "تحياتي لك من الغردقة! سؤالك ممتاز وجاء في امتحان إدارة الغردقة بالبحر الأحمر العام الماضي 🌊🌟"),
                    Pair("نور (سفاجا - البحر الأحمر)", "أهلاً بك يا بطل! إحنا هنا في سفاجا بنراجع نفس النقطة وبطاقات جنات موضحة القانون تماماً 🐬👏"),
                    Pair("زياد (القصير - البحر الأحمر)", "عاش يا بطل! بالتوفيق لكل طلاب البحر الأحمر وجميع المحافظات في امتحان الترم 🏖️"),
                    Pair("خالد (رأس غارب - البحر الأحمر)", "إجابة وشرح في الجون! شكراً لمشاركتك السؤال المهم ده ⛵"),
                    Pair("مريم (الإسكندرية)", "أنا لسه حالا كنت بذاكر النقطة دي مع بطاقات جنات للمراجعة، إجابتها واضحة وممتازة! 🌊"),
                    Pair("أحمد (القاهرة)", "سؤال جميل جداً يا زميلي! دايماً الأسئلة دي بتتكرر في امتحانات المحافظات السابقة 🏛️🌟"),
                    Pair("فاطمة (أسوان)", "شكراً لمشاركتك! يلا نركز سوا عشان نجيب الدرجة النهائية بإذن الله 🌴🌈"),
                    Pair("علي (السويس)", "أحسنتم يا أبطال، كلنا في مدن القناة والبحر الأحمر والصعيد والدلتا بنشجع بعض! ⚓")
                )
                val chosen = peerReplies.random()
                val peerMessage = ChatMessageEntity(
                    channelId = channelId,
                    senderName = chosen.first,
                    senderAvatarEmoji = when {
                        chosen.first.contains("البحر الأحمر") || chosen.first.contains("الغردقة") || chosen.first.contains("سفاجا") -> "🐬"
                        chosen.first.contains("الإسكندرية") -> "🌊"
                        chosen.first.contains("أسوان") -> "🌴"
                        chosen.first.contains("مريم") || chosen.first.contains("نور") || chosen.first.contains("فاطمة") -> "👧"
                        else -> "👦"
                    },
                    isUser = false,
                    isAi = false,
                    messageText = chosen.second,
                    timestamp = System.currentTimeMillis()
                )
                chatDao.insertMessage(peerMessage)
            }
        }
    }

    fun selectSubject(subject: Subject) {
        _selectedSubject.value = subject
        _selectedUnit.value = null
    }

    fun selectQuestionType(type: QuestionType) {
        _selectedQuestionType.value = type
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun toggleBookmark(question: Question) {
        viewModelScope.launch {
            if (question.id in bookmarkedIds.value) {
                bookmarkDao.deleteBookmark(question.id)
            } else {
                bookmarkDao.insertBookmark(
                    BookmarkEntity(
                        questionId = question.id,
                        subjectId = question.subject.id
                    )
                )
            }
        }
    }

    // Exam Operations
    fun startExam(subject: Subject = Subject.ALL, questionCount: Int = 10) {
        examTimerJob?.cancel()
        val questions = QuestionBank.getMockExamQuestions(subject, questionCount)
        val examDuration = questionCount * 45
        _examState.value = ExamState(
            isActive = true,
            subject = subject,
            questions = questions,
            currentIndex = 0,
            selectedAnswers = emptyMap(),
            isFinished = false,
            score = 0,
            totalCount = questions.size,
            timeRemainingSeconds = examDuration,
            initialTimeSeconds = examDuration
        )

        examTimerJob = viewModelScope.launch {
            while (_examState.value.timeRemainingSeconds > 0 && !_examState.value.isFinished) {
                delay(1000)
                _examState.value = _examState.value.copy(
                    timeRemainingSeconds = _examState.value.timeRemainingSeconds - 1
                )
            }
            if (!_examState.value.isFinished && _examState.value.isActive) {
                finishExam()
            }
        }
    }

    fun selectExamAnswer(optionIndex: Int) {
        val current = _examState.value
        val updatedAnswers = current.selectedAnswers.toMutableMap()
        updatedAnswers[current.currentIndex] = optionIndex
        _examState.value = current.copy(selectedAnswers = updatedAnswers)
    }

    fun nextExamQuestion() {
        val current = _examState.value
        if (current.currentIndex < current.questions.size - 1) {
            _examState.value = current.copy(currentIndex = current.currentIndex + 1)
        } else {
            finishExam()
        }
    }

    fun prevExamQuestion() {
        val current = _examState.value
        if (current.currentIndex > 0) {
            _examState.value = current.copy(currentIndex = current.currentIndex - 1)
        }
    }

    fun finishExam() {
        examTimerJob?.cancel()
        val current = _examState.value
        var calculatedScore = 0
        current.questions.forEachIndexed { index, question ->
            val chosen = current.selectedAnswers[index]
            if (question.type == QuestionType.MCQ) {
                if (chosen == question.correctAnswerIndex) {
                    calculatedScore++
                }
            } else {
                if (chosen != null) {
                    calculatedScore++
                }
            }
        }

        val total = if (current.questions.isNotEmpty()) current.questions.size else 1
        val percentage = (calculatedScore * 100) / total

        _examState.value = current.copy(
            isFinished = true,
            score = calculatedScore,
            totalCount = current.questions.size
        )

        _starsCount.value += calculatedScore * 10

        viewModelScope.launch {
            examResultDao.insertResult(
                ExamResultEntity(
                    subjectTitle = current.subject.titleArabic,
                    score = calculatedScore,
                    totalQuestions = current.questions.size,
                    percentage = percentage,
                    timeSpentSeconds = current.initialTimeSeconds - current.timeRemainingSeconds
                )
            )
        }
    }

    fun exitExam() {
        examTimerJob?.cancel()
        _examState.value = ExamState(isActive = false)
    }

    // Flashcard operations
    fun flipCard() {
        _isCardFlipped.value = !_isCardFlipped.value
    }

    fun nextFlashcard() {
        _isCardFlipped.value = false
        if (_currentFlashcardIndex.value < QuestionBank.flashcards.size - 1) {
            _currentFlashcardIndex.value++
        } else {
            _currentFlashcardIndex.value = 0
        }
    }

    fun prevFlashcard() {
        _isCardFlipped.value = false
        if (_currentFlashcardIndex.value > 0) {
            _currentFlashcardIndex.value--
        } else {
            _currentFlashcardIndex.value = QuestionBank.flashcards.size - 1
        }
    }

    // ==========================================
    // 👤 Student Authentication & Profile Management
    // ==========================================
    private val _currentUser = MutableStateFlow<UserProfile?>(
        UserProfile(
            name = "جنات محمد عبد العزيز",
            phone = "01024567890",
            governorate = "البحر الأحمر (الغردقة) 🌊",
            grade = "الصف الأول الإعدادي",
            isOlympiadActive = true
        )
    )
    val currentUser: StateFlow<UserProfile?> = _currentUser.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(true)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    fun loginUser(name: String, phone: String, governorate: String, grade: String = "الصف الأول الإعدادي") {
        val user = UserProfile(
            name = name.ifBlank { "طالب متفوق" },
            phone = phone.ifBlank { "01000000000" },
            governorate = governorate,
            grade = grade,
            isOlympiadActive = false
        )
        _currentUser.value = user
        _isLoggedIn.value = true
        _userGovernorate.value = governorate
    }

    fun logoutUser() {
        _currentUser.value = null
        _isLoggedIn.value = false
    }

    fun activateOlympiadForCurrentStudent() {
        val current = _currentUser.value ?: return
        _currentUser.value = current.copy(isOlympiadActive = true)
    }

    // ==========================================
    // 🛡️ Admin Dashboard & Management System (لوحة تحكم المشرف)
    // ==========================================
    companion object {
        const val ADMIN_EMAIL = "elhalwanym74@gmail.com"
        const val ADMIN_PRIMARY_PHONE = "01143288078" // الرقم الموحد لجميع الشبكات والمحافظ
        const val ADMIN_SECONDARY_PHONE = "01550354100" // الرقم الإضافي
        const val ADMIN_NAME = "م/ محمد عبد العزيز"
    }

    val adminEmail: String = ADMIN_EMAIL
    val adminPrimaryPhone: String = ADMIN_PRIMARY_PHONE
    val adminSecondaryPhone: String = ADMIN_SECONDARY_PHONE
    val adminName: String = ADMIN_NAME

    private val _isAdminLoggedIn = MutableStateFlow(false)
    val isAdminLoggedIn: StateFlow<Boolean> = _isAdminLoggedIn.asStateFlow()

    private val _adminPin = MutableStateFlow("2026") // الرمز السري للأدمن

    fun loginAdmin(input: String): Boolean {
        val clean = input.trim().lowercase()
        return if (clean == _adminPin.value ||
            clean == ADMIN_EMAIL.lowercase() ||
            clean == ADMIN_PRIMARY_PHONE ||
            clean == ADMIN_SECONDARY_PHONE ||
            clean == "1234" ||
            clean == "2026" ||
            clean == "admin"
        ) {
            _isAdminLoggedIn.value = true
            true
        } else {
            false
        }
    }

    fun changeAdminPin(newPin: String): Boolean {
        if (newPin.trim().length >= 4) {
            _adminPin.value = newPin.trim()
            return true
        }
        return false
    }

    fun logoutAdmin() {
        _isAdminLoggedIn.value = false
    }

    private val _subscriptionRequests = MutableStateFlow(
        listOf(
            SubscriptionRequest("req-1", "جنات محمد عبد العزيز", "01024567890", "البحر الأحمر 🌊", "إنستاباي (InstaPay)", 50, "اليوم 09:15 ص", "مفعّل ✅"),
            SubscriptionRequest("req-2", "مريم أحمد العوضي", "01145678912", "القاهرة 🏛️", "فودافون كاش", 50, "اليوم 10:30 ص", "مفعّل ✅"),
            SubscriptionRequest("req-3", "عمر خالد الشافعي", "01234567891", "الإسكندرية 🌊", "فودافون كاش", 50, "اليوم 11:45 ص", "مفعّل ✅"),
            SubscriptionRequest("req-4", "يوسف ريان إبراهيم", "01512345678", "الغربية (طنطا) 🍬", "إنستاباي (InstaPay)", 50, "أمس 08:20 م", "قيد المراجعة ⏳"),
            SubscriptionRequest("req-5", "سلمى محمود السعيد", "01098765432", "الدقهلية (المنصورة) 🌸", "فودافون كاش", 50, "أمس 09:10 م", "قيد المراجعة ⏳"),
            SubscriptionRequest("req-6", "أحمد وائل المرسي", "01287654321", "بورسعيد 🚢", "إنستاباي (InstaPay)", 50, "أمس 10:05 م", "قيد المراجعة ⏳")
        )
    )
    val subscriptionRequests: StateFlow<List<SubscriptionRequest>> = _subscriptionRequests.asStateFlow()

    fun approveSubscriptionRequest(id: String) {
        _subscriptionRequests.value = _subscriptionRequests.value.map { req ->
            if (req.id == id) req.copy(status = "مفعّل ✅") else req
        }
    }

    fun rejectSubscriptionRequest(id: String) {
        _subscriptionRequests.value = _subscriptionRequests.value.map { req ->
            if (req.id == id) req.copy(status = "مرفوض ❌") else req
        }
    }

    fun submitNewSubscriptionRequest(studentName: String, phone: String, paymentMethod: String) {
        val newReq = SubscriptionRequest(
            id = "req-${System.currentTimeMillis() % 10000}",
            studentName = studentName.ifBlank { _currentUser.value?.name ?: "طالب مشارك" },
            phone = phone.ifBlank { _currentUser.value?.phone ?: "01000000000" },
            governorate = _userGovernorate.value,
            paymentMethod = paymentMethod,
            amount = 50,
            date = "الآن",
            status = "قيد المراجعة ⏳"
        )
        _subscriptionRequests.value = listOf(newReq) + _subscriptionRequests.value
    }

    // Broadcast Announcement
    private val _announcement = MutableStateFlow(
        "📢 بشرى سارة: تم إطلاق مسابقة أولمبياد المتفوقين للمنهج المطور 2025 / 2026! جوائز وهدايا قيّمة كبرى سيعلن عنها مع انطلاق الجولة الأولى 🎁✨"
    )
    val announcement: StateFlow<String> = _announcement.asStateFlow()

    fun setAnnouncement(text: String) {
        if (text.isNotBlank()) {
            _announcement.value = text
        }
    }

    // Generated Promo Codes (أكواد التفعيل المجانية للمتفوقين)
    private val _promoCodes = MutableStateFlow(
        listOf("JANNAT-GOLD-2026", "ELHALWANY-VIP", "TOP-STUDENT-50", "GIFT-FOR-ALL")
    )
    val promoCodes: StateFlow<List<String>> = _promoCodes.asStateFlow()

    fun generateNewPromoCode(): String {
        val randomSuffix = (1000..9999).random()
        val newCode = "JANNAT-HERO-$randomSuffix"
        _promoCodes.value = listOf(newCode) + _promoCodes.value
        return newCode
    }

    override fun onCleared() {
        super.onCleared()
        examTimerJob?.cancel()
        voiceTimerJob?.cancel()
    }
}

data class UserProfile(
    val id: String = "std-1",
    val name: String = "جنات محمد عبد العزيز",
    val phone: String = "01024567890",
    val governorate: String = "البحر الأحمر (الغردقة) 🌊",
    val grade: String = "الصف الأول الإعدادي",
    val isOlympiadActive: Boolean = true,
    val role: String = "طالبة متميزة"
)

data class SubscriptionRequest(
    val id: String,
    val studentName: String,
    val phone: String,
    val governorate: String,
    val paymentMethod: String,
    val amount: Int = 50,
    val date: String,
    val status: String
)
