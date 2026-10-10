package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.JannatTipBox
import com.example.ui.theme.JannatGold
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class RainbowBadge(
    val title: String,
    val description: String,
    val color: Color,
    val emoji: String,
    val isUnlocked: Boolean
)

@Composable
fun AchievementsScreen(
    viewModel: JannatViewModel,
    modifier: Modifier = Modifier
) {
    val streakDays by viewModel.streakDays.collectAsState()
    val starsCount by viewModel.starsCount.collectAsState()
    val examHistory by viewModel.examHistory.collectAsState()

    val rainbowBadges = listOf(
        RainbowBadge(
            title = "وسام الطيف الأحمر 🔴",
            description = "حل أول امتحان تجريبي بنجاح",
            color = RainbowRed,
            emoji = "🏆",
            isUnlocked = true
        ),
        RainbowBadge(
            title = "وسام الطيف البرتقالي 🟠",
            description = "إتقان أسئلة كواكب المجموعة وفصول السنة",
            color = RainbowOrange,
            emoji = "🌍",
            isUnlocked = true
        ),
        RainbowBadge(
            title = "وسام الطيف الأصفر 🟡",
            description = "حفظ وتفسير وصايا لقمان الحكيم",
            color = RainbowYellow,
            emoji = "🕌",
            isUnlocked = true
        ),
        RainbowBadge(
            title = "وسام الطيف الأخضر 🟢",
            description = "التميز في مفردات وقواعد اللغة الإنجليزية",
            color = RainbowGreen,
            emoji = "🇬🇧",
            isUnlocked = starsCount >= 100
        ),
        RainbowBadge(
            title = "وسام الطيف السماوي 🔵",
            description = "حل مسائل الكثافة والطاقة في العلوم",
            color = RainbowCyan,
            emoji = "🔬",
            isUnlocked = starsCount >= 150
        ),
        RainbowBadge(
            title = "وسام الطيف النيلي 🔷",
            description = "العبقرية في الأعداد النسبية والهندسة",
            color = RainbowIndigo,
            emoji = "📐",
            isUnlocked = starsCount >= 200
        ),
        RainbowBadge(
            title = "وسام الطيف البنفسجي 🟣",
            description = "تقفيل إعراب ونحو اللغة العربية",
            color = RainbowViolet,
            emoji = "📖",
            isUnlocked = starsCount >= 250
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("achievements_screen")
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // Stats Overview Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Color(0xFF3F37C9),
                                    Color(0xFF4361EE),
                                    Color(0xFF7209B7)
                                )
                            )
                        )
                        .padding(18.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "لوحة إنجازات بطل الإعدادية 🌟",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                )
                                Text(
                                    text = "كل سؤال تحله يقربك من المركز الأول!",
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontSize = 12.sp
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "👑", fontSize = 28.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Stats counters row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Surface(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(14.dp),
                                color = Color.White.copy(alpha = 0.15f)
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "🌈 $streakDays أيام", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 15.sp)
                                    Text(text = "سلسلة التفوق", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp)
                                }
                            }

                            Surface(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(14.dp),
                                color = Color.White.copy(alpha = 0.15f)
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "⭐ $starsCount", fontWeight = FontWeight.Bold, color = JannatGold, fontSize = 15.sp)
                                    Text(text = "نجوم التميز", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp)
                                }
                            }

                            Surface(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(14.dp),
                                color = Color.White.copy(alpha = 0.15f)
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "📝 ${examHistory.size}", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 15.sp)
                                    Text(text = "امتحانات منجزة", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "أوسمة الطيف السبعة للامتحانات 🌈",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Color(0xFF0F172A)
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        // Rainbow Badges List
        items(rainbowBadges) { badge ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (badge.isUnlocked) badge.color.copy(alpha = 0.4f) else Color(0xFFE2E8F0)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(if (badge.isUnlocked) badge.color.copy(alpha = 0.15f) else Color(0xFFF1F5F9)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = badge.emoji, fontSize = 22.sp)
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = badge.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.5.sp,
                                color = if (badge.isUnlocked) Color(0xFF0F172A) else Color(0xFF64748B)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = badge.description,
                                fontSize = 12.sp,
                                color = Color(0xFF64748B)
                            )
                        }
                    }

                    if (badge.isUnlocked) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFECFDF5)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Unlocked",
                                tint = RainbowGreen,
                                modifier = Modifier.padding(6.dp).size(20.dp)
                            )
                        }
                    } else {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFF1F5F9)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Locked",
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.padding(6.dp).size(20.dp)
                            )
                        }
                    }
                }
            }
        }

        // Exam History Section
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "سجل درجات الامتحانات السابقة 📊",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Color(0xFF0F172A)
            )
            Spacer(modifier = Modifier.height(10.dp))

            if (examHistory.isEmpty()) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "📝", fontSize = 32.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "لم تقم بحل أي امتحان بعد",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF334155)
                        )
                        Text(
                            text = "ابدأ أول امتحان متوقع لترى سجلك ودرجاتك هنا!",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }
            }
        }

        items(examHistory) { result ->
            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ar"))
            val formattedDate = dateFormat.format(Date(result.timestamp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "امتحان: ${result.subjectTitle}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF1E293B)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "$formattedDate • ${result.timeSpentSeconds} ثانية",
                            fontSize = 11.5.sp,
                            color = Color(0xFF64748B)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (result.percentage >= 70) Color(0xFFECFDF5) else Color(0xFFFFF7ED)
                    ) {
                        Text(
                            text = "${result.percentage}% (${result.score}/${result.totalQuestions})",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = if (result.percentage >= 70) RainbowGreen else RainbowOrange,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(18.dp))
            JannatTipBox(
                tipText = "الأبطال لا يستسلمون! كل خطأ تكتشفه في نماذج الامتحانات هو درجة مضمونة إضافية في امتحان نصف العام!"
            )
        }
    }
}
