package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RotateRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.QuestionBank
import com.example.ui.components.JannatTipBox
import com.example.ui.theme.RainbowGreen
import com.example.ui.theme.RainbowHorizontalGradient
import com.example.ui.theme.RainbowIndigo
import com.example.ui.theme.RainbowOrange
import com.example.ui.theme.RainbowSpectrumColors
import com.example.ui.viewmodel.JannatViewModel

@Composable
fun FlashcardsScreen(
    viewModel: JannatViewModel,
    modifier: Modifier = Modifier
) {
    val currentIndex by viewModel.currentFlashcardIndex.collectAsState()
    val isFlipped by viewModel.isCardFlipped.collectAsState()
    val flashcards = QuestionBank.flashcards
    val currentCard = flashcards.getOrNull(currentIndex)

    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "card_flip"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("flashcards_screen")
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "بطاقات المراجعة السريعة 🌈",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = "مراجعة ليلة الامتحان في نقاط مركزة",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B)
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = RainbowIndigo.copy(alpha = 0.12f)
            ) {
                Text(
                    text = "${currentIndex + 1} / ${flashcards.size}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = RainbowIndigo,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Flip Card Box with 3D Flip Effect
        if (currentCard != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp)
                    .graphicsLayer {
                        rotationY = rotation
                        cameraDistance = 12f * density
                    }
                    .clip(RoundedCornerShape(24.dp))
                    .clickable { viewModel.flipCard() }
                    .testTag("flashcard_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                border = androidx.compose.foundation.BorderStroke(2.dp, Brush.sweepGradient(RainbowSpectrumColors))
            ) {
                if (rotation <= 90f) {
                    // Front Side: The Question / Rule Name
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = currentCard.subject.rainbowColor.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "${currentCard.subject.iconEmoji} ${currentCard.subject.titleArabic}",
                                    color = currentCard.subject.rainbowColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            Text(
                                text = currentCard.category,
                                fontSize = 11.5.sp,
                                color = Color(0xFF64748B),
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(vertical = 16.dp)
                        ) {
                            Text(
                                text = "🤔",
                                fontSize = 42.sp
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = currentCard.frontTitle,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = Color(0xFF0F172A),
                                    textAlign = TextAlign.Center,
                                    lineHeight = 26.sp
                                )
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.RotateRight,
                                contentDescription = "Flip",
                                tint = RainbowIndigo,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "اضغط على البطاقة لقلبها ورؤية الحل 🔄",
                                fontSize = 12.sp,
                                color = RainbowIndigo,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                } else {
                    // Back Side: The Model Answer & Jannat Advice
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer { rotationY = 180f }
                            .padding(24.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = RainbowGreen.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "الإجابة النموذجية وقانون الحل 🎯",
                                color = RainbowGreen,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(vertical = 10.dp)
                        ) {
                            Text(
                                text = currentCard.backAnswer,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color(0xFF14532D),
                                    textAlign = TextAlign.Center,
                                    lineHeight = 24.sp
                                )
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFFFFBEB),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A))
                            ) {
                                Text(
                                    text = "💡 سر جنات: ${currentCard.jannatAdvice}",
                                    fontSize = 12.sp,
                                    color = Color(0xFF92400E),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(10.dp),
                                    lineHeight = 17.sp
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.RotateRight,
                                contentDescription = "Flip Back",
                                tint = Color(0xFF64748B),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "اضغط للعودة للسؤال 🔄",
                                fontSize = 11.5.sp,
                                color = Color(0xFF64748B)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Flip & Navigation Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { viewModel.prevFlashcard() },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "السابق")
                Spacer(modifier = Modifier.width(4.dp))
                Text("السابق")
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = { viewModel.flipCard() },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = RainbowIndigo),
                modifier = Modifier.weight(1.2f).testTag("flip_btn")
            ) {
                Icon(imageVector = Icons.Default.RotateRight, contentDescription = "اقلب")
                Spacer(modifier = Modifier.width(4.dp))
                Text("اقلب البطاقة")
            }

            Spacer(modifier = Modifier.width(10.dp))

            OutlinedButton(
                onClick = { viewModel.nextFlashcard() },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text("التالي")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "التالي")
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Mascot Tip
        JannatTipBox(
            tipText = "التكرار المتباعد للبطاقات يثبت المعلومات في الذاكرة طويلة المدى، راجع 5 بطاقات يومياً قبل النوم!"
        )
    }
}
