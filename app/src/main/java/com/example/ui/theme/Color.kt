package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// ==========================================
// 🌈 ألوان الطيف السبعة (The 7 Rainbow Spectrum Colors)
// ==========================================
val RainbowRed = Color(0xFFEF4444)       // أحمر الطيف
val RainbowOrange = Color(0xFFF97316)    // برتقالي الطيف
val RainbowYellow = Color(0xFFFBBF24)    // أصفر الطيف
val RainbowGreen = Color(0xFF10B981)     // أخضر الطيف
val RainbowCyan = Color(0xFF06B6D4)      // أزرق سماوي الطيف
val RainbowIndigo = Color(0xFF4361EE)    // نيلي الطيف الملكي
val RainbowViolet = Color(0xFF8B5CF6)    // بنفسجي الطيف

// Rainbow list
val RainbowSpectrumColors = listOf(
    RainbowRed,
    RainbowOrange,
    RainbowYellow,
    RainbowGreen,
    RainbowCyan,
    RainbowIndigo,
    RainbowViolet
)

val RainbowSpectrumPastel = listOf(
    Color(0xFFFFECEC),
    Color(0xFFFFF3E8),
    Color(0xFFFEF9C3),
    Color(0xFFE8F8F0),
    Color(0xFFE0F7FA),
    Color(0xFFEEF2FF),
    Color(0xFFF3E8FF)
)

// Gradients
val RainbowHorizontalGradient = Brush.horizontalGradient(RainbowSpectrumColors)
val RainbowVerticalGradient = Brush.verticalGradient(RainbowSpectrumColors)
val RainbowLinearGradient = Brush.linearGradient(RainbowSpectrumColors)

// Subject Signature Spectrum Colors
val SubjectScience = Color(0xFF06B6D4)       // أزرق سماوي (العلوم)
val SubjectScienceBg = Color(0xFFE0F7FA)

val SubjectMath = Color(0xFF4361EE)          // نيلي ملكي (الرياضيات)
val SubjectMathBg = Color(0xFFEEF2FF)

val SubjectSocial = Color(0xFFF97316)        // برتقالي ذهبي (الدراسات الاجتماعية)
val SubjectSocialBg = Color(0xFFFFF3E8)

val SubjectArabic = Color(0xFF8B5CF6)        // بنفسجي راقي (اللغة العربية)
val SubjectArabicBg = Color(0xFFF3E8FF)

val SubjectEnglish = Color(0xFF10B981)       // أخضر زمردي (اللغة الإنجليزية)
val SubjectEnglishBg = Color(0xFFE8F8F0)

val SubjectReligion = Color(0xFFD97706)      // كهرماني ذهبي (التربية الإسلامية)
val SubjectReligionBg = Color(0xFFFEF3C7)

val SubjectChristian = Color(0xFF0284C7)     // أزرق سماوي ملكي (التربية المسيحية)
val SubjectChristianBg = Color(0xFFF0F9FF)

// Supporting UI Colors (Optimized for maximum contrast & crisp readability)
val JannatPrimary = Color(0xFF4361EE)
val JannatSecondary = Color(0xFFF97316)
val JannatTertiary = Color(0xFF10B981)
val JannatBackground = Color(0xFFF8FAFC)
val JannatSurface = Color(0xFFFFFFFF)
val JannatCardBorder = Color(0xFFCBD5E1)
val JannatTextPrimary = Color(0xFF0F172A)    // فاحم شديد الوضوح وعالي التباين
val JannatTextSecondary = Color(0xFF334155)  // رمادي داكن واضح جداً ومقروء بامتياز
val JannatTextMuted = Color(0xFF475569)      // رمادي كحلي داكن بدلاً من الرمادي الباهت
val JannatSuccess = Color(0xFF059669)
val JannatError = Color(0xFFDC2626)
val JannatGold = Color(0xFFD97706)
