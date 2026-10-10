package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Question
import com.example.data.model.QuestionType
import com.example.data.model.Subject
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.RainbowIndigo
import com.example.ui.theme.RainbowGreen

/**
 * 🖨️ شاشة ومعاينة الطباعة فائقة الدقة (Print & PDF Export Preview)
 * تتيح لأولياء الأمور والطلاب طباعة بنك الأسئلة أو الامتحانات بجودة بيضاء وسوداء عالية التباين 300 DPI
 */
@Composable
fun PrintExamDialog(
    subject: Subject,
    unitTitle: String?,
    questions: List<Question>,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var includeAnswers by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth(0.96f),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF0F172A)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Print,
                            contentDescription = "طباعة",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "طباعة المذكرة ونماذج الامتحانات",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            text = "جودة طباعة بيور HD (بيكسل ناصع 300 DPI)",
                            fontSize = 11.sp,
                            color = Color(0xFF475569)
                        )
                    }
                }
                IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "إغلاق",
                        tint = Color(0xFF64748B)
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
            ) {
                // خيارات الطباعة
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFF1F5F9),
                    border = BorderStroke(1.dp, Color(0xFFCBD5E1)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "تضمين نموذج الإجابة والشرح في الطباعة:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )
                        Switch(
                            checked = includeAnswers,
                            onCheckedChange = { includeAnswers = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = RainbowIndigo
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // معاينة الورقة المطبوعة (A4 Paper Preview)
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.5.dp, Color(0xFF0F172A)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    ) {
                        // ترويسة الورقة الامتحانية الرسمية
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, Color.Black)
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "جمهورية مصر العربية\nوزارة التربية والتعليم",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black,
                                        textAlign = TextAlign.Start
                                    )
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "مذكرة تفوق وامتحانات 2025/2026",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color.Black
                                        )
                                        Text(
                                            text = "الصف الأول الإعدادي - الفصل الدراسي الأول",
                                            fontSize = 9.5.sp,
                                            color = Color.Black
                                        )
                                    }
                                    Text(
                                        text = "المادة: ${subject.titleArabic}\nالزمن: ساعتان",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black,
                                        textAlign = TextAlign.End
                                    )
                                }
                                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = Color.Black)
                                Text(
                                    text = unitTitle ?: "بنك الأسئلة الشامل لجميع وحدات المنهج المطور",
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        // قائمة الأسئلة المنسقة للطباعة
                        itemsIndexed(questions) { index, q ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Text(
                                    text = "س ${index + 1}: ${q.questionText}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    lineHeight = 20.sp
                                )

                                if (q.type == QuestionType.MCQ && q.options.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(3.dp))
                                    q.options.forEachIndexed { optIndex, opt ->
                                        val letter = listOf("أ", "ب", "ج", "د").getOrElse(optIndex) { "${optIndex + 1}" }
                                        Text(
                                            text = "   ($letter) $opt",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Color.Black
                                        )
                                    }
                                }

                                if (includeAnswers) {
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = Color(0xFFF8FAFC),
                                        border = BorderStroke(0.5.dp, Color(0xFF94A3B8)),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(modifier = Modifier.padding(6.dp)) {
                                            Text(
                                                text = "✔ الإجابة النموذجية: ${q.modelAnswer}",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color(0xFF0F172A)
                                            )
                                        }
                                    }
                                }

                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 6.dp),
                                    color = Color(0xFFE2E8F0)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // زر الطباعة المباشرة عبر طابعة الهاتف أو PDF
                Button(
                    onClick = {
                        printDocument(context, subject, unitTitle, questions, includeAnswers)
                    },
                    modifier = Modifier
                        .weight(1.2f)
                        .testTag("print_now_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Print,
                        contentDescription = "طباعة",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "طباعة الآن / PDF",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color.White
                    )
                }

                // زر المشاركة
                OutlinedButton(
                    onClick = {
                        shareAsText(context, subject, unitTitle, questions, includeAnswers)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("share_exam_button"),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Color(0xFFCBD5E1))
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "مشاركة",
                        tint = RainbowIndigo,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "مشاركة",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.5.sp,
                        color = Color(0xFF334155)
                    )
                }
            }
        },
        dismissButton = {}
    )
}

/**
 * دالة إرسال المذكرة للطباعة بجودة عالية 300 DPI عبر Android PrintManager و WebView
 */
private fun printDocument(
    context: Context,
    subject: Subject,
    unitTitle: String?,
    questions: List<Question>,
    includeAnswers: Boolean
) {
    try {
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as? PrintManager
        if (printManager == null) {
            Toast.makeText(context, "خاصية الطباعة غير مدعومة على هذا الجهاز", Toast.LENGTH_SHORT).show()
            return
        }

        val html = buildPrintableHtml(subject, unitTitle, questions, includeAnswers)
        val webView = WebView(context)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                val printAdapter = webView.createPrintDocumentAdapter("مذكرة_${subject.titleArabic}_2026")
                val printAttributes = PrintAttributes.Builder()
                    .setColorMode(PrintAttributes.COLOR_MODE_MONOCHROME)
                    .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                    .setMinMargins(PrintAttributes.Margins(300, 300, 300, 300))
                    .build()
                printManager.print("مذكرة_${subject.titleArabic}_2026", printAdapter, printAttributes)
            }
        }
        webView.loadDataWithBaseURL(null, html, "text/html; charset=utf-8", "UTF-8", null)
    } catch (e: Exception) {
        Toast.makeText(context, "حدث خطأ أثناء بدء الطباعة: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
    }
}

