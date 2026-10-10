package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material.icons.outlined.SmartToy
import androidx.compose.material.icons.outlined.Style
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.material3.LocalTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.Subject
import com.example.ui.components.AppSettingsDialog
import com.example.ui.components.AuthDialog
import com.example.ui.components.CompactRainbowHeader
import com.example.ui.components.SecurityLicenseDialog
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import com.example.ui.screens.AchievementsScreen
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.BookmarksScreen
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.ExamSimulatorScreen
import com.example.ui.screens.FlashcardsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OlympiadCompetitionScreen
import com.example.ui.screens.QuestionBankScreen
import com.example.ui.screens.WelcomeScreen
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.RainbowGreen
import com.example.ui.theme.RainbowHorizontalGradient
import com.example.ui.theme.RainbowIndigo
import com.example.ui.theme.RainbowOrange
import com.example.ui.theme.RainbowRed
import com.example.ui.theme.RainbowViolet
import com.example.ui.viewmodel.JannatViewModel

enum class MainTab(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val accentColor: Color
) {
    HOME("الرئيسية", Icons.Filled.Home, Icons.Outlined.Home, RainbowIndigo),
    QUESTIONS("الأسئلة", Icons.Filled.MenuBook, Icons.Outlined.MenuBook, RainbowCyan),
    CHAT("اسأل جنات", Icons.Filled.SmartToy, Icons.Outlined.SmartToy, RainbowOrange),
    EXAM("الامتحانات", Icons.Filled.Quiz, Icons.Outlined.Quiz, RainbowRed),
    MORE("المزيد 🌟", Icons.Filled.MoreHoriz, Icons.Outlined.MoreHoriz, RainbowViolet)
}

