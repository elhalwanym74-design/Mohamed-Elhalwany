package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import com.example.ui.components.PrintExamDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.QuestionType
import com.example.data.model.Subject
import com.example.ui.components.JannatTipBox
import com.example.ui.components.RainbowProgressBar
import com.example.ui.theme.JannatGold
import com.example.ui.theme.RainbowCyan
import com.example.ui.theme.RainbowGreen
import com.example.ui.theme.RainbowHorizontalGradient
import com.example.ui.theme.RainbowIndigo
import com.example.ui.theme.RainbowOrange
import com.example.ui.theme.RainbowRed
import com.example.ui.theme.RainbowSpectrumColors
import com.example.ui.theme.RainbowViolet
import com.example.ui.viewmodel.JannatViewModel

@Composable
fun ExamSimulatorScreen(
    viewModel: JannatViewModel,
    modifier: Modifier = Modifier
) {
    val examState by viewModel.examState.collectAsState()

    if (!examState.isActive) {
        // Setup screen: Pick subject & question count
        ExamSetupView(
            onStart = { sub, count -> viewModel.startExam(sub, count) },
            modifier = modifier
        )
    } else if (examState.isFinished) {
        // Results Screen
        ExamResultsView(
            examState = examState,
            onRestart = { viewModel.startExam(examState.subject, examState.totalCount) },
            onExit = { viewModel.exitExam() },
            modifier = modifier
        )
    } else {
        // Ongoing Exam Screen
        ActiveExamView(
            examState = examState,
            onSelectAnswer = { viewModel.selectExamAnswer(it) },
            onNext = { viewModel.nextExamQuestion() },
            onPrev = { viewModel.prevExamQuestion() },
            onFinish = { viewModel.finishExam() },
            onExit = { viewModel.exitExam() },
            modifier = modifier
        )
    }
}

