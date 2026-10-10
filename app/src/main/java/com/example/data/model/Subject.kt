package com.example.data.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.RainbowCyan
import com.example.ui.theme.RainbowGreen
import com.example.ui.theme.RainbowIndigo
import com.example.ui.theme.RainbowOrange
import com.example.ui.theme.RainbowViolet
import com.example.ui.theme.RainbowYellow
import com.example.ui.theme.SubjectArabicBg
import com.example.ui.theme.SubjectEnglishBg
import com.example.ui.theme.SubjectMathBg
import com.example.ui.theme.SubjectReligionBg
import com.example.ui.theme.SubjectChristian
import com.example.ui.theme.SubjectChristianBg
import com.example.ui.theme.SubjectScienceBg
import com.example.ui.theme.SubjectSocialBg

enum class Subject(
    val id: String,
    val titleArabic: String,
    val titleEnglish: String,
    val iconEmoji: String,
    val rainbowColor: Color,
    val bgLightColor: Color,
    val rainbowColorName: String,
    val subtitle: String
) {
    ALL(
        id = "all",
        titleArabic = "كل المواد",
        titleEnglish = "All Subjects",
        iconEmoji = "🌈",
        rainbowColor = RainbowIndigo,
        bgLightColor = Color(0xFFF1F5F9),
        rainbowColorName = "طيف النخبة",
        subtitle = "جميع فروع منهج الصف الأول الإعدادي"
    ),
    SCIENCE(
        id = "science",
        titleArabic = "العلوم",
        titleEnglish = "Science",
        iconEmoji = "🔬",
        rainbowColor = RainbowCyan,
        bgLightColor = SubjectScienceBg,
        rainbowColorName = "أزرق سماوي",
        subtitle = "المادة وخواصها، الذرة، الطاقة، التكيف"
    ),
    MATH(
        id = "math",
        titleArabic = "الرياضيات",
        titleEnglish = "Mathematics",
        iconEmoji = "📐",
        rainbowColor = RainbowIndigo,
        bgLightColor = SubjectMathBg,
        rainbowColorName = "نيلي ملكي",
        subtitle = "الجبر، الأعداد النسبية، الهندسة والتطابق"
    ),
    SOCIAL_STUDIES(
        id = "social_studies",
        titleArabic = "الدراسات الاجتماعية",
        titleEnglish = "Social Studies",
        iconEmoji = "🌍",
        rainbowColor = RainbowOrange,
        bgLightColor = SubjectSocialBg,
        rainbowColorName = "برتقالي ذهبي",
        subtitle = "الجغرافيا وفصول السنة، تاريخ الفراعنة"
    ),
    ARABIC(
        id = "arabic",
        titleArabic = "اللغة العربية",
        titleEnglish = "Arabic Language",
        iconEmoji = "📖",
        rainbowColor = RainbowViolet,
        bgLightColor = SubjectArabicBg,
        rainbowColorName = "بنفسجي راقي",
        subtitle = "النحو، القراءة، النصوص، همزة القطع والوصل"
    ),
    ENGLISH(
        id = "english",
        titleArabic = "اللغة الإنجليزية",
        titleEnglish = "English - New Hello",
        iconEmoji = "🇬🇧",
        rainbowColor = RainbowGreen,
        bgLightColor = SubjectEnglishBg,
        rainbowColorName = "أخضر زمردي",
        subtitle = "Grammar, Vocab, Reading, Dialogues"
    ),
    ISLAMIC_RELIGION(
        id = "islamic_religion",
        titleArabic = "التربية الإسلامية",
        titleEnglish = "Islamic Studies",
        iconEmoji = "🕌",
        rainbowColor = RainbowYellow,
        bgLightColor = SubjectReligionBg,
        rainbowColorName = "أصفر كهرماني",
        subtitle = "سورة لقمان، التأمل، الطهارة، السيرة النبوية"
    ),
    CHRISTIAN_RELIGION(
        id = "christian_religion",
        titleArabic = "التربية المسيحية",
        titleEnglish = "Christian Studies",
        iconEmoji = "⛪",
        rainbowColor = SubjectChristian,
        bgLightColor = SubjectChristianBg,
        rainbowColorName = "أزرق سماوي ملكي",
        subtitle = "الله محبة، الوصايا العشر، سير القديسين، الفضائل والأخلاق"
    )
}