val RainbowCyan = Color(0xFF06B6D4)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel: JannatViewModel = viewModel()
            val textScale by mainViewModel.textScale.collectAsState()

            MyApplicationTheme {
                val baseDensity = LocalDensity.current
                // Use exact 1:1 hardware screen density for razor-sharp pixel rendering without blur
                val scaledDensity = Density(
                    density = baseDensity.density,
                    fontScale = baseDensity.fontScale * textScale
                )

                // Ensure authentic Arabic right-to-left layout and scalable crisp Cairo typography
                CompositionLocalProvider(
                    LocalLayoutDirection provides LayoutDirection.Rtl,
                    LocalDensity provides scaledDensity,
                    LocalTextStyle provides TextStyle(
                        fontFamily = CairoFontFamily,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF0F172A),
                        letterSpacing = 0.sp
                    )
                ) {
                    val hasSeenWelcome by mainViewModel.hasSeenWelcomeScreen.collectAsState()
                    Crossfade(
                        targetState = hasSeenWelcome,
                        label = "welcome_crossfade"
                    ) { seen ->
                        if (!seen) {
                            WelcomeScreen(
                                viewModel = mainViewModel,
                                onStartClicked = { mainViewModel.completeWelcomeScreen() }
                            )
                        } else {
                            LearnWithJannatApp(mainViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LearnWithJannatApp(viewModel: JannatViewModel = viewModel()) {
    var currentTab by remember { mutableStateOf(MainTab.HOME) }
    var activeSubScreen by remember { mutableStateOf<String?>(null) } // "welcome", "flashcards", "bookmarks", "achievements"

    BackHandler(enabled = activeSubScreen != null) {
        activeSubScreen = null
    }

    val textScale by viewModel.textScale.collectAsState()
    val isFullScreen by viewModel.isFullScreenMode.collectAsState()
    var showSettingsDialog by remember { mutableStateOf(false) }
    var showMoreMenuDialog by remember { mutableStateOf(false) }
    var showAuthDialog by remember { mutableStateOf(false) }
    var showSecurityLicenseDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("app_scaffold"),
        containerColor = Color(0xFFF8FAFC),
        topBar = {
            if (!isFullScreen && activeSubScreen != "welcome") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.statusBars)
                ) {
                    CompactRainbowHeader(
                        title = "تعلم مع جنات",
                        subtitle = when {
                            activeSubScreen == "admin" -> "لوحة تحكم المشرف العام والأدمن 🛡️"
                            activeSubScreen == "olympiad" -> "أولمبياد المتفوقين والجوائز الكبرى 🏆"
                            activeSubScreen == "flashcards" -> "بطاقات ليلة الامتحان السريعة"
                            activeSubScreen == "bookmarks" -> "الأسئلة المحفوظة للمراجعة"
                            activeSubScreen == "achievements" -> "سجل النتائج والتفوق"
                            currentTab == MainTab.HOME -> "المنهج المطور • الصف الأول الإعدادي"
                            currentTab == MainTab.QUESTIONS -> "أهم الأسئلة ونماذج الإجابة المعتمدة"
                            currentTab == MainTab.CHAT -> "اسأل جنات ومحادثات المحافظات"
                            currentTab == MainTab.EXAM -> "محاكي امتحانات نصف العام"
                            else -> "المزيد من الخدمات والبطاقات"
                        },
                        textScale = textScale
                    )
                }
            }
        },
        bottomBar = {
            if (activeSubScreen != "welcome") {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shadowElevation = 14.dp,
                    color = Color.White
                ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                ) {
                    // Rainbow indicator line
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(RainbowHorizontalGradient)
                            .height(3.dp)
                    )

                    NavigationBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(78.dp),
                        containerColor = Color.White,
                        tonalElevation = 0.dp,
                        windowInsets = WindowInsets(0, 0, 0, 0)
                    ) {
                        MainTab.values().forEach { tab ->
                            val isSelected = (activeSubScreen == null && currentTab == tab) ||
                                    (tab == MainTab.MORE && activeSubScreen != null)
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = {
                                    if (tab == MainTab.MORE) {
                                        showMoreMenuDialog = true
                                    } else {
                                        activeSubScreen = null
                                        currentTab = tab
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                        contentDescription = tab.title,
                                        modifier = Modifier.size(24.dp)
                                    )
                                },
                                label = {
                                    Text(
                                        text = tab.title,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.SemiBold,
                                        maxLines = 1,
                                        softWrap = false
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = tab.accentColor,
                                    selectedTextColor = tab.accentColor,
                                    unselectedIconColor = Color(0xFF475569),
                                    unselectedTextColor = Color(0xFF334155),
                                    indicatorColor = tab.accentColor.copy(alpha = 0.16f)
                                ),
                                modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                            )
                        }
                    }
                }
            }
        }
    }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .then(if (isFullScreen) Modifier.windowInsetsPadding(WindowInsets.statusBars) else Modifier)
        ) {
            when {
                activeSubScreen == "welcome" -> {
                    WelcomeScreen(
                        viewModel = viewModel,
                        onStartClicked = {
                            activeSubScreen = null
                            currentTab = MainTab.HOME
                        }
                    )
                }
                activeSubScreen == "admin" -> {
                    AdminScreen(
                        viewModel = viewModel,
                        onBack = {
                            activeSubScreen = null
                            currentTab = MainTab.HOME
                        }
                    )
                }
                activeSubScreen == "olympiad" -> {
                    OlympiadCompetitionScreen(
                        viewModel = viewModel,
                        onBackToHome = {
                            activeSubScreen = null
                            currentTab = MainTab.HOME
                        }
                    )
                }
                activeSubScreen == "flashcards" -> {
                    FlashcardsScreen(viewModel = viewModel)
                }
                activeSubScreen == "bookmarks" -> {
                    BookmarksScreen(
                        viewModel = viewModel,
                        onNavigateToQuestionBank = {
                            viewModel.selectSubject(Subject.ALL)
                            activeSubScreen = null
                            currentTab = MainTab.QUESTIONS
                        }
                    )
                }
                activeSubScreen == "achievements" -> {
                    AchievementsScreen(viewModel = viewModel)
                }
                currentTab == MainTab.HOME -> {
                    HomeScreen(
                        viewModel = viewModel,
                        onNavigateToSubject = { subject ->
                            viewModel.selectSubject(subject)
                            activeSubScreen = null
                            currentTab = MainTab.QUESTIONS
                        },
                        onStartExam = { subject ->
                            viewModel.startExam(subject, 10)
                            activeSubScreen = null
                            currentTab = MainTab.EXAM
                        },
                        onNavigateToFlashcards = {
                            activeSubScreen = "flashcards"
                        },
                        onNavigateToQuestionBank = {
                            viewModel.selectSubject(Subject.ALL)
                            activeSubScreen = null
                            currentTab = MainTab.QUESTIONS
                        },
                        onNavigateToChat = { channelId ->
                            viewModel.selectChannel(channelId)
                            activeSubScreen = null
                            currentTab = MainTab.CHAT
                        },
                        onNavigateToOlympiad = {
                            activeSubScreen = "olympiad"
                        },
                        onOpenAuth = {
                            showAuthDialog = true
                        },
                        onOpenAdmin = {
                            activeSubScreen = "admin"
                        }
                    )
                }
                currentTab == MainTab.QUESTIONS -> {
                    QuestionBankScreen(viewModel = viewModel)
                }
                currentTab == MainTab.CHAT -> {
                    ChatScreen(viewModel = viewModel)
                }
                currentTab == MainTab.EXAM -> {
                    ExamSimulatorScreen(viewModel = viewModel)
                }
                else -> {
                    HomeScreen(
                        viewModel = viewModel,
                        onNavigateToSubject = { subject ->
                            viewModel.selectSubject(subject)
                            currentTab = MainTab.QUESTIONS
                        },
                        onStartExam = { subject ->
                            viewModel.startExam(subject, 10)
                            currentTab = MainTab.EXAM
                        },
                        onNavigateToFlashcards = {
                            activeSubScreen = "flashcards"
                        },
                        onNavigateToQuestionBank = {
                            viewModel.selectSubject(Subject.ALL)
                            currentTab = MainTab.QUESTIONS
                        },
                        onNavigateToChat = { channelId ->
                            viewModel.selectChannel(channelId)
                            currentTab = MainTab.CHAT
                        },
                        onNavigateToOlympiad = {
                            activeSubScreen = "olympiad"
                        },
                        onOpenAuth = {
                            showAuthDialog = true
                        },
                        onOpenAdmin = {
                            activeSubScreen = "admin"
                        }
                    )
                }
            }

            // If in Full Screen mode: floating minimal button to exit full screen or open settings
            if (isFullScreen) {
                FloatingActionButton(
                    onClick = { showSettingsDialog = true },
                    shape = RoundedCornerShape(12.dp),
                    containerColor = RainbowIndigo.copy(alpha = 0.85f),
                    contentColor = Color.White,
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .size(42.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FullscreenExit,
                        contentDescription = "إنهاء ملء الشاشة أو الضبط",
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }

    // App Settings Dialog (تكبير الخط للهاتف، ملء الشاشة، اختيار المحافظة، بيانات التفوق)
    if (showSettingsDialog) {
        AppSettingsDialog(
            viewModel = viewModel,
            onDismiss = { showSettingsDialog = false }
        )
    }

    // More Options Menu Dialog
    if (showMoreMenuDialog) {
        AlertDialog(
            onDismissRequest = { showMoreMenuDialog = false },
            title = {
                Text(
                    text = "المزيد من الأدوات والضبط 🌟",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = RainbowIndigo
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // User Auth & Profile
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = RainbowIndigo.copy(alpha = 0.1f),
                        border = BorderStroke(1.dp, RainbowIndigo.copy(alpha = 0.3f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showMoreMenuDialog = false
                                showAuthDialog = true
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "👤", fontSize = 22.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "تسجيل الدخول / الملف الشخصي",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color(0xFF0F172A)
                                )
                                Text(
                                    text = "حساب الطالب وبيانات التواصل وحالة الاشتراك",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF334155)
                                )
                            }
                        }
                    }

                    // Admin Dashboard Access
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF0F172A),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showMoreMenuDialog = false
                                activeSubScreen = "admin"
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🛡️", fontSize = 22.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "لوحة تحكم المشرف العام (الأدمن)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color(0xFFFBBF24)
                                )
                                Text(
                                    text = "إدارة الاشتراكات والتحويلات وصندوق الجوائز وأكواد التفعيل",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFFCBD5E1)
                                )
                            }
                        }
                    }

                    // Olympiad Competition Entry
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFEF3C7),
                        border = BorderStroke(1.5.dp, Color(0xFFFBBF24)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                activeSubScreen = "olympiad"
                                showMoreMenuDialog = false
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🏆", fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "أولمبياد المتفوقين والجوائز الكبرى",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 15.sp,
                                    color = Color(0xFF92400E)
                                )
                                Text(
                                    text = "هدايا قيمة سيعلن عنها عند الجولة الأولى 🎁✨ • رسم رمزي 50 ج لدعم الأوائل",
                                    fontSize = 12.sp,
                                    color = Color(0xFF9A3412),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    // Flashcards
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = RainbowGreen.copy(alpha = 0.1f),
                        border = BorderStroke(1.dp, RainbowGreen.copy(alpha = 0.3f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                activeSubScreen = "flashcards"
                                showMoreMenuDialog = false
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "📇", fontSize = 22.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "بطاقات ليلة الامتحان السريعة",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color(0xFF0F172A)
                                )
                                Text(
                                    text = "مراجعة القوانين والمصطلحات بنظام البطاقات التفاعلية",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF334155)
                                )
                            }
                        }
                    }

                    // Bookmarks
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = RainbowViolet.copy(alpha = 0.1f),
                        border = BorderStroke(1.dp, RainbowViolet.copy(alpha = 0.3f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                activeSubScreen = "bookmarks"
                                showMoreMenuDialog = false
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🔖", fontSize = 22.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "الأسئلة المحفوظة",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color(0xFF0F172A)
                                )
                                Text(
                                    text = "الأسئلة الصعبة التي حددتها للمراجعة قبل الامتحان",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF334155)
                                )
                            }
                        }
                    }

                    // Achievements & History
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = RainbowOrange.copy(alpha = 0.1f),
                        border = BorderStroke(1.dp, RainbowOrange.copy(alpha = 0.3f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                activeSubScreen = "achievements"
                                showMoreMenuDialog = false
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🏆", fontSize = 22.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "سجل الامتحانات وإنجازاتي",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color(0xFF0F172A)
                                )
                                Text(
                                    text = "درجاتك السابقة وشهادة التفوق والنجوم",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF334155)
                                )
                            }
                        }
                    }

                    // Welcome & Onboarding Screen Showcase
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF3E8FF),
                        border = BorderStroke(1.dp, RainbowViolet.copy(alpha = 0.4f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showMoreMenuDialog = false
                                activeSubScreen = "welcome"
                            }
                            .testTag("more_menu_welcome_btn")
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🌈", fontSize = 22.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "شاشة الترحيب 'تعلم مع جنات' 🎈",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = RainbowViolet
                                )
                                Text(
                                    text = "عرض بطاقة البداية المبهجة بألوان الطيف ومميزات المنصة",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF475569)
                                )
                            }
                        }
                    }

                    // App Settings
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = RainbowIndigo.copy(alpha = 0.1f),
                        border = BorderStroke(1.dp, RainbowIndigo.copy(alpha = 0.3f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showMoreMenuDialog = false
                                showSettingsDialog = true
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "⚙️", fontSize = 22.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "الضبط وتكبير الخط والشاشة الكاملة",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color(0xFF0F172A)
                                )
                                Text(
                                    text = "تكبير الخط للموبايل (130% / 150%) واختيار المحافظة",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF334155)
                                )
                            }
                        }
                    }
                    // Data Security & Official License
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFECFDF5),
                        border = BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.4f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showMoreMenuDialog = false
                                showSecurityLicenseDialog = true
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🛡️", fontSize = 22.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "تأمين البيانات والترخيص الرسمي 🔒",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF065F46)
                                )
                                Text(
                                    text = "تشفير 256-bit، رخصة المنصة الرسمية، وثيقة الأمان © 2026",
                                    fontSize = 11.sp,
                                    color = Color(0xFF047857)
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showMoreMenuDialog = false }) {
                    Text("إغلاق", color = RainbowIndigo, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    if (showAuthDialog) {
        AuthDialog(
            viewModel = viewModel,
            onDismiss = { showAuthDialog = false },
            onOpenAdmin = {
                showAuthDialog = false
                activeSubScreen = "admin"
            }
        )
    }

    if (showSecurityLicenseDialog) {
        SecurityLicenseDialog(
            viewModel = viewModel,
            onDismiss = { showSecurityLicenseDialog = false }
        )
    }
}