@Composable
fun ExamSetupView(
    onStart: (Subject, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSubject by remember { mutableStateOf(Subject.ALL) }
    var selectedCount by remember { mutableStateOf(10) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("exam_setup_view")
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            // Big Rainbow Icon
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                RainbowIndigo,
                                RainbowViolet,
                                RainbowOrange
                            )
                        )
                    )
                    .border(3.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "📝", fontSize = 40.sp)
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "محاكي امتحانات نصف العام المتوقعة",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF0F172A),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "اختبارات تفاعلية محاكية لورقة الامتحان الرسمية بالوقت والتصحيح النموذجي المعتمد من جنات 🌈",
                fontSize = 13.5.sp,
                color = Color(0xFF64748B),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Choose Subject
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "1. اختر المادة:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color(0xFF1E293B)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    val subjects = Subject.values()
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        subjects.forEach { subject ->
                            val isSelected = selectedSubject == subject
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { selectedSubject = subject }
                                    .testTag("setup_subject_${subject.id}"),
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) subject.rainbowColor.copy(alpha = 0.12f) else Color(0xFFF8FAFC),
                                border = androidx.compose.foundation.BorderStroke(
                                    1.5.dp,
                                    if (isSelected) subject.rainbowColor else Color(0xFFE2E8F0)
                                )
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = subject.iconEmoji, fontSize = 20.sp)
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(
                                                text = subject.titleArabic,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                fontSize = 14.sp,
                                                color = if (isSelected) subject.rainbowColor else Color(0xFF334155)
                                            )
                                            Text(
                                                text = subject.subtitle,
                                                fontSize = 11.sp,
                                                color = Color(0xFF64748B)
                                            )
                                        }
                                    }

                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "Selected",
                                            tint = subject.rainbowColor,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Choose Question Count
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "2. عدد أسئلة الامتحان:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color(0xFF1E293B)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        listOf(5, 10, 15).forEach { count ->
                            val isSelected = selectedCount == count
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { selectedCount = count }
                                    .testTag("count_btn_$count"),
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) RainbowIndigo else Color(0xFFF1F5F9),
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isSelected) RainbowIndigo else Color(0xFFCBD5E1)
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "$count",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = if (isSelected) Color.White else Color(0xFF334155)
                                    )
                                    Text(
                                        text = "سؤال",
                                        fontSize = 11.5.sp,
                                        color = if (isSelected) Color.White.copy(alpha = 0.9f) else Color(0xFF64748B)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Start Exam Button
        item {
            Button(
                onClick = { onStart(selectedSubject, selectedCount) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("start_exam_btn"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = RainbowIndigo)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Start",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ابدأ الامتحان المتوقع الآن 🌈",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun ActiveExamView(
    examState: com.example.ui.viewmodel.ExamState,
    onSelectAnswer: (Int) -> Unit,
    onNext: () -> Unit,
    onPrev: () -> Unit,
    onFinish: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentQuestion = examState.questions.getOrNull(examState.currentIndex)
    val chosenOption = examState.selectedAnswers[examState.currentIndex]
    val minutes = examState.timeRemainingSeconds / 60
    val seconds = examState.timeRemainingSeconds % 60
    val timeFormatted = String.format("%02d:%02d", minutes, seconds)
    val isTimeLow = examState.timeRemainingSeconds < 60

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("active_exam_view")
            .padding(16.dp)
    ) {
        // Top Bar: Timer, Subject Badge & Exit
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = examState.subject.rainbowColor.copy(alpha = 0.15f)
            ) {
                Text(
                    text = "${examState.subject.iconEmoji} ${examState.subject.titleArabic}",
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    color = examState.subject.rainbowColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }

            // Timer Pill
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (isTimeLow) RainbowRed.copy(alpha = 0.15f) else Color(0xFFF1F5F9),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isTimeLow) RainbowRed else Color(0xFFCBD5E1)
                )
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = "Timer",
                        tint = if (isTimeLow) RainbowRed else RainbowIndigo,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = timeFormatted,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = if (isTimeLow) RainbowRed else Color(0xFF1E293B)
                    )
                }
            }

            // Exit Button
            Surface(
                shape = CircleShape,
                color = Color(0xFFF1F5F9),
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .clickable { onExit() }
                    .testTag("exit_exam_btn")
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Exit",
                        tint = Color(0xFF64748B),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Progress text & Rainbow Progress Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "السؤال ${examState.currentIndex + 1} من ${examState.totalCount}",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = Color(0xFF334155)
            )
            Text(
                text = "الدرجة: ${examState.selectedAnswers.size} / ${examState.totalCount} تم الحل",
                fontSize = 12.sp,
                color = RainbowIndigo,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        val progress = (examState.currentIndex + 1).toFloat() / examState.totalCount.toFloat()
        RainbowProgressBar(progress = progress)

        Spacer(modifier = Modifier.height(14.dp))

        // Question Details & Card
        if (currentQuestion != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(18.dp)
                ) {
                    item {
                        // Badge
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = RainbowOrange.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = currentQuestion.type.titleArabic,
                                    color = RainbowOrange,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.5.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = currentQuestion.unit,
                                fontSize = 11.5.sp,
                                color = Color(0xFF64748B)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Question Text
                        Text(
                            text = currentQuestion.questionText,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.5.sp,
                                color = Color(0xFF0F172A),
                                lineHeight = 24.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(18.dp))
                    }

                    // If MCQ: show options
                    if (currentQuestion.type == QuestionType.MCQ && currentQuestion.options.isNotEmpty()) {
                        items(currentQuestion.options.size) { index ->
                            val option = currentQuestion.options[index]
                            val isSelected = chosenOption == index
                            val optionLetter = listOf("أ", "ب", "ج", "د").getOrElse(index) { "${index + 1}" }

                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .clickable { onSelectAnswer(index) }
                                    .testTag("exam_option_$index"),
                                shape = RoundedCornerShape(14.dp),
                                color = if (isSelected) RainbowIndigo.copy(alpha = 0.12f) else Color(0xFFF8FAFC),
                                border = androidx.compose.foundation.BorderStroke(
                                    1.5.dp,
                                    if (isSelected) RainbowIndigo else Color(0xFFE2E8F0)
                                )
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(if (isSelected) RainbowIndigo else Color(0xFFE2E8F0)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = optionLetter,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = if (isSelected) Color.White else Color(0xFF475569)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = option,
                                        fontSize = 14.5.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) RainbowIndigo else Color(0xFF1E293B)
                                    )
                                }
                            }
                        }
                    } else {
                        // For essay/written questions in exam mode
                        item {
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFF0FDF4),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBBF7D0))
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text(
                                        text = "فكر في الإجابة واكتبها في مسودتك، ثم اضغط (سجلت إجابتي):",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = Color(0xFF166534)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    val isAnsweredWritten = chosenOption != null
                                    Button(
                                        onClick = { onSelectAnswer(1) },
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isAnsweredWritten) RainbowGreen else Color(0xFF334155)
                                        )
                                    ) {
                                        Text(
                                            text = if (isAnsweredWritten) "تم تسجيل إجابتي بنجاح ✔" else "سجلت إجابتي في ورقة الإجابة"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Navigation Footer Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = onPrev,
                enabled = examState.currentIndex > 0,
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "السابق")
                Spacer(modifier = Modifier.width(6.dp))
                Text("السابق")
            }

            if (examState.currentIndex == examState.totalCount - 1) {
                Button(
                    onClick = onFinish,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RainbowGreen),
                    modifier = Modifier.testTag("finish_exam_btn")
                ) {
                    Text("تصحيح الامتحان 🏆", fontWeight = FontWeight.Bold)
                }
            } else {
                Button(
                    onClick = onNext,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RainbowIndigo),
                    modifier = Modifier.testTag("next_exam_btn")
                ) {
                    Text("التالي")
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "التالي")
                }
            }
        }
    }
}

