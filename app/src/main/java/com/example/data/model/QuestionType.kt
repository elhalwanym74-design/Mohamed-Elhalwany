package com.example.data.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.RainbowCyan
import com.example.ui.theme.RainbowGreen
import com.example.ui.theme.RainbowIndigo
import com.example.ui.theme.RainbowOrange
import com.example.ui.theme.RainbowRed
import com.example.ui.theme.RainbowViolet

enum class QuestionType(
    val id: String,
    val titleArabic: String,
    val iconEmoji: String,
    val rainbowColor: Color
) {
    ALL("all", "كل الأنواع", "✨", RainbowIndigo),
    MCQ("mcq", "اختر الإجابة الصحيحة", "🔘", RainbowCyan),
    EXPLAIN_WHY("explain_why", "علل وبم تفسر", "💡", RainbowOrange),
    WHAT_RESULTS("what_results", "ما النتائج المترتبة", "⚡", RainbowViolet),
    SCIENTIFIC_TERM("scientific_term", "المصطلح والمفهوم", "🏷️", RainbowGreen),
    TRUE_FALSE("true_false", "صح أم خطأ مع التصويب", "⚖️", RainbowRed),
    PROBLEM_SOLVE("problem_solve", "مسائل وتطبيقات", "🧮", RainbowIndigo)
}
