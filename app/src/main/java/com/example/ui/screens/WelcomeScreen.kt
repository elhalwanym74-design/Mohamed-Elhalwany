package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.RainbowCyan
import com.example.ui.theme.RainbowGreen
import com.example.ui.theme.RainbowHorizontalGradient
import com.example.ui.theme.RainbowIndigo
import com.example.ui.theme.RainbowOrange
import com.example.ui.theme.RainbowRed
import com.example.ui.theme.RainbowSpectrumColors
import com.example.ui.theme.RainbowViolet
import com.example.ui.theme.RainbowYellow
import com.example.ui.viewmodel.JannatViewModel

@Composable
fun WelcomeScreen(
    viewModel: JannatViewModel,
    onStartClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val userGovernorate by viewModel.userGovernorate.collectAsState()
    var showGovDialog by remember { mutableStateOf(false) }

    // Pulsing & Floating Animations
    val infiniteTransition = rememberInfiniteTransition(label = "rainbow_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    val haloRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(9000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "halo_rotation"
    )

    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -4f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float_anim"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFEF9C3).copy(alpha = 0.35f), // Yellow tint
                        Color(0xFFF0FDF4).copy(alpha = 0.45f), // Green tint
                        Color(0xFFEFF6FF).copy(alpha = 0.60f), // Indigo tint
                        Color(0xFFFAF5FF)                      // Violet tint
                    )
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("welcome_screen_root")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Top Rainbow Arches Indicator
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(top = 8.dp, bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    RainbowSpectrumColors.forEachIndexed { index, color ->
                        Box(
                            modifier = Modifier
                                .size(width = 24.dp, height = 4.5.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(color)
                        )
                    }
                }

                // Friendly Welcome Badge
                Surface(
                    shape = RoundedCornerShape(30.dp),
                    color = Color.White,
                    shadowElevation = 3.dp,
                    border = BorderStroke(1.2.dp, RainbowViolet.copy(alpha = 0.35f)),
                    modifier = Modifier
                        .offset(y = floatOffset.dp)
                        .testTag("welcome_badge")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "🎈", fontSize = 15.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "أهلاً بك في منصة الأوائل والمتفوقين 2026",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = RainbowIndigo,
                            fontFamily = CairoFontFamily
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "✨", fontSize = 15.sp)
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Hero Avatar with Animated Rainbow Ring
                Box(
                    modifier = Modifier.size(136.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Rotating Rainbow Halo
                    Box(
                        modifier = Modifier
                            .size(132.dp)
                            .rotate(haloRotation)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.sweepGradient(RainbowSpectrumColors)
                            )
                    )

                    // Avatar Image Container
                    Box(
                        modifier = Modifier
                            .size(122.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_jannat_real_avatar_1790311360541),
                            contentDescription = "جنات - رفيقة تفوقك",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // Floating Star Badge
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(x = (-4).dp, y = (-4).dp)
                            .size(34.dp)
                            .scale(pulseScale)
                            .clip(CircleShape)
                            .background(RainbowYellow)
                            .shadow(4.dp, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "⭐", fontSize = 18.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 🌈 App Title: "تعلم مع جنات" with Cheerful Rainbow Spectrum Styling
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "🌈", fontSize = 26.sp)
                        Spacer(modifier = Modifier.width(8.dp))

                        // Gradient Styled Title
                        Text(
                            text = "تعلم مع جنات",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = CairoFontFamily,
                            color = RainbowIndigo,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.testTag("welcome_app_title")
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "✨", fontSize = 26.sp)
                    }

                    // Cheerful Color Pills for the 7 Rainbow Spectrum Colors
                    Row(
                        modifier = Modifier
                            .padding(top = 4.dp, bottom = 8.dp)
                            .fillMaxWidth(0.85f),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = RainbowRed.copy(alpha = 0.12f),
                            modifier = Modifier.padding(horizontal = 2.dp)
                        ) {
                            Text(
                                text = "شغف",
                                color = RainbowRed,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = RainbowOrange.copy(alpha = 0.12f),
                            modifier = Modifier.padding(horizontal = 2.dp)
                        ) {
                            Text(
                                text = "ذكاء",
                                color = RainbowOrange,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = RainbowGreen.copy(alpha = 0.12f),
                            modifier = Modifier.padding(horizontal = 2.dp)
                        ) {
                            Text(
                                text = "تفوق",
                                color = RainbowGreen,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = RainbowCyan.copy(alpha = 0.12f),
                            modifier = Modifier.padding(horizontal = 2.dp)
                        ) {
                            Text(
                                text = "فهم",
                                color = RainbowCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = RainbowViolet.copy(alpha = 0.12f),
                            modifier = Modifier.padding(horizontal = 2.dp)
                        ) {
                            Text(
                                text = "نجاح 100%",
                                color = RainbowViolet,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(
                        text = "منهج الصف الأول الإعدادي المطور • نماذج الوزارة المعتمدة",
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF475569),
                        fontFamily = CairoFontFamily,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "رحلتك الممتعة والشاملة لحصد الدرجات النهائية في جميع المواد 🏆",
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF64748B),
                        fontFamily = CairoFontFamily,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 2.dp, start = 12.dp, end = 12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Governorate Quick Confirmation Chip
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    shadowElevation = 2.dp,
                    border = BorderStroke(1.2.dp, RainbowCyan.copy(alpha = 0.5f)),
                    modifier = Modifier
                        .clickable { showGovDialog = true }
                        .testTag("welcome_gov_chip")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "محافظتك",
                            tint = RainbowCyan,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "محافظتك المختارة: ",
                            fontSize = 12.sp,
                            color = Color(0xFF475569),
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = userGovernorate,
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = RainbowIndigo
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.ExpandMore,
                            contentDescription = "تغيير",
                            tint = Color(0xFF94A3B8),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Feature Highlights in Rainbow Spectrum Colors
                Text(
                    text = "🌟 مميزات حصرية تجعل التفوق مضموناً:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    textAlign = TextAlign.Start
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    WelcomeFeatureCard(
                        iconEmoji = "📚",
                        title = "بنك الأسئلة الشامل ونماذج الوزارة",
                        desc = "مئات الأسئلة المتوقعة بنماذج إجابة معتمدة وتوزيع الدرجات",
                        accentColor = RainbowCyan,
                        bgColor = Color(0xFFE0F7FA).copy(alpha = 0.6f)
                    )

                    WelcomeFeatureCard(
                        iconEmoji = "📇",
                        title = "بطاقات ليلة الامتحان السريعة",
                        desc = "تلخيص القوانين والمصطلحات والخرائط للمراجعة في دقائق",
                        accentColor = RainbowGreen,
                        bgColor = Color(0xFFE8F8F0).copy(alpha = 0.6f)
                    )

                    WelcomeFeatureCard(
                        iconEmoji = "🤖",
                        title = "اسأل جنات (المعلم الذكي)",
                        desc = "شرح فوري وتبسيط المسائل الصعبة خطوة بخطوة 24 ساعة",
                        accentColor = RainbowOrange,
                        bgColor = Color(0xFFFFF3E8).copy(alpha = 0.6f)
                    )

                    WelcomeFeatureCard(
                        iconEmoji = "🏆",
                        title = "أولمبياد المتفوقين والجوائز الكبرى",
                        desc = "تنافس مع أوائل المحافظات واجمع النجوم والأوسمة وشهادات التقدير",
                        accentColor = RainbowViolet,
                        bgColor = Color(0xFFF3E8FF).copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // Action Buttons Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                // Primary Start Button with Rainbow Horizontal Gradient
                Button(
                    onClick = onStartClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .scale(pulseScale)
                        .shadow(8.dp, RoundedCornerShape(27.dp))
                        .testTag("welcome_start_button"),
                    shape = RoundedCornerShape(27.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(RainbowHorizontalGradient),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "ابدأ رحلة التفوق الآن 🚀",
                                color = Color.White,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = CairoFontFamily
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = RainbowYellow,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Bottom Reassurance text
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = null,
                        tint = RainbowGreen,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "متاح ومجاني لجميع طلبة الصف الأول الإعدادي • بالتوفيق والدرجة النهائية 💯",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF475569),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    if (showGovDialog) {
        AlertDialog(
            onDismissRequest = { showGovDialog = false },
            title = {
                Text(
                    text = "اختر محافظتك 🇪🇬",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = RainbowIndigo,
                    fontFamily = CairoFontFamily
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                ) {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(viewModel.allGovernorates) { gov ->
                            val isChosen = gov == userGovernorate
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isChosen) RainbowCyan.copy(alpha = 0.15f) else Color(0xFFF8FAFC),
                                border = BorderStroke(
                                    1.dp,
                                    if (isChosen) RainbowCyan else Color(0xFFE2E8F0)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.setUserGovernorate(gov)
                                        showGovDialog = false
                                    }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = gov,
                                        fontSize = 13.sp,
                                        fontWeight = if (isChosen) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isChosen) RainbowCyan else Color(0xFF1E293B)
                                    )
                                    if (isChosen) {
                                        Text(text = "✔", color = RainbowCyan, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showGovDialog = false }) {
                    Text(text = "تم", fontWeight = FontWeight.Bold, color = RainbowIndigo)
                }
            }
        )
    }
}

@Composable
fun WelcomeFeatureCard(
    iconEmoji: String,
    title: String,
    desc: String,
    accentColor: Color,
    bgColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.2.dp, accentColor.copy(alpha = 0.35f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                Text(text = iconEmoji, fontSize = 22.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.5.sp,
                    color = Color(0xFF0F172A),
                    fontFamily = CairoFontFamily
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = desc,
                    fontSize = 11.5.sp,
                    color = Color(0xFF475569),
                    fontWeight = FontWeight.Normal,
                    fontFamily = CairoFontFamily,
                    lineHeight = 16.sp
                )
            }
        }
    }
}
