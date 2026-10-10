package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Question
import com.example.data.model.QuestionType
import com.example.data.repository.QuestionBank
import com.example.ui.components.SecurityLicenseDialog
import com.example.ui.theme.RainbowCyan
import com.example.ui.theme.RainbowGreen
import com.example.ui.theme.RainbowHorizontalGradient
import com.example.ui.theme.RainbowIndigo
import com.example.ui.theme.RainbowOrange
import com.example.ui.theme.RainbowRed
import com.example.ui.theme.RainbowViolet
import com.example.ui.theme.RainbowYellow
import com.example.ui.viewmodel.JannatViewModel
import kotlinx.coroutines.delay

data class LeaderboardStudent(
    val rank: Int,
    val name: String,
    val governorate: String,
    val score: Int,
    val totalTimeSeconds: Int,
    val prizeTitle: String,
    val avatarEmoji: String
)

@Composable
fun OlympiadCompetitionScreen(
    viewModel: JannatViewModel,
    onBackToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val currentGovernorate by viewModel.userGovernorate.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("🏆 الجوائز والشروط", "💳 تفعيل الاشتراك (50 ج)", "⏱️ امتحان التحدي", "🥇 لوحة الشرف")

    // Subscription & activation state
    val context = LocalContext.current
    var isSubscribed by remember { mutableStateOf(false) }
    var inputCode by remember { mutableStateOf("") }
    var inputPhone by remember { mutableStateOf("") }
    var activationMessage by remember { mutableStateOf<String?>(null) }
    var showCopySuccess by remember { mutableStateOf(false) }
    var copySuccessDetail by remember { mutableStateOf("تم نسخ البيانات بنجاح ✅") }
    var showSecurityLicenseModal by remember { mutableStateOf(false) }

    fun dialPhoneNumber(phone: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
            context.startActivity(intent)
        } catch (e: Exception) {
            clipboardManager.setText(AnnotatedString(phone))
            copySuccessDetail = "تم نسخ رقم الهاتف $phone للاتصال"
            showCopySuccess = true
        }
    }

    fun dialUssdCode(ussd: String) {
        try {
            val encoded = Uri.encode(ussd)
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$encoded"))
            context.startActivity(intent)
        } catch (e: Exception) {
            clipboardManager.setText(AnnotatedString(ussd))
            copySuccessDetail = "تم نسخ كود التحويل $ussd بنجاح"
            showCopySuccess = true
        }
    }

    // Quiz Mode state
    var isQuizActive by remember { mutableStateOf(false) }
    var quizCompleted by remember { mutableStateOf(false) }
    var quizCurrentIndex by remember { mutableIntStateOf(0) }
    var quizScore by remember { mutableIntStateOf(0) }
    var quizSecondsRemaining by remember { mutableIntStateOf(180) } // 3 minutes total
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var candidateName by remember { mutableStateOf("جنات محمد") }

    // Filter MCQ questions from question bank for competition
    val competitionQuestions = remember {
        QuestionBank.allQuestions.filter { it.type == QuestionType.MCQ }.take(10)
    }

    // Leaderboard list
    val leaderboard = remember {
        mutableStateListOf(
            LeaderboardStudent(1, "جنات محمد عبد العزيز", "البحر الأحمر (الغردقة) 🌊", 100, 72, "جائزة المركز الأول الكبرى 🎁", "🥇"),
            LeaderboardStudent(2, "مريم أحمد العوضي", "القاهرة (مدينة نصر) 🏛️", 98, 85, "تابلت تعليمي 10 بوصة للمذاكرة 💻", "🥈"),
            LeaderboardStudent(3, "عمر خالد الشافعي", "الإسكندرية (سموحة) 🌊", 96, 91, "ساعة ذكية رياضية لتنظيم المذاكرة ⌚", "🥉"),
            LeaderboardStudent(4, "سلمى محمود السعيد", "الدقهلية (المنصورة) 🌸", 94, 98, "درع التفوق + باقة مراجع تعليمية 📚", "🎖️"),
            LeaderboardStudent(5, "يوسف ريان إبراهيم", "الغربية (طنطا) 🍬", 92, 104, "درع التفوق + شهادة امتياز 🎖️", "🎖️"),
            LeaderboardStudent(6, "هنا عبد الرحمن حسن", "الجيزة (الدقي) 🏺", 90, 112, "شهادة تفوق مذهبة + باقة كتب 📜", "⭐"),
            LeaderboardStudent(7, "أحمد وائل المرسي", "بورسعيد 🚢", 88, 120, "شهادة تفوق مذهبة + هدية تذكارية 🎁", "⭐")
        )
    }

    // Timer for Quiz
    LaunchedEffect(isQuizActive, quizSecondsRemaining) {
        if (isQuizActive && quizSecondsRemaining > 0 && !quizCompleted) {
            delay(1000)
            quizSecondsRemaining--
        } else if (isQuizActive && quizSecondsRemaining <= 0 && !quizCompleted) {
            // Time up!
            quizCompleted = true
            isQuizActive = false
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .testTag("olympiad_competition_screen"),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // Top Header Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF1E1B4B),
                                    Color(0xFF312E81),
                                    Color(0xFF4338CA)
                                )
                            )
                        )
                        .padding(18.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = RainbowYellow.copy(alpha = 0.2f),
                                border = BorderStroke(1.dp, RainbowYellow)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.EmojiEvents,
                                        contentDescription = null,
                                        tint = RainbowYellow,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = "المسابقة الكبرى الرسمية 2025 / 2026",
                                        color = RainbowYellow,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            IconButton(
                                onClick = onBackToHome,
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Color.White.copy(alpha = 0.15f), CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "الرجوع للرئيسية",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "أولمبياد جنات للمتفوقين 🌟",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Text(
                            text = "تنافس شريف على مستوى الجمهورية • جوائز وهدايا قيّمة كبرى سيعلن عنها مع انطلاق الجولة الأولى 🎁✨",
                            color = Color(0xFFE0E7FF),
                            fontSize = 12.5.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Status & Subscription indicator
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (isSubscribed) RainbowGreen.copy(alpha = 0.25f) else RainbowOrange.copy(alpha = 0.25f),
                            border = BorderStroke(1.dp, if (isSubscribed) RainbowGreen else RainbowOrange)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (isSubscribed) Icons.Default.Verified else Icons.Default.AccountBalanceWallet,
                                        contentDescription = null,
                                        tint = if (isSubscribed) RainbowGreen else RainbowOrange,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (isSubscribed) "حسابك مفعّل في الأولمبياد وجاهز للتحدي ✅" else "رسم اشتراك رمزي: 50 جنيهاً مصرياً فقط",
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                if (!isSubscribed) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = RainbowOrange,
                                        modifier = Modifier.clickable { selectedTab = 1 }
                                    ) {
                                        Text(
                                            text = "طرق التفعيل",
                                            color = Color.White,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Tabs
        item {
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                edgePadding = 16.dp,
                divider = {},
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = RainbowIndigo,
                        height = 3.dp
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 13.sp,
                                color = if (selectedTab == index) RainbowIndigo else Color(0xFF64748B)
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        // TAB 0: الجوائز والشروط
        if (selectedTab == 0) {
            item {
                PrizesAndRulesSection(
                    onStartExamClick = {
                        selectedTab = 2
                    },
                    onOpenPaymentClick = {
                        selectedTab = 1
                    }
                )
            }
        }

        // TAB 1: تفعيل الاشتراك
        if (selectedTab == 1) {
            item {
                SubscriptionPaymentSection(
                    isSubscribed = isSubscribed,
                    inputCode = inputCode,
                    onCodeChange = { inputCode = it },
                    inputPhone = inputPhone,
                    onPhoneChange = { inputPhone = it },
                    activationMessage = activationMessage,
                    onActivate = {
                        if (inputCode.trim().isNotEmpty() || inputPhone.trim().length >= 10) {
                            isSubscribed = true
                            activationMessage = "تم تفعيل اشتراكك بنجاح في الأولمبياد! يمكنك الآن بدء امتحان التحدي والمنافسة 🏆"
                        } else {
                            activationMessage = "يرجى كتابة رقم الهاتف المحول منه أو كود التفعيل"
                        }
                    },
                    onInstantTrial = {
                        isSubscribed = true
                        activationMessage = "هدية من جنات: تم تفعيل الحساب التجريبي الفوري بنجاح! بالتوفيق والنجاح 🌟"
                    },
                    onCopyPrimaryPhone = {
                        clipboardManager.setText(AnnotatedString("01143288078"))
                        copySuccessDetail = "تم نسخ الرقم الموحد 01143288078 بنجاح. يمكنك استخدامه مع فودافون كاش، اتصالات كاش، أورنج كاش، وي باي، وإنستاباي."
                        showCopySuccess = true
                    },
                    onCopySecondaryPhone = {
                        clipboardManager.setText(AnnotatedString("01550354100"))
                        copySuccessDetail = "تم نسخ الرقم البديل 01550354100 بنجاح."
                        showCopySuccess = true
                    },
                    onCopyInstaPayEmail = {
                        clipboardManager.setText(AnnotatedString("elhalwanym74@gmail.com"))
                        copySuccessDetail = "تم نسخ حساب إنستاباي والبريد الرسمي elhalwanym74@gmail.com بنجاح."
                        showCopySuccess = true
                    },
                    onDialPrimaryPhone = {
                        dialPhoneNumber("01143288078")
                    },
                    onDialSecondaryPhone = {
                        dialPhoneNumber("01550354100")
                    },
                    onDialVodafoneUssd = {
                        dialUssdCode("*9*7*01143288078*50#")
                    },
                    onDialEtisalatUssd = {
                        dialUssdCode("*777#")
                    },
                    onDialOrangeUssd = {
                        dialUssdCode("*115#")
                    },
                    onDialWeUssd = {
                        dialUssdCode("*322#")
                    },
                    onOpenSecurityLicense = {
                        showSecurityLicenseModal = true
                    }
                )
            }
        }

        // TAB 2: امتحان التحدي الكبير
        if (selectedTab == 2) {
            item {
                OlympiadExamSection(
                    isSubscribed = isSubscribed,
                    isQuizActive = isQuizActive,
                    quizCompleted = quizCompleted,
                    quizCurrentIndex = quizCurrentIndex,
                    quizScore = quizScore,
                    quizSecondsRemaining = quizSecondsRemaining,
                    selectedOptionIndex = selectedOptionIndex,
                    candidateName = candidateName,
                    currentGovernorate = currentGovernorate,
                    questions = competitionQuestions,
                    onCandidateNameChange = { candidateName = it },
                    onStartQuiz = {
                        isQuizActive = true
                        quizCompleted = false
                        quizCurrentIndex = 0
                        quizScore = 0
                        quizSecondsRemaining = 180
                        selectedOptionIndex = null
                    },
                    onSelectOption = { optionIdx ->
                        selectedOptionIndex = optionIdx
                    },
                    onSubmitAnswer = {
                        val currentQ = competitionQuestions.getOrNull(quizCurrentIndex)
                        if (currentQ != null && selectedOptionIndex == currentQ.correctAnswerIndex) {
                            // Bonus points for answering fast
                            val speedBonus = (quizSecondsRemaining / 20)
                            quizScore += (10 + speedBonus)
                        }

                        if (quizCurrentIndex < competitionQuestions.size - 1) {
                            quizCurrentIndex++
                            selectedOptionIndex = null
                        } else {
                            quizCompleted = true
                            isQuizActive = false
                            // Add student to live leaderboard
                            val newRank = (leaderboard.count { it.score >= quizScore } + 1).coerceAtMost(leaderboard.size + 1)
                            leaderboard.add(
                                LeaderboardStudent(
                                    rank = newRank,
                                    name = candidateName.ifEmpty { "متسابق متميز" },
                                    governorate = currentGovernorate,
                                    score = quizScore,
                                    totalTimeSeconds = 180 - quizSecondsRemaining,
                                    prizeTitle = when (newRank) {
                                        1 -> "جائزة المركز الأول الكبرى 🎁"
                                        2 -> "جائزة المركز الثاني القيّمة 🎁"
                                        3 -> "جائزة المركز الثالث القيّمة 🎁"
                                        else -> "درع تفوق + شهادة امتياز 🎖️"
                                    },
                                    avatarEmoji = if (newRank == 1) "🥇" else if (newRank == 2) "🥈" else if (newRank == 3) "🥉" else "⭐"
                                )
                            )
                        }
                    },
                    onGoToActivate = {
                        selectedTab = 1
                    },
                    onGoToLeaderboard = {
                        selectedTab = 3
                    }
                )
            }
        }

        // TAB 3: لوحة الشرف
        if (selectedTab == 3) {
            item {
                LeaderboardSection(
                    leaderboard = leaderboard.sortedByDescending { it.score }
                )
            }
        }
    }

    if (showCopySuccess) {
        AlertDialog(
            onDismissRequest = { showCopySuccess = false },
            title = { Text("تم النسخ بنجاح ✅", fontWeight = FontWeight.Bold) },
            text = { Text(copySuccessDetail) },
            confirmButton = {
                TextButton(onClick = { showCopySuccess = false }) {
                    Text("حسناً", fontWeight = FontWeight.Bold, color = RainbowIndigo)
                }
            }
        )
    }

    if (showSecurityLicenseModal) {
        SecurityLicenseDialog(
            viewModel = viewModel,
            onDismiss = { showSecurityLicenseModal = false }
        )
    }
}

// ==========================================
// 1. قسم الجوائز والشروط الشفافة
// ==========================================
@Composable
private fun PrizesAndRulesSection(
    onStartExamClick: () -> Unit,
    onOpenPaymentClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Grand Prize Card 1
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            border = BorderStroke(1.5.dp, Color(0xFFFBBF24))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(Color(0xFFFEF3C7), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🥇", fontSize = 28.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "المركز الأول على مستوى الجمهورية",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color(0xFFB45309)
                        )
                        Text(
                            text = "جوائز وهدايا قيّمة كبرى سيعلن عنها مع انطلاق الجولة الأولى 🎁✨",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 13.5.sp,
                            color = Color(0xFF1E293B)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = Color(0xFFF1F5F9))
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "بالإضافة إلى: درع بطل أولمبياد جنات + شهادة تقدير مذهبة وتكريم خاص لولي الأمر.",
                    fontSize = 12.sp,
                    color = Color(0xFF475569)
                )
            }
        }

        // Prize Card 2 & 3 Grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Second Place
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = BorderStroke(1.dp, Color(0xFFCBD5E1))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = "🥈 المركز الثاني", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF475569))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "هدايا قيّمة كبرى 🎁", fontWeight = FontWeight.ExtraBold, fontSize = 12.5.sp, color = RainbowIndigo)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "سيعلن عنها مع انطلاق الجولة الأولى + شهادة تفوق.", fontSize = 11.sp, color = Color(0xFF334155))
                }
            }

            // Third Place
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = BorderStroke(1.dp, Color(0xFFF97316).copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = "🥉 المركز الثالث", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = RainbowOrange)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "هدايا قيّمة كبرى 🎁", fontWeight = FontWeight.ExtraBold, fontSize = 12.5.sp, color = Color(0xFF1E293B))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "سيعلن عنها مع انطلاق الجولة الأولى + درع التميز.", fontSize = 11.sp, color = Color(0xFF334155))
                }
            }
        }

        // Centers 4 to 10
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "🎖️", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "المراكز من الرابع إلى العاشر (4 - 10)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.5.sp,
                        color = Color(0xFF1E293B)
                    )
                    Text(
                        text = "دروع تكريم المتفوقين + شهادات تقدير معتمدة + باقات كتب خارجية وهدايا عينية تشجيعية.",
                        fontSize = 12.sp,
                        color = Color(0xFF64748B)
                    )
                }
            }
        }

        // Transparency & Anti-Gambling Declaration (ميثاق العدالة والشفافية)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
            border = BorderStroke(1.dp, RainbowIndigo.copy(alpha = 0.3f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = null,
                        tint = RainbowIndigo,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ميثاق الشفافية والعدالة (علمي بحت 100%)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = RainbowIndigo
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "• الفوز مبني على الجد والاجتهاد العلمي فقط دون أي قرعة أو حظ.\n" +
                            "• المعيار: أعلى درجة في الامتحان الشامل + أسرع وقت بالإجابات الصحيحة (بالثواني).\n" +
                            "• رسوم الـ 50 جنيهاً رمزية وتُخصص بالكامل لتجهيز الجوائز والدروع والشهادات لتشجيع الطلاب لوجه الله تعالى.",
                    fontSize = 12.sp,
                    color = Color(0xFF334155),
                    lineHeight = 18.sp
                )
            }
        }

        // Action CTA Buttons
        Button(
            onClick = onStartExamClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("start_olympiad_cta"),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RainbowIndigo)
        ) {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
            Spacer(modifier = Modifier.width(6.dp))
            Text("دخول امتحان التحدي والمنافسة الآن", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }

        OutlinedButton(
            onClick = onOpenPaymentClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, RainbowOrange)
        ) {
            Icon(imageVector = Icons.Default.AccountBalanceWallet, contentDescription = null, tint = RainbowOrange)
            Spacer(modifier = Modifier.width(6.dp))
            Text("طرق تفعيل الاشتراك الرمزي (50 ج)", color = RainbowOrange, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
    }
}