@Composable
fun ExamResultsView(
    examState: com.example.ui.viewmodel.ExamState,
    onRestart: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val percentage = if (examState.totalCount > 0) (examState.score * 100) / examState.totalCount else 0
    var showPrintDialog by remember { mutableStateOf(false) }

    val (gradeTitle, jannatFeedback, badgeColor) = when {
        percentage >= 90 -> Triple(
            "درجة نهائية أسطورية! 🏆🌟",
            "ما شاء الله عليك! مستواك ممتاز وجاهز لتقفيل درجات الترم الأول بالكامل. جنات فخورة بك جداً!",
            RainbowGreen
        )
        percentage >= 70 -> Triple(
            "أحسنت يا بطل! مستوى متقدم 💪",
            "أداء رائع جداً! بقليل من التركيز ومراجعة الأسئلة التي تعثرت فيها ستحقق الدرجة النهائية!",
            RainbowCyan
        )
        percentage >= 50 -> Triple(
            "مجهود جيد وتحتاج إلى تثبيت المعلومات 📖",
            "بداية طيبة، راجع نصائح جنات الذكية وبطاقات المراجعة السريعة وحاول مرة ثانية لتصل لقمة الطيف!",
            RainbowOrange
        )
        else -> Triple(
            "فرصة للتعلم وتدارك الأخطاء 🌈",
            "لا تقلق! هذا اختبار تجريبي للتعلم، اقرأ الإجابات النموذجية بالأسفل وافهم سر كل سؤال مع جنات.",
            RainbowRed
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("exam_results_view")
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Result Score Card with Rainbow Gradient Ring
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                border = androidx.compose.foundation.BorderStroke(2.dp, RainbowHorizontalGradient)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .border(6.dp, Brush.sweepGradient(RainbowSpectrumColors), CircleShape)
                            .background(Color(0xFFF8FAFC)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$percentage%",
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp,
                                color = RainbowIndigo
                            )
                            Text(
                                text = "${examState.score} / ${examState.totalCount}",
                                fontSize = 12.sp,
                                color = Color(0xFF64748B)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = gradeTitle,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF0F172A),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    JannatTipBox(tipText = jannatFeedback)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons: Restart & Back
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onRestart,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RainbowIndigo)
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Restart")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("إعادة الاختبار", fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = onExit,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("الرئيسية")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Print Exam Button (HD)
            Button(
                onClick = { showPrintDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .testTag("print_exam_result_btn"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A))
            ) {
                Icon(
                    imageVector = Icons.Default.Print,
                    contentDescription = "طباعة",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "طباعة ورقة الامتحان ونموذج الإجابة (PDF HD) 🖨️",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.5.sp,
                    color = Color.White
                )
            }

            if (showPrintDialog) {
                PrintExamDialog(
                    subject = examState.subject ?: Subject.SCIENCE,
                    unitTitle = "امتحان تجريبي كامل - النتيجة المحققة: $percentage%",
                    questions = examState.questions,
                    onDismiss = { showPrintDialog = false }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "مراجعة نموذج إجابة الامتحان بالتفصيل 🔍",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF0F172A),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        // Detailed Review of Each Question
        items(examState.questions.size) { index ->
            val q = examState.questions[index]
            val chosen = examState.selectedAnswers[index]
            val isCorrect = if (q.type == QuestionType.MCQ) chosen == q.correctAnswerIndex else chosen != null

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isCorrect) RainbowGreen.copy(alpha = 0.5f) else RainbowRed.copy(alpha = 0.5f)
                )
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isCorrect) Color(0xFFECFDF5) else Color(0xFFFEF2F2)
                        ) {
                            Text(
                                text = if (isCorrect) "إجابة صحيحة ✔" else "إجابة تحتاج مراجعة ✖",
                                color = if (isCorrect) RainbowGreen else RainbowRed,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.5.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        Text(
                            text = q.subject.titleArabic,
                            fontSize = 11.sp,
                            color = q.subject.rainbowColor,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${index + 1}. ${q.questionText}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF1E293B)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "الإجابة النموذجية: ${q.modelAnswer}",
                        fontSize = 12.5.sp,
                        color = Color(0xFF15803D),
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "💡 نصيحة جنات: ${q.jannatTip}",
                        fontSize = 11.5.sp,
                        color = Color(0xFFB45309)
                    )
                }
            }
        }
    }
}