/**
 * إنشاء صفحة HTML فائقة الدقة مجهزة خصيصاً للطباعة الورقية A4 و PDF
 */
private fun buildPrintableHtml(
    subject: Subject,
    unitTitle: String?,
    questions: List<Question>,
    includeAnswers: Boolean
): String {
    val sb = StringBuilder()
    sb.append("""
        <!DOCTYPE html>
        <html dir="rtl" lang="ar">
        <head>
        <meta charset="UTF-8">
        <title>مذكرة ${subject.titleArabic}</title>
        <style>
            @page { size: A4; margin: 15mm; }
            body {
                font-family: 'Cairo', 'Segoe UI', Tahoma, Arial, sans-serif;
                color: #000;
                background: #fff;
                margin: 0;
                padding: 10px;
                font-size: 14pt;
                line-height: 1.6;
            }
            .header-table {
                width: 100%;
                border-collapse: collapse;
                border: 2px solid #000;
                margin-bottom: 20px;
            }
            .header-table td {
                padding: 8px 12px;
                vertical-align: middle;
            }
            .title-center {
                text-align: center;
                font-size: 16pt;
                font-weight: bold;
            }
            .question-item {
                margin-bottom: 16px;
                page-break-inside: avoid;
            }
            .q-title {
                font-weight: bold;
                font-size: 14pt;
                color: #000;
            }
            .options-list {
                margin: 6px 20px 8px 0;
                font-size: 13pt;
            }
            .answer-box {
                margin-top: 5px;
                padding: 6px 10px;
                background-color: #f1f5f9;
                border: 1px solid #64748b;
                border-radius: 4px;
                font-size: 12pt;
                font-weight: bold;
            }
            hr {
                border: 0;
                border-top: 1px dashed #999;
                margin: 12px 0;
            }
            .footer {
                text-align: center;
                font-size: 10pt;
                margin-top: 30px;
                color: #555;
            }
        </style>
        </head>
        <body>
        <table class="header-table">
            <tr>
                <td style="width: 30%;">
                    <strong>جمهورية مصر العربية</strong><br>
                    <strong>وزارة التربية والتعليم</strong>
                </td>
                <td class="title-center" style="width: 40%;">
                    مذكرة ونماذج امتحانات 2025/2026<br>
                    <span style="font-size: 12pt;">الصف الأول الإعدادي</span>
                </td>
                <td style="width: 30%; text-align: left;">
                    <strong>المادة:</strong> ${subject.titleArabic}<br>
                    <strong>الزمن:</strong> ساعتان
                </td>
            </tr>
            <tr>
                <td colspan="3" style="border-top: 1px solid #000; text-align: center; font-weight: bold; background: #f8fafc;">
                    ${unitTitle ?: "نماذج امتحانات وبنك الأسئلة الشامل"} (عدد الأسئلة: ${questions.size})
                </td>
            </tr>
        </table>
    """.trimIndent())

    questions.forEachIndexed { i, q ->
        sb.append("<div class='question-item'>")
        sb.append("<div class='q-title'>س ${i + 1}: ${q.questionText}</div>")
        if (q.type == QuestionType.MCQ && q.options.isNotEmpty()) {
            sb.append("<div class='options-list'>")
            q.options.forEachIndexed { optI, opt ->
                val letter = listOf("أ", "ب", "ج", "د").getOrElse(optI) { "${optI + 1}" }
                sb.append("<div>($letter) $opt</div>")
            }
            sb.append("</div>")
        }
        if (includeAnswers) {
            sb.append("<div class='answer-box'>✔ الإجابة النموذجية: ${q.modelAnswer}</div>")
        }
        sb.append("<hr></div>")
    }

    sb.append("""
        <div class="footer">
            تم الاستخراج والطباعة عبر تطبيق «تعلم مع جنات» - الصف الأول الإعدادي المطور
        </div>
        </body>
        </html>
    """.trimIndent())

    return sb.toString()
}

/**
 * مشاركة نص المذكرة لطباعتها أو حفظها في واتساب أو البريد
 */
private fun shareAsText(
    context: Context,
    subject: Subject,
    unitTitle: String?,
    questions: List<Question>,
    includeAnswers: Boolean
) {
    val sb = StringBuilder()
    sb.append("📚 مذكرة وامتحان مادة: ${subject.titleArabic}\n")
    sb.append("📖 الوحدة: ${unitTitle ?: "جميع الوحدات"}\n")
    sb.append("✨ منهج الصف الأول الإعدادي المطور 2025 / 2026\n")
    sb.append("------------------------------------------\n\n")

    questions.forEachIndexed { index, q ->
        sb.append("س ${index + 1}: ${q.questionText}\n")
        if (q.type == QuestionType.MCQ && q.options.isNotEmpty()) {
            q.options.forEachIndexed { optIndex, opt ->
                val letter = listOf("أ", "ب", "ج", "د").getOrElse(optIndex) { "${optIndex + 1}" }
                sb.append("   ($letter) $opt\n")
            }
        }
        if (includeAnswers) {
            sb.append("✔ الإجابة: ${q.modelAnswer}\n")
        }
        sb.append("\n")
    }

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "مذكرة ${subject.titleArabic}")
        putExtra(Intent.EXTRA_TEXT, sb.toString())
    }
    context.startActivity(Intent.createChooser(intent, "مشاركة المذكرة للطباعة"))
}