// ==========================================
// 2. قسم الاشتراك والدفع السهل (Vodafone Cash / InstaPay)
// ==========================================
@Composable
private fun SubscriptionPaymentSection(
    isSubscribed: Boolean,
    inputCode: String,
    onCodeChange: (String) -> Unit,
    inputPhone: String,
    onPhoneChange: (String) -> Unit,
    activationMessage: String?,
    onActivate: () -> Unit,
    onInstantTrial: () -> Unit,
    onCopyPrimaryPhone: () -> Unit,
    onCopySecondaryPhone: () -> Unit,
    onCopyInstaPayEmail: () -> Unit,
    onDialPrimaryPhone: () -> Unit,
    onDialSecondaryPhone: () -> Unit,
    onDialVodafoneUssd: () -> Unit,
    onDialEtisalatUssd: () -> Unit,
    onDialOrangeUssd: () -> Unit,
    onDialWeUssd: () -> Unit,
    onOpenSecurityLicense: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Explanatory Banner
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFFEF3C7),
            border = BorderStroke(1.dp, Color(0xFFFDE68A))
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "💡", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "قيمة الاشتراك الرمزية (50 جنيهاً مصرياً) مخصصة لصندوق الجوائز والهدايا القيّمة ودعم الطلاب المتفوقين في جميع المحافظات.",
                    fontSize = 12.5.sp,
                    color = Color(0xFF92400E),
                    lineHeight = 18.sp
                )
            }
        }

        // Reassurance for users without InstaPay (لا تحتاج لإنستاباي - المحافظ تعمل مباشرة)
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFEFF6FF),
            border = BorderStroke(1.5.dp, Color(0xFF3B82F6))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "📱", fontSize = 22.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ليس لديك إنستاباي؟ لا مشكلة نهائياً! يمكنك التحويل من محفظتك مباشرة ⚡",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.5.sp,
                        color = Color(0xFF1D4ED8)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "الرقم الموحد 01143288078 يستقبل التحويلات فوراً من كافة محافظ الهواتف (فودافون كاش، اتصالات كاش، أورنج كاش، وي باي، والمحافظ البنكية Smart Wallet) دون أي حاجة لتطبيق إنستاباي. كما يمكنك الاتصال بكود شبكتك بضغطة زر أدناه.",
                    fontSize = 12.sp,
                    color = Color(0xFF1E3A8A),
                    lineHeight = 17.sp
                )
            }
        }

        // Method 1: Unified Phone Number (الرقم الموحد لجميع الشبكات والمحافظ)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            border = BorderStroke(1.5.dp, RainbowIndigo.copy(alpha = 0.4f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = RainbowIndigo.copy(alpha = 0.12f),
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = "⭐", fontSize = 18.sp)
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "الرقم الموحد لكافة الشبكات والمحافظ",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.5.sp,
                                    color = Color(0xFF1E293B)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = RainbowGreen.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = "معتمد رسمي ✅",
                                        color = RainbowGreen,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Text(
                                text = "فودافون كاش • اتصالات كاش • أورنج كاش • وي باي • إنستاباي",
                                fontSize = 11.sp,
                                color = RainbowIndigo,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    IconButton(onClick = onCopyPrimaryPhone) {
                        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "نسخ الرقم", tint = RainbowIndigo)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFEFF6FF),
                    border = BorderStroke(1.dp, RainbowIndigo.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "01143288078",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 16.sp,
                                color = RainbowIndigo
                            )
                            Text(
                                text = "باسم: م/ محمد عبد العزيز",
                                fontSize = 10.5.sp,
                                color = Color(0xFF64748B)
                            )
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = RainbowIndigo,
                                modifier = Modifier.clickable { onCopyPrimaryPhone() }
                            ) {
                                Text(
                                    text = "نسخ 📋",
                                    fontSize = 11.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFF059669),
                                modifier = Modifier.clickable { onDialPrimaryPhone() }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(imageVector = Icons.Default.Call, contentDescription = null, tint = Color.White, modifier = Modifier.size(13.dp))
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "اتصال 📞",
                                        fontSize = 11.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Direct USSD & Quick Dialing Cards (أكواد الاتصال المباشر بالمحفظة)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Color(0xFFF59E0B).copy(alpha = 0.6f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "⚡", fontSize = 22.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "أكواد التحويل السريع المباشرة من لوحة الاتصال",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.5.sp,
                            color = Color(0xFF92400E)
                        )
                        Text(
                            text = "اضغط على شبكتك لطلب كود المحفظة مباشرة من هاتفك:",
                            fontSize = 11.sp,
                            color = Color(0xFFB45309)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Vodafone Cash
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFFEF2F2),
                    border = BorderStroke(1.dp, Color(0xFFFCA5A5)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "🔴 فودافون كاش", fontWeight = FontWeight.Bold, fontSize = 12.5.sp, color = Color(0xFF991B1B))
                            Text(text = "كود التحويل الفوري: *9*7*01143288078*50#", fontSize = 10.5.sp, color = Color(0xFF7F1D1D))
                        }
                        Button(
                            onClick = onDialVodafoneUssd,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text("اتصال فوري 📞", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Etisalat Cash
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFF0FDF4),
                    border = BorderStroke(1.dp, Color(0xFF86EFAC)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "🟢 اتصالات كاش", fontWeight = FontWeight.Bold, fontSize = 12.5.sp, color = Color(0xFF166534))
                            Text(text = "اطلب *777# وحوّل للرقم 01143288078", fontSize = 10.5.sp, color = Color(0xFF14532D))
                        }
                        Button(
                            onClick = onDialEtisalatUssd,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A)),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text("طلب *777# 📞", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Orange Cash & WE Pay Grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Orange Cash
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFFFF7ED),
                        border = BorderStroke(1.dp, Color(0xFFFDBA74)),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onDialOrangeUssd() }
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(text = "🟠 أورنج كاش", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF9A3412))
                            Text(text = "طلب *115# للتحويل", fontSize = 10.sp, color = Color(0xFFC2410C))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "اضغط للاتصال 📞", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFEA580C))
                        }
                    }

                    // WE Pay
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFFAF5FF),
                        border = BorderStroke(1.dp, Color(0xFFD8B4FE)),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onDialWeUssd() }
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(text = "🟣 وي باي WE", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF6B21A8))
                            Text(text = "طلب *322# للتحويل", fontSize = 10.sp, color = Color(0xFF7E22CE))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "اضغط للاتصال 📞", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF9333EA))
                        }
                    }
                }
            }
        }

        // Method 2: Secondary Phone Number (الرقم الإضافي / البديل)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFF1F5F9),
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = "📞", fontSize = 18.sp)
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "الرقم الثاني / الإضافي للمحافظ",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.5.sp,
                                color = Color(0xFF1E293B)
                            )
                            Text(
                                text = "متاح أيضاً للتحويل في حال ضغط الشبكات",
                                fontSize = 11.sp,
                                color = Color(0xFF64748B)
                            )
                        }
                    }

                    IconButton(onClick = onCopySecondaryPhone) {
                        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "نسخ الرقم", tint = Color(0xFF64748B))
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFF8FAFC),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "01550354100",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color(0xFF334155)
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "نسخ 📋",
                                fontSize = 11.sp,
                                color = Color(0xFF64748B),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable { onCopySecondaryPhone() }
                            )
                            Text(
                                text = "اتصال 📞",
                                fontSize = 11.sp,
                                color = RainbowGreen,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable { onDialSecondaryPhone() }
                            )
                        }
                    }
                }
            }
        }

        // Method 3: InstaPay via Email / Phone (لمستخدمي إنستاباي)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            border = BorderStroke(1.dp, RainbowViolet.copy(alpha = 0.35f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = RainbowViolet.copy(alpha = 0.12f),
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = "🟣", fontSize = 18.sp)
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "إنستاباي (InstaPay) - اختياري",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.5.sp,
                                color = Color(0xFF1E293B)
                            )
                            Text(
                                text = "لمن يمتلك تطبيق إنستاباي ويرغب بالتحويل عبره",
                                fontSize = 11.sp,
                                color = Color(0xFF64748B)
                            )
                        }
                    }

                    IconButton(onClick = onCopyInstaPayEmail) {
                        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "نسخ البريد", tint = RainbowViolet)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFFAF5FF),
                    border = BorderStroke(1.dp, RainbowViolet.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "elhalwanym74@gmail.com",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 13.5.sp,
                                color = RainbowViolet
                            )
                            Text(
                                text = "أو عبر رقم الهاتف: 01143288078",
                                fontSize = 10.5.sp,
                                color = Color(0xFF64748B)
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = RainbowViolet,
                            modifier = Modifier.clickable { onCopyInstaPayEmail() }
                        ) {
                            Text(
                                text = "نسخ البريد 📋",
                                fontSize = 11.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        // Official License & Security Assurance Card (تأمين البيانات والترخيص الرسمي)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFECFDF5)),
            border = BorderStroke(1.5.dp, Color(0xFF10B981))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = null,
                            tint = Color(0xFF059669),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "تأمين كامل للبيانات وتطبيق مرخص رسمياً 🔒",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color(0xFF065F46)
                            )
                            Text(
                                text = "تشفير AES-256 Bit • ترخيص معتمد LIC-JANNAT-2026",
                                fontSize = 10.5.sp,
                                color = Color(0xFF047857)
                            )
                        }
                    }
                    Button(
                        onClick = onOpenSecurityLicense,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669)),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("وثيقة الترخيص 📜", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "جميع العمليات والتحويلات المالية وبيانات الطلاب مشفرة ومؤمنة تماماً. المنصة معتمدة ومسجلة رسمياً تحت الإشراف المباشر للأستاذ م/ محمد عبد العزيز © 2026.",
                    fontSize = 11.sp,
                    color = Color(0xFF065F46),
                    lineHeight = 16.sp
                )
            }
        }

        // WhatsApp Confirmation & Customer Support Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
            border = BorderStroke(1.dp, RainbowGreen.copy(alpha = 0.4f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "💬", fontSize = 22.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "خدمة المشتركين وإرسال إيصال التحويل (واتساب):",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.5.sp,
                        color = Color(0xFF166534)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "بعد التحويل، يمكنك إرسال صورة إيصال التحويل على واتساب المشرف العام:\n" +
                            "• رقم الواتساب الموحد: 01143288078\n" +
                            "• رقم التواصل البديل: 01550354100\n" +
                            "• المسؤول: م/ محمد عبد العزيز",
                    fontSize = 11.5.sp,
                    color = Color(0xFF14532D),
                    lineHeight = 17.sp
                )
            }
        }

        // Activation Form
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "تأكيد التحويل وتفعيل الحساب 🚀",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFF1E293B)
                )
                Text(
                    text = "بعد التحويل، اكتب رقم الهاتف الذي قمت بالتحويل منه لتفعيل اشتراكك فوراً:",
                    fontSize = 11.5.sp,
                    color = Color(0xFF64748B),
                    modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
                )

                OutlinedTextField(
                    value = inputPhone,
                    onValueChange = onPhoneChange,
                    label = { Text("رقم الهاتف المحوّل منه (مثلاً: 01012345678)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = inputCode,
                    onValueChange = onCodeChange,
                    label = { Text("أو كود التفعيل الخاص بك (إن وجد)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                if (activationMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = RainbowGreen.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = activationMessage,
                            color = RainbowGreen,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onActivate,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RainbowGreen)
                ) {
                    Icon(imageVector = Icons.Default.LockOpen, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("تأكيد التفعيل والدخول للتحدي", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Instant Free Trial Button
                OutlinedButton(
                    onClick = onInstantTrial,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, RainbowIndigo)
                ) {
                    Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = RainbowIndigo)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("تجربة مجانية فورية كهدية لتجربة الامتحان 🎁", color = RainbowIndigo, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

// ==========================================
// 3. قسم امتحان التحدي والمنافسة السريعة
// ==========================================
@Composable
private fun OlympiadExamSection(
    isSubscribed: Boolean,
    isQuizActive: Boolean,
    quizCompleted: Boolean,
    quizCurrentIndex: Int,
    quizScore: Int,
    quizSecondsRemaining: Int,
    selectedOptionIndex: Int?,
    candidateName: String,
    currentGovernorate: String,
    questions: List<Question>,
    onCandidateNameChange: (String) -> Unit,
    onStartQuiz: () -> Unit,
    onSelectOption: (Int) -> Unit,
    onSubmitAnswer: () -> Unit,
    onGoToActivate: () -> Unit,
    onGoToLeaderboard: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Case 1: Quiz is not active and not completed (Intro & Name entry)
        if (!isQuizActive && !quizCompleted) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(RainbowIndigo.copy(alpha = 0.1f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "⚡", fontSize = 22.sp)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "امتحان التحدي الأولمبي (10 أسئلة)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFF1E293B)
                            )
                            Text(
                                text = "شامل جميع مواد الصف الأول الإعدادي ومحسوب بالثواني",
                                fontSize = 12.sp,
                                color = Color(0xFF64748B)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "بيانات المتسابق للتسجيل في لوحة الشرف:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color(0xFF334155)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = candidateName,
                        onValueChange = onCandidateNameChange,
                        label = { Text("اسم الطالب ثلاثي") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFF1F5F9),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "📍 المحافظة المسجلة:", fontSize = 12.sp, color = Color(0xFF475569))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = currentGovernorate, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = RainbowIndigo)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFEF2F2),
                        border = BorderStroke(1.dp, Color(0xFFFECACA)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Timer, contentDescription = null, tint = RainbowRed, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "الزمن: 3 دقائق للإجابة. كلما أجبت أسرع حصلت على نقاط سرعة إضافية!",
                                fontSize = 11.5.sp,
                                color = RainbowRed,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onStartQuiz,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("start_challenge_quiz_button"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RainbowIndigo)
                    ) {
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("انطلاق التحدي الآن 🚀", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }

        // Case 2: Quiz is running
        if (isQuizActive && !quizCompleted) {
            val currentQ = questions.getOrNull(quizCurrentIndex)
            val minutes = quizSecondsRemaining / 60
            val seconds = quizSecondsRemaining % 60
            val formattedTime = String.format("%02d:%02d", minutes, seconds)

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Header with question number and timer
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = RainbowIndigo.copy(alpha = 0.1f)
                        ) {
                            Text(
                                text = "السؤال ${quizCurrentIndex + 1} من ${questions.size}",
                                color = RainbowIndigo,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (quizSecondsRemaining < 30) RainbowRed.copy(alpha = 0.15f) else RainbowOrange.copy(alpha = 0.15f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = null,
                                    tint = if (quizSecondsRemaining < 30) RainbowRed else RainbowOrange,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = formattedTime,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (quizSecondsRemaining < 30) RainbowRed else RainbowOrange
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    LinearProgressIndicator(
                        progress = { (quizCurrentIndex + 1).toFloat() / questions.size.toFloat() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = RainbowIndigo,
                        trackColor = Color(0xFFE2E8F0)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    if (currentQ != null) {
                        Text(
                            text = currentQ.questionText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color(0xFF1E293B),
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Options
                        currentQ.options.forEachIndexed { optIndex, optionText ->
                            val isSelected = selectedOptionIndex == optIndex
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) RainbowIndigo.copy(alpha = 0.12f) else Color(0xFFF8FAFC),
                                border = BorderStroke(
                                    if (isSelected) 2.dp else 1.dp,
                                    if (isSelected) RainbowIndigo else Color(0xFFE2E8F0)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable { onSelectOption(optIndex) }
                                    .testTag("olympiad_option_$optIndex")
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .background(
                                                if (isSelected) RainbowIndigo else Color.White,
                                                CircleShape
                                            )
                                            .border(1.dp, if (isSelected) RainbowIndigo else Color(0xFFCBD5E1), CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = optionText,
                                        fontSize = 13.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) RainbowIndigo else Color(0xFF1E293B)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = onSubmitAnswer,
                            enabled = selectedOptionIndex != null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("submit_olympiad_answer_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = RainbowIndigo)
                        ) {
                            Text(
                                text = if (quizCurrentIndex == questions.size - 1) "إنهاء الامتحان وتسجيل النتيجة 🏆" else "السؤال التالي ⬅️",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }

        // Case 3: Quiz Completed (Certificate & Score)
        if (quizCompleted) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                border = BorderStroke(2.dp, Color(0xFFFBBF24))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "🎉", fontSize = 42.sp)
                    Text(
                        text = "شهادة تفوق أولمبياد جنات",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = Color(0xFFB45309)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "نبارك للبطل / البطلة: $candidateName",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color(0xFF1E293B)
                    )
                    Text(
                        text = "المحافظة: $currentGovernorate",
                        fontSize = 12.sp,
                        color = Color(0xFF64748B)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFFEF3C7),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "مجموع النقاط التنافسية (بالسرعة والدقة):", fontSize = 12.sp, color = Color(0xFF92400E))
                            Text(
                                text = "$quizScore نقطة 🌟",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 26.sp,
                                color = Color(0xFFB45309)
                            )
                            Text(
                                text = "الوقت المستغرق: ${180 - quizSecondsRemaining} ثانية",
                                fontSize = 11.5.sp,
                                color = Color(0xFF78350F)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onGoToLeaderboard,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RainbowIndigo)
                    ) {
                        Icon(imageVector = Icons.Default.EmojiEvents, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("عرض ترتيبك في لوحة الشرف 🥇", fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = onStartQuiz,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("إعادة المحاولة لتحسين السرعة والنقاط 🔄")
                    }
                }
            }
        }
    }
}

// ==========================================
// 4. قسم لوحة الشرف المباشرة
// ==========================================
@Composable
private fun LeaderboardSection(
    leaderboard: List<LeaderboardStudent>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Color(0xFFEEF2FF),
            border = BorderStroke(1.dp, RainbowIndigo.copy(alpha = 0.2f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "📢", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "يتم تحديث الترتيب آلياً بحسب أعلى درجة وسرعة الحل بالثواني. الأوائل مؤهلون لاستلام الجوائز الكبرى رسمياً!",
                    fontSize = 11.5.sp,
                    color = RainbowIndigo,
                    lineHeight = 16.sp
                )
            }
        }

        leaderboard.forEach { student ->
            val isTop3 = student.rank <= 3
            val borderColor = when (student.rank) {
                1 -> Color(0xFFFBBF24)
                2 -> Color(0xFFCBD5E1)
                3 -> Color(0xFFF97316)
                else -> Color.Transparent
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (student.rank == 1) Color(0xFFFFFBEB) else Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = if (isTop3) 3.dp else 1.dp),
                border = if (isTop3) BorderStroke(1.5.dp, borderColor) else BorderStroke(1.dp, Color(0xFFF1F5F9))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Rank badge
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .background(
                                when (student.rank) {
                                    1 -> Color(0xFFFEF3C7)
                                    2 -> Color(0xFFF1F5F9)
                                    3 -> Color(0xFFFFEDD5)
                                    else -> Color(0xFFF8FAFC)
                                },
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = student.avatarEmoji,
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = student.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.5.sp,
                                color = Color(0xFF1E293B)
                            )
                            if (student.rank == 1) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "👑", fontSize = 12.sp)
                            }
                        }
                        Text(
                            text = student.governorate,
                            fontSize = 11.sp,
                            color = Color(0xFF64748B)
                        )
                        Text(
                            text = "الجائزة المرشحة: ${student.prizeTitle}",
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (student.rank == 1) Color(0xFFB45309) else Color(0xFF475569)
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "${student.score} نقطة",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp,
                            color = RainbowIndigo
                        )
                        Text(
                            text = "⏱️ ${student.totalTimeSeconds} ثانية",
                            fontSize = 10.5.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }
            }
        }
    }
}
