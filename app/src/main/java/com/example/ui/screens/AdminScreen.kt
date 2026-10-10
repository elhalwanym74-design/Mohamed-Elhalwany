package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SecurityLicenseDialog
import com.example.ui.theme.RainbowGreen
import com.example.ui.theme.RainbowIndigo
import com.example.ui.theme.RainbowOrange
import com.example.ui.theme.RainbowRed
import com.example.ui.theme.RainbowViolet
import com.example.ui.theme.RainbowYellow
import com.example.ui.viewmodel.JannatViewModel
import com.example.ui.viewmodel.SubscriptionRequest

@Composable
fun AdminScreen(
    viewModel: JannatViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isAdminLoggedIn by viewModel.isAdminLoggedIn.collectAsState()
    val requests by viewModel.subscriptionRequests.collectAsState()
    val announcement by viewModel.announcement.collectAsState()
    val promoCodes by viewModel.promoCodes.collectAsState()
    val clipboardManager = LocalClipboardManager.current

    var pinInput by remember { mutableStateOf("") }
    var pinError by remember { mutableStateOf(false) }
    var isPinVisible by remember { mutableStateOf(true) }

    var newPinInput by remember { mutableStateOf("") }
    var confirmPinInput by remember { mutableStateOf("") }
    var isNewPinVisible by remember { mutableStateOf(false) }
    var changePinError by remember { mutableStateOf<String?>(null) }
    var changePinSuccess by remember { mutableStateOf(false) }

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("📋 طلبات التفعيل", "💰 إحصائيات الصندوق", "🎟️ أكواد التفعيل", "🔐 تغيير كلمة السر", "📢 التنبيه العام")

    var showCopyDialog by remember { mutableStateOf(false) }
    var copiedCodeText by remember { mutableStateOf("") }
    var showSecurityLicenseModal by remember { mutableStateOf(false) }

    var newAnnouncementText by remember { mutableStateOf(announcement) }
    var announcementSuccess by remember { mutableStateOf(false) }

    // If Admin is not logged in, show PIN Gate
    if (!isAdminLoggedIn) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF0F172A))
                .padding(24.dp)
                .testTag("admin_login_screen"),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFEF3C7)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = null,
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "لوحة تحكم المشرف العام (الأدمن) 🛡️",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = Color(0xFF1E293B),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "مخصصة لإدارة المشتركين، واعتماد طلبات التحويل وتفعيل مسابقة الجوائز الكبرى.",
                        fontSize = 12.sp,
                        color = Color(0xFF64748B),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                    )

                    // Admin Credentials Info Card
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFEFF6FF),
                        border = BorderStroke(1.dp, RainbowIndigo.copy(alpha = 0.25f)),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "بيانات المشرف المعتمدة:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.5.sp,
                                color = RainbowIndigo
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "📧 البريد: ${viewModel.adminEmail}",
                                fontSize = 11.sp,
                                color = Color(0xFF334155),
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "📱 الرقم الموحد لكافة الشبكات: ${viewModel.adminPrimaryPhone}",
                                fontSize = 11.sp,
                                color = Color(0xFF334155),
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "📞 الرقم البديل: ${viewModel.adminSecondaryPhone}",
                                fontSize = 10.5.sp,
                                color = Color(0xFF64748B)
                            )
                        }
                    }

                    OutlinedTextField(
                        value = pinInput,
                        onValueChange = {
                            pinInput = it
                            pinError = false
                        },
                        label = { Text("أدخل الرمز السري أو البريد أو الهاتف", color = Color(0xFF334155)) },
                        placeholder = { Text("الرمز: 2026 أو البريد الرسمي", color = Color(0xFF94A3B8)) },
                        visualTransformation = if (isPinVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { isPinVisible = !isPinVisible }) {
                                Icon(
                                    imageVector = if (isPinVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = "إظهار أو إخفاء الرمز",
                                    tint = RainbowIndigo
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color(0xFF0F172A),
                            unfocusedTextColor = Color(0xFF0F172A),
                            focusedContainerColor = Color(0xFFF8FAFC),
                            unfocusedContainerColor = Color(0xFFF8FAFC),
                            focusedBorderColor = RainbowIndigo,
                            unfocusedBorderColor = Color(0xFF94A3B8),
                            cursorColor = RainbowIndigo
                        ),
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        ),
                        isError = pinError,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true
                    )

                    if (pinError) {
                        Text(
                            text = "بيانات الدخول غير صحيحة، يرجى المحاولة مرة أخرى",
                            color = RainbowRed,
                            fontSize = 11.5.sp,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            val success = viewModel.loginAdmin(pinInput)
                            if (!success) {
                                pinError = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RainbowIndigo)
                    ) {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("دخول لوحة التحكم", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Quick 1-click admin login button for convenience
                    OutlinedButton(
                        onClick = {
                            viewModel.loginAdmin("2026")
                        },
                        modifier = Modifier.fillMaxWidth().height(42.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, RainbowGreen)
                    ) {
                        Text("دخول سريع بحساب المشرف (${viewModel.adminEmail}) ✅", color = RainbowGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    TextButton(onClick = onBack) {
                        Text("الرجوع إلى التطبيق", color = Color(0xFF64748B), fontSize = 13.sp)
                    }
                }
            }
        }
        return
    }

    // Admin Dashboard Content
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .testTag("admin_dashboard_screen"),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // Top Admin Header
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                shape = RoundedCornerShape(22.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF0F172A),
                                    Color(0xFF1E293B),
                                    Color(0xFF334155)
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
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = CircleShape,
                                    color = Color(0xFFF59E0B),
                                    modifier = Modifier.size(34.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(text = "👑", fontSize = 18.sp)
                                    }
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "لوحة تحكم الإدارة والمشرف العام",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                    Text(
                                        text = "المسؤول: ${viewModel.adminName} • الغردقة 🌊",
                                        color = Color(0xFFFDE68A),
                                        fontSize = 12.5.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "البريد: ${viewModel.adminEmail}",
                                        color = Color(0xFFCBD5E1),
                                        fontSize = 11.sp
                                    )
                                    Text(
                                        text = "الرقم الموحد: ${viewModel.adminPrimaryPhone} • البديل: ${viewModel.adminSecondaryPhone}",
                                        color = Color(0xFF94A3B8),
                                        fontSize = 10.5.sp
                                    )
                                }
                            }

                            Row {
                                IconButton(
                                    onClick = { viewModel.logoutAdmin() },
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(Color.White.copy(alpha = 0.15f), CircleShape)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "تسجيل خروج",
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                IconButton(
                                    onClick = onBack,
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(Color.White.copy(alpha = 0.15f), CircleShape)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "الرجوع",
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Security & Official System License Status
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFF1E293B),
                            border = BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.4f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showSecurityLicenseModal = true }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.VerifiedUser,
                                        contentDescription = null,
                                        tint = Color(0xFF34D399),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "حالة النظام: مُؤَمَّن بالكامل (AES-256) • ترخيص: LIC-JANNAT-2026",
                                        fontSize = 11.sp,
                                        color = Color(0xFFE2E8F0),
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                Text(
                                    text = "عرض الوثيقة 📜",
                                    fontSize = 10.5.sp,
                                    color = Color(0xFF34D399),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Real-time KPI Stats Grid
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Total students
                            StatMetricCard(
                                title = "الطلاب المسجلين",
                                value = "248 طالب",
                                icon = Icons.Default.People,
                                color = RainbowIndigo,
                                modifier = Modifier.weight(1f)
                            )
                            // Olympiad participants
                            StatMetricCard(
                                title = "مشتركو الأولمبياد",
                                value = "${requests.count { it.status.contains("مفعّل") }} مشترك",
                                icon = Icons.Default.EmojiEvents,
                                color = RainbowOrange,
                                modifier = Modifier.weight(1f)
                            )
                            // Prize Fund
                            val totalFund = requests.count { it.status.contains("مفعّل") } * 50
                            StatMetricCard(
                                title = "صندوق الجوائز",
                                value = "$totalFund ج.م",
                                icon = Icons.Default.Savings,
                                color = RainbowGreen,
                                modifier = Modifier.weight(1f)
                            )
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
                        color = Color(0xFF0F172A),
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
                                color = if (selectedTab == index) Color(0xFF0F172A) else Color(0xFF64748B)
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        // TAB 0: طلبات التحويل وتفعيل الاشتراكات
        if (selectedTab == 0) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "طلبات التحويل الأخيرة (${requests.size}):",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF1E293B)
                        )
                        Text(
                            text = "اضغط تفعيل لإتاحة الامتحان فوراً ✅",
                            fontSize = 11.sp,
                            color = RainbowGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    requests.forEach { req ->
                        SubscriptionRequestItem(
                            request = req,
                            onApprove = { viewModel.approveSubscriptionRequest(req.id) },
                            onReject = { viewModel.rejectSubscriptionRequest(req.id) }
                        )
                    }
                }
            }
        }

        // TAB 1: صندوق الجوائز وشراء الهدايا
        if (selectedTab == 1) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val activeCount = requests.count { it.status.contains("مفعّل") }
                    val currentFund = activeCount * 50

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.5.dp, Color(0xFFF59E0B))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "💰", fontSize = 26.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "الميزانية المتاحة لشراء الجوائز",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.5.sp,
                                        color = Color(0xFF92400E)
                                    )
                                    Text(
                                        text = "$currentFund جنيهاً مصرياً مجمعة حتى الآن",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 18.sp,
                                        color = Color(0xFFB45309)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))
                            HorizontalDivider(color = Color(0xFFF1F5F9))
                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "خطة صرف الجوائز وتوزيعها حسب الحصيلة:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color(0xFF334155)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            PrizeAllocationRow(
                                title = "المركز الأول 🥇",
                                itemDesc = "جوائز وهدايا قيّمة كبرى للمركز الأول",
                                costEstimate = "5,000 - 8,000 ج",
                                status = if (currentFund >= 5000) "مغطاة بالكامل جاهزة للشراء ✅" else "قيد التجميع (${currentFund}/5000 ج)"
                            )

                            PrizeAllocationRow(
                                title = "المركز الثاني 🥈",
                                itemDesc = "تابلت تعليمي للمذاكرة 10 بوصة",
                                costEstimate = "3,000 - 4,000 ج",
                                status = if (currentFund >= 8000) "مغطاة بالكامل ✅" else "سيتم توفيرها قريباً"
                            )

                            PrizeAllocationRow(
                                title = "المركز الثالث 🥉",
                                itemDesc = "ساعة ذكية + باقة كتب خارجية",
                                costEstimate = "1,500 ج",
                                status = "متاحة للتنفيذ 🎁"
                            )
                        }
                    }

                    // Official Admin Accounts for Collection
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, RainbowIndigo.copy(alpha = 0.25f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Payments, contentDescription = null, tint = RainbowIndigo)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "حسابات الاستلام الرسمية المعتمدة للطلاب",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF1E293B)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFF8FAFC),
                                modifier = Modifier.fillMaxWidth().clickable {
                                    clipboardManager.setText(AnnotatedString(viewModel.adminPrimaryPhone))
                                    copiedCodeText = "تم نسخ الرقم الموحد ${viewModel.adminPrimaryPhone}"
                                    showCopyDialog = true
                                }
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("📱 الرقم الموحد لكافة الشبكات والمحافظ:", fontSize = 11.5.sp, color = Color(0xFF64748B))
                                        Text(viewModel.adminPrimaryPhone, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = RainbowIndigo)
                                        Text("(فودافون كاش • اتصالات كاش • أورنج كاش • وي باي • واتساب)", fontSize = 10.sp, color = RainbowGreen)
                                    }
                                    Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, tint = RainbowIndigo)
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFF8FAFC),
                                modifier = Modifier.fillMaxWidth().clickable {
                                    clipboardManager.setText(AnnotatedString(viewModel.adminSecondaryPhone))
                                    copiedCodeText = "تم نسخ الرقم البديل ${viewModel.adminSecondaryPhone}"
                                    showCopyDialog = true
                                }
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("📞 الرقم الإضافي / البديل:", fontSize = 11.5.sp, color = Color(0xFF64748B))
                                        Text(viewModel.adminSecondaryPhone, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF334155))
                                    }
                                    Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, tint = Color(0xFF64748B))
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFF8FAFC),
                                modifier = Modifier.fillMaxWidth().clickable {
                                    clipboardManager.setText(AnnotatedString(viewModel.adminEmail))
                                    copiedCodeText = "تم نسخ البريد المعتمد ${viewModel.adminEmail}"
                                    showCopyDialog = true
                                }
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("🟣 حساب إنستاباي والبريد الرسمي:", fontSize = 11.5.sp, color = Color(0xFF64748B))
                                        Text(viewModel.adminEmail, fontWeight = FontWeight.Bold, fontSize = 13.5.sp, color = RainbowViolet)
                                    }
                                    Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, tint = RainbowViolet)
                                }
                            }
                        }
                    }
                }
            }
        }

        // TAB 2: توليد أكواد التفعيل المجانية
        if (selectedTab == 2) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "توليد أكواد التفعيل المجانية (Promo Codes) 🎟️",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.5.sp,
                                color = Color(0xFF1E293B)
                            )
                            Text(
                                text = "تتيح لك إهداء اشتراكات مجانية للمتفوقين أو الأقارب أو الأسر غير القادرة ليدخلوا المسابقة دون دفع أي رسوم:",
                                fontSize = 12.sp,
                                color = Color(0xFF64748B),
                                modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                            )

                            Button(
                                onClick = {
                                    val code = viewModel.generateNewPromoCode()
                                    copiedCodeText = code
                                    clipboardManager.setText(AnnotatedString(code))
                                    showCopyDialog = true
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = RainbowIndigo)
                            ) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("توليد كود تفعيل فوري ونسخه 📋", fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Text(
                        text = "الأكواد النشطة الحالية (${promoCodes.size}):",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.5.sp,
                        color = Color(0xFF334155)
                    )

                    promoCodes.forEach { code ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = "🎟️", fontSize = 18.sp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = code,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.5.sp,
                                        color = RainbowIndigo
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        clipboardManager.setText(AnnotatedString(code))
                                        copiedCodeText = code
                                        showCopyDialog = true
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "نسخ الكود",
                                        tint = Color(0xFF64748B),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // TAB 3: تغيير كلمة المرور
        if (selectedTab == 3) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier.size(42.dp).clip(CircleShape).background(Color(0xFFFEF3C7)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(24.dp))
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text("تغيير كلمة المرور للمشرف 🔐", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF0F172A))
                                    Text("يمكنك تعيين رمز سري جديد للدخول للوحة التحكم", fontSize = 12.sp, color = Color(0xFF475569))
                                }
                            }

                            Spacer(modifier = Modifier.height(18.dp))

                            OutlinedTextField(
                                value = newPinInput,
                                onValueChange = {
                                    newPinInput = it
                                    changePinError = null
                                    changePinSuccess = false
                                },
                                label = { Text("كلمة السر الجديدة (4 خانات على الأقل)") },
                                placeholder = { Text("مثال: 5599 أو admin2026") },
                                visualTransformation = if (isNewPinVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    IconButton(onClick = { isNewPinVisible = !isNewPinVisible }) {
                                        Icon(
                                            imageVector = if (isNewPinVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                            contentDescription = null,
                                            tint = RainbowIndigo
                                        )
                                    }
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color(0xFF0F172A),
                                    unfocusedTextColor = Color(0xFF0F172A),
                                    focusedContainerColor = Color(0xFFF8FAFC),
                                    unfocusedContainerColor = Color(0xFFF8FAFC),
                                    focusedBorderColor = RainbowIndigo
                                ),
                                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A)),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = confirmPinInput,
                                onValueChange = {
                                    confirmPinInput = it
                                    changePinError = null
                                    changePinSuccess = false
                                },
                                label = { Text("تأكيد كلمة السر الجديدة") },
                                placeholder = { Text("أعد كتابة نفس الرمز للتأكيد") },
                                visualTransformation = if (isNewPinVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color(0xFF0F172A),
                                    unfocusedTextColor = Color(0xFF0F172A),
                                    focusedContainerColor = Color(0xFFF8FAFC),
                                    unfocusedContainerColor = Color(0xFFF8FAFC),
                                    focusedBorderColor = RainbowIndigo
                                ),
                                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A)),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true
                            )

                            if (changePinError != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = changePinError!!, color = RainbowRed, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            }

                            if (changePinSuccess) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFECFDF5),
                                    border = BorderStroke(1.dp, RainbowGreen.copy(alpha = 0.5f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "✅ تم تغيير وحفظ كلمة المرور بنجاح! يمكنك الآن استخدام الرمز الجديد للدخول في أي وقت.",
                                        color = Color(0xFF065F46),
                                        fontSize = 12.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    if (newPinInput.trim().length < 4) {
                                        changePinError = "كلمة المرور يجب ألا تقل عن 4 خانات"
                                    } else if (newPinInput.trim() != confirmPinInput.trim()) {
                                        changePinError = "كلمتا المرور غير متطابقتين"
                                    } else {
                                        val ok = viewModel.changeAdminPin(newPinInput.trim())
                                        if (ok) {
                                            changePinSuccess = true
                                            changePinError = null
                                        } else {
                                            changePinError = "حدث خطأ أثناء حفظ كلمة السر"
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxWidth().height(48.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = RainbowIndigo)
                            ) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("حفظ وتحديث كلمة المرور الجديدة", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                        }
                    }
                }
            }
        }

        // TAB 4: التنبيه العام
        if (selectedTab == 4) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "تعديل الإشعار العام لجميع الطلاب 📢",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.5.sp,
                                color = Color(0xFF1E293B)
                            )
                            Text(
                                text = "النص المكتوب هنا سيظهر في شريط بارز أعلى الصفحة الرئيسية لكل من يفتح التطبيق:",
                                fontSize = 12.sp,
                                color = Color(0xFF64748B),
                                modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                            )

                            OutlinedTextField(
                                value = newAnnouncementText,
                                onValueChange = {
                                    newAnnouncementText = it
                                    announcementSuccess = false
                                },
                                label = { Text("نص الإشعار") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                maxLines = 4
                            )

                            if (announcementSuccess) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "تم تحديث الإعلان العام ويظهر الآن لجميع الطلاب بنجاح! ✅",
                                    color = RainbowGreen,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Button(
                                onClick = {
                                    viewModel.setAnnouncement(newAnnouncementText)
                                    announcementSuccess = true
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = RainbowGreen)
                            ) {
                                Icon(imageVector = Icons.Default.NotificationsActive, contentDescription = null)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("حفظ ونشر الإشعار العام", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showCopyDialog) {
        AlertDialog(
            onDismissRequest = { showCopyDialog = false },
            title = { Text("تم نسخ الكود بنجاح 📋", fontWeight = FontWeight.Bold) },
            text = { Text("تم نسخ كود التفعيل ($copiedCodeText). يمكنك إرساله للطالب في رسالة عبر واتساب ليقوم بتفعيل حسابه فوراً!") },
            confirmButton = {
                TextButton(onClick = { showCopyDialog = false }) {
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

@Composable
private fun StatMetricCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color.White.copy(alpha = 0.12f),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)
            Text(text = title, color = Color(0xFFCBD5E1), fontSize = 10.sp)
        }
    }
}

@Composable
private fun SubscriptionRequestItem(
    request: SubscriptionRequest,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = request.studentName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF1E293B)
                    )
                    Text(
                        text = "${request.governorate} • ${request.date}",
                        fontSize = 11.5.sp,
                        color = Color(0xFF64748B)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (request.status.contains("مفعّل")) RainbowGreen.copy(alpha = 0.12f) else RainbowOrange.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = request.status,
                        color = if (request.status.contains("مفعّل")) RainbowGreen else RainbowOrange,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "الهاتف: ${request.phone} • عبر: ${request.paymentMethod}",
                    fontSize = 11.5.sp,
                    color = Color(0xFF334155),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${request.amount} ج.م",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 13.sp,
                    color = RainbowIndigo
                )
            }

            if (!request.status.contains("مفعّل")) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onApprove,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RainbowGreen),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("تفعيل الاشتراك ✅", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = onReject,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, RainbowRed),
                        modifier = Modifier.weight(0.6f)
                    ) {
                        Text("رفض", color = RainbowRed, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun PrizeAllocationRow(
    title: String,
    itemDesc: String,
    costEstimate: String,
    status: String
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF1E293B))
                Text(text = costEstimate, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = RainbowIndigo)
            }
            Text(text = itemDesc, fontSize = 12.sp, color = Color(0xFF475569))
            Text(text = "الحالة: $status", fontSize = 11.sp, color = RainbowGreen, fontWeight = FontWeight.Medium)
        }
    }
}
