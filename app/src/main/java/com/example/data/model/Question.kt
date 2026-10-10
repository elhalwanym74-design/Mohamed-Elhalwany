package com.example.data.model

data class Question(
    val id: String,
    val subject: Subject,
    val unit: String,
    val type: QuestionType,
    val questionText: String,
    val options: List<String> = emptyList(), // For MCQ
    val correctAnswerIndex: Int = -1,        // For MCQ
    val modelAnswer: String,                 // Full model answer
    val jannatTip: String,                   // نصيحة جنات الذكية 💡
    val examFrequencyBadge: String,          // e.g. "سؤال متكرر في 5 محافظات 2025 / 2026"
    val difficulty: String = "متوسط",         // سهل، متوسط، متفوقين
    val isImportant: Boolean = true
)
