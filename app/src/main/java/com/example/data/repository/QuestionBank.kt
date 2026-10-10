package com.example.data.repository

import com.example.data.model.Flashcard
import com.example.data.model.Question
import com.example.data.model.QuestionType
import com.example.data.model.Subject

// ==============================================================================
// 📚 بنك الأسئلة والتقييمات المعتمد للمنهج المطور الجديد (2025 / 2026)
// مقسم حسب الكتب والوحدات الرسمية لوزارة التربية والتعليم للصف الأول الإعدادي
// تم إعداده وتدقيقه بناءً على طلب أولياء الأمور لتسهيل المراجعة الأسبوعية والنهائية
// ==============================================================================
object QuestionBank {

    val allQuestions: List<Question> = listOf(
        // ==============================================================================
        // 🔬 مادة العلوم (كتاب التلميذ "اكتشف وتعلم" + كراسة الأداءات والتقييمات 2025/2026)
        // ==============================================================================
        // --- الوحدة الأولى: المادة وتركيبها والتركيب الذري ---
        Question(
            id = "sci_1",
            subject = Subject.SCIENCE,
            unit = "الوحدة الأولى: المادة وتركيبها والتركيب الذري",
            type = QuestionType.MCQ,
            questionText = "وحدة قياس الكثافة هي ............",
            options = listOf("جم / سم³", "جم . سم", "نيوتن / م", "كجم / م"),
            correctAnswerIndex = 0,
            modelAnswer = "جم / سم³ (جرام لكل سنتيمتر مكعب)، لأن الكثافة = الكتلة ÷ الحجم.",
            jannatTip = "💡 احفظ مثلث الكثافة: الكثافة = الكتلة (جم) ÷ الحجم (سم³)، وتأكد من كتابة وحدة القياس دائماً في الامتحان!",
            examFrequencyBadge = "امتحانات نصف العام للمنهج المطور 2025 / 2026 ⭐"
        ),
        Question(
            id = "sci_2",
            subject = Subject.SCIENCE,
            unit = "الوحدة الأولى: المادة وتركيبها والتركيب الذري",
            type = QuestionType.EXPLAIN_WHY,
            questionText = "علل لما يأتي: تطفو قطعة من الخشب على سطح الماء بينما يغوص مسمار من الحديد فيه؟",
            modelAnswer = "لأن كثافة الخشب أقل من كثافة الماء فيطفو، بينما كثافة الحديد أكبر من كثافة الماء فيغوص.",
            jannatTip = "💡 الإجابة النموذجية تعتمد دائماً على المقارنة بكثافة الماء النقي (1 جم/سم³)، لا تكتفِ بالقول لأن الحديد ثقيل!",
            examFrequencyBadge = "سؤال أساسي في امتحانات المحافظات 2025 / 2026"
        ),
        Question(
            id = "sci_3",
            subject = Subject.SCIENCE,
            unit = "الوحدة الأولى: المادة وتركيبها والتركيب الذري",
            type = QuestionType.EXPLAIN_WHY,
            questionText = "علل لما يأتي: الذرة متعادلة كهربياً في حالتها العادية؟",
            modelAnswer = "لتساوي عدد الإلكترونات السالبة التي تدور حول النواة مع عدد البروتونات الموجبة الموجودة داخل النواة.",
            jannatTip = "💡 ركّز على كلمتي: (السالبة) تدور خارج النواة، و(الموجبة) داخل النواة. نموذج إجابة الوزارة يدقق فيهما.",
            examFrequencyBadge = "متوقع في امتحانات نصف العام 2025 / 2026"
        ),
        Question(
            id = "sci_4",
            subject = Subject.SCIENCE,
            unit = "الوحدة الأولى: المادة وتركيبها والتركيب الذري",
            type = QuestionType.SCIENTIFIC_TERM,
            questionText = "اكتب المصطلح العلمي: أبسط صورة نقية للمادة لا يمكن تحليلها إلى ما هو أبسط منها بالطرق الكيميائية البسيطة.",
            modelAnswer = "العنصر (Element).",
            jannatTip = "💡 فرّق بين (العنصر) و(المركب)؛ المركب يتكون من اتحاد ذرتين أو أكثر لعناصر مختلفة بنسب وزنية ثابتة.",
            examFrequencyBadge = "منهج العلوم المطور 2025 / 2026"
        ),
        Question(
            id = "sci_9",
            subject = Subject.SCIENCE,
            unit = "الوحدة الأولى: المادة وتركيبها والتركيب الذري",
            type = QuestionType.PROBLEM_SOLVE,
            questionText = "مسألة: احسب كثافة قطعة من النحاس كتلتها 88 جم وحجمها 10 سم³، وهل تغوص أم تطفو إذا وضعت في حوض به ماء نقي؟ ولماذا؟",
            modelAnswer = "1. قانون الكثافة: الكثافة = الكتلة ÷ الحجم\nالكثافة = 88 ÷ 10 = 8.8 جم/سم³.\n2. تغوص في الماء النقي لأن كثافتها (8.8 جم/سم³) أكبر من كثافة الماء النقي (1 جم/سم³).",
            jannatTip = "💡 خطوات حل المسألة: القانون أولاً (نصف الدرجة)، التعويض والناتج مع الوحدة، ثم الاستنتاج والتعليل.",
            examFrequencyBadge = "مسألة امتحانات المحافظات المكررة 2025 / 2026 🎯"
        ),
        Question(
            id = "sci_10",
            subject = Subject.SCIENCE,
            unit = "الوحدة الأولى: المادة وتركيبها والتركيب الذري",
            type = QuestionType.SCIENTIFIC_TERM,
            questionText = "اكتب المصطلح العلمي: أصغر جزء من المادة يمكن أن يوجد على حالة انفراد وتتضح فيه خواص المادة.",
            modelAnswer = "الجزيء (Molecule).",
            jannatTip = "💡 احفظ الفارق: الجزيء يوجد منفرداً وتتضح فيه الخواص، بينما الذرة أصغر وحدة تشترك في التفاعلات الكيميائية.",
            examFrequencyBadge = "مفاهيم كتاب التلميذ 2025 / 2026"
        ),

        // --- الوحدة الثانية: الطاقة وتحولاتها وصورها ---
        Question(
            id = "sci_5",
            subject = Subject.SCIENCE,
            unit = "الوحدة الثانية: الطاقة وتحولاتها وصورها",
            type = QuestionType.WHAT_RESULTS,
            questionText = "ما النتائج المترتبة على: زيادة سرعة جسم متحرك إلى الضعف بالنسبة لطاقة حركته؟",
            modelAnswer = "تزداد طاقة الحركة إلى أربعة أمثال قيمتها؛ لأن طاقة الحركة تتناسب طردياً مع مربع السرعة (طح = ½ ك ع²).",
            jannatTip = "💡 انتبه: السرعة مربعة (ع²)! إذا زادت السرعة لـ 2، تصبح طاقة الحركة 2² = 4 أمثال!",
            examFrequencyBadge = "سؤال للمتفوقين في امتحانات المحافظات 2025 / 2026 🏆"
        ),
        Question(
            id = "sci_6",
            subject = Subject.SCIENCE,
            unit = "الوحدة الثانية: الطاقة وتحولاتها وصورها",
            type = QuestionType.PROBLEM_SOLVE,
            questionText = "مسألة: احسب طاقة وضع جسم وزنه 60 نيوتن موضوع على رف ارتفاعه 5 أمتار من سطح الأرض؟",
            modelAnswer = "القانون: طاقة الوضع = الوزن × الارتفاع\nطاقة الوضع = 60 × 5 = 300 جول.",
            jannatTip = "💡 لا تنسَ كتابة القانون أولاً في ورقة الإجابة (عليه نصف الدرجة) ثم وحدة القياس (جول)!",
            examFrequencyBadge = "مسألة امتحانات نصف العام 2025 / 2026"
        ),
        Question(
            id = "sci_11",
            subject = Subject.SCIENCE,
            unit = "الوحدة الثانية: الطاقة وتحولاتها وصورها",
            type = QuestionType.EXPLAIN_WHY,
            questionText = "علل لما يأتي: يوضع الفريزر في أعلى الثلاجة، بينما توضع المدفأة على أرضية الغرفة؟",
            modelAnswer = "1. يوضع الفريزر في الأعلى: لأن الهواء القريب منه يبرد فتزداد كثافته ويهبط لأسفل ويحل محله هواء دافئ، فيتم تبريد كل الثلاجة.\n2. توضع المدفأة في الأسفل: لأن الهواء القريب منها يسخن فتقل كثافته ويرتفع لأعلى ويحل محله هواء بارد، فيتم تدفئة كامل الغرفة بتيارات الحمل.",
            jannatTip = "💡 مفتاح الإجابة يكمن في (تيارات الحمل): الهواء الساخن تقل كثافته ويرتفع لأعلى، والهواء البارد تزداد كثافته ويهبط لأسفل.",
            examFrequencyBadge = "سؤال علل الشهير في امتحانات العلوم 2025 / 2026 🌟"
        ),
        Question(
            id = "sci_12",
            subject = Subject.SCIENCE,
            unit = "الوحدة الثانية: الطاقة وتحولاتها وصورها",
            type = QuestionType.SCIENTIFIC_TERM,
            questionText = "اكتب المفهوم العلمي: مجموع طاقتي الوضع والحركة لأي جسم وتظل قيمتها ثابتة في مجال الجاذبية الأرضية.",
            modelAnswer = "الطاقة الميكانيكية (Mechanical Energy)، وقانون بقاء الطاقة الميكانيكية.",
            jannatTip = "💡 عند أقصى ارتفاع: الطاقة الميكانيكية = طاقة الوضع فقط (طح = صفر). عند لحظة الاصطدام بالأرض: الطاقة الميكانيكية = طاقة الحركة فقط!",
            examFrequencyBadge = "قوانين الطاقة الميكانيكية 2025 / 2026"
        ),

        // --- الوحدة الثالثة: التنوع والتكيف في الكائنات الحية ---
        Question(
            id = "sci_7",
            subject = Subject.SCIENCE,
            unit = "الوحدة الثالثة: التنوع والتكيف في الكائنات الحية",
            type = QuestionType.SCIENTIFIC_TERM,
            questionText = "اكتب المصطلح العلمي: قدرة بعض الكائنات الحية على محاكاة الظروف البيئية السائدة بغرض التخفي من الأعداء أو لاقتناص الفرائس.",
            modelAnswer = "المماتنة (Camouflage / Mimicry) (مثل الحشرة الورقية وحشرة العود والحرباء).",
            jannatTip = "💡 تذكر أمثلة المماتنة: حشرة العود تشبه أغصان النبات، والحرباء تتلون بلون البيئة.",
            examFrequencyBadge = "متكرر في امتحانات المحافظات 2025 / 2026"
        ),
        Question(
            id = "sci_8",
            subject = Subject.SCIENCE,
            unit = "الوحدة الثالثة: التنوع والتكيف في الكائنات الحية",
            type = QuestionType.MCQ,
            questionText = "من أمثلة النباتات المفترسة (آكلة الحشرات) نبات .............",
            options = listOf("الديونيا والدروسيرا", "الفول والقمح", "الصنوبر والسيكاس", "الذرة والنخيل"),
            correctAnswerIndex = 0,
            modelAnswer = "الديونيا والدروسيرا وحامول الماء (نباتات تقتنص الحشرات لامتصاص المواد النيتروجينية لبناء البروتينات).",
            jannatTip = "💡 علل: لماذا تفترس النباتات الحشرات؟ لامتصاص النيتروجين لأن جذورها لا تستطيع امتصاصه من التربة!",
            examFrequencyBadge = "سؤال مهم جداً في اختر والصواب والخطأ 2025 / 2026"
        ),
        Question(
            id = "sci_13",
            subject = Subject.SCIENCE,
            unit = "الوحدة الثالثة: التنوع والتكيف في الكائنات الحية",
            type = QuestionType.EXPLAIN_WHY,
            questionText = "قارن بين التكيف التركيبي والوظيفي والسلوكي مع ذكر مثال واحد لكل منها في البيئة المصرية؟",
            modelAnswer = "1. تكيف تركيبي (تشريحي): تحور في تركيب أحد أجزاء جسم الكائن؛ كخف الجمل المفلطح لعدم الغوص في الرمال، وحافر الحصان للجري على الصخور.\n2. تكيف وظيفي: تحور في أنسجة وأعضاء الجسم لأداء وظيفة محددة؛ كإفراز السم في الثعابين وإفراز العرق في الإنسان.\n3. تكيف سلوكي: تحور في سلوك الكائن في أوقات محددة؛ كهجرة الطيور ونشاط الخفافيش ليلاً.",
            jannatTip = "💡 جدول المقارنة هو الأفضل دائماً في الامتحان لنيل الدرجة الكاملة بتنظيم دقيق!",
            examFrequencyBadge = "مقارنات الامتحان المتوقعة 2025 / 2026 📋"
        ),

        // --- كراسة الأداءات والتقييمات الأسبوعية للعلوم ---
        Question(
            id = "sci_eval_1",
            subject = Subject.SCIENCE,
            unit = "كراسة الأداءات والتقييمات الأسبوعية للعلوم",
            type = QuestionType.PROBLEM_SOLVE,
            questionText = "تقييم أسبوعي: في تجربة لتعيين كثافة سائل، كانت كتلة المخبار فارغاً 75 جم، وكتلته وبه السائل 135 جم، وحجم السائل في المخبار 50 سم³. احسب كثافة السائل؟",
            modelAnswer = "1. كتلة السائل = كتلة المخبار وبه السائل - كتلة المخبار فارغاً = 135 - 75 = 60 جم.\n2. الكثافة = الكتلة ÷ الحجم = 60 ÷ 50 = 1.2 جم/سم³.",
            jannatTip = "💡 تدريب عملي من كراسة تقييمات الوزارة 2025 / 2026: احسب كتلة السائل الصافية أولاً قبل تطبيق قانون الكثافة!",
            examFrequencyBadge = "كراسة تقييمات الأداء الأسبوعي الرسمية 2025 / 2026 📝"
        ),
        Question(
            id = "sci_eval_2",
            subject = Subject.SCIENCE,
            unit = "كراسة الأداءات والتقييمات الأسبوعية للعلوم",
            type = QuestionType.PROBLEM_SOLVE,
            questionText = "تقييم أسبوعي: عنصر الصوديوم رمز ذري Na عدده الذري 11 وعدده الكتلي 23. وضح بالرسم التوزيع الإلكتروني، وحدد عدد النيوترونات، وعدد مستويات الطاقة المشغولة بالإلكترونات، وهل هو نشط كيميائياً أم خامل؟ ولماذا؟",
            modelAnswer = "1. عدد البروتونات = 11، عدد الإلكترونات = 11.\n2. عدد النيوترونات = العدد الكتلي - العدد الذري = 23 - 11 = 12 نيوترون.\n3. التوزيع الإلكتروني: K=2, L=8, M=1.\n4. عدد مستويات الطاقة المشغولة = 3 مستويات (K, L, M).\n5. عنصر نشط كيميائياً؛ لأن مستوى الطاقة الخارجي (M) يحتوي على إلكترون واحد فقط (غير مكتمل بـ 8 إلكترونات).",
            jannatTip = "💡 قاعدة التوزيع: 2n² للمستويات الأربعة الأولى (2, 8, 18, 32). والمستوى الأخير لا يتحمل أكثر من 8 إلكترونات مهما كان رقمه!",
            examFrequencyBadge = "التقييم الأسبوعي الثالث للمنهج المطور 2025 / 2026 🌟"
        ),

        // ==============================================================================
        // 🌍 مادة الدراسات الاجتماعية (كتاب "إفريقيا ومصر معاً على طريق التنمية والحضارة" 2025/2026)
        // ==============================================================================
        // --- الوحدة الأولى: قارة إفريقيا والمظاهر الطبيعية والمناخ ---
        Question(
            id = "soc_1",
            subject = Subject.SOCIAL_STUDIES,
            unit = "الوحدة الأولى: قارة إفريقيا والمظاهر الطبيعية والمناخ",
            type = QuestionType.MCQ,
            questionText = "يحد قارة إفريقيا من جهة الشرق ............. والمحيط الهندي.",
            options = listOf("البحر الأحمر", "البحر المتوسط", "المحيط الأطلنطي", "خليج غينيا"),
            correctAnswerIndex = 0,
            modelAnswer = "البحر الأحمر والمحيط الهندي يحدان قارة إفريقيا من جهة الشرق، بينما يحدها من الشمال البحر المتوسط، ومن الغرب المحيط الأطلنطي.",
            jannatTip = "💡 احفظ موقع قارة إفريقيا الفلكي والجغرافي من خريطة كتاب الوزارة المطور: إفريقيا ومصر معاً على طريق التنمية!",
            examFrequencyBadge = "كتاب إفريقيا ومصر المطور 2025 / 2026 ⭐"
        ),
        Question(
            id = "soc_2",
            subject = Subject.SOCIAL_STUDIES,
            unit = "الوحدة الأولى: قارة إفريقيا والمظاهر الطبيعية والمناخ",
            type = QuestionType.EXPLAIN_WHY,
            questionText = "بم تفسر: تنوع المناخ والنبات الطبيعي في قارة إفريقيا؟",
            modelAnswer = "بسبب اتساع مساحة القارة، وامتدادها الكبير بين دوائر العرض حيث يمر بها خط الاستواء في المنتصف ومدار السرطان شمالاً ومدار الجدي جنوباً، بجانب تنوع التضاريس والارتفاعات.",
            jannatTip = "💡 تذكر: إفريقيا القارة الوحيدة التي يمر بها خط الاستواء والمداران معاً، مما يمنحها تنوعاً بيئياً فريداً!",
            examFrequencyBadge = "امتحانات نصف العام للدراسات 2025 / 2026"
        ),
        Question(
            id = "soc_6",
            subject = Subject.SOCIAL_STUDIES,
            unit = "الوحدة الأولى: قارة إفريقيا والمظاهر الطبيعية والمناخ",
            type = QuestionType.WHAT_RESULTS,
            questionText = "ما النتائج المترتبة على: وجود هضبة البحيرات الاستوائية وهضبة إثيوبيا (الحبشة) بالنسبة لمصر؟",
            modelAnswer = "تعتبران الروافد والمنابع الرئيسية لنهر النيل الذي يمد مصر بالمياه العذبة وتكوين السهول الفيضية الخصبة اللازمة للزراعة والحياة وقيام الحضارة المصرية.",
            jannatTip = "💡 هضبة إثيوبيا تمثل الرافد الموسمي الصيفي (موسم الفيضان)، وهضبة البحيرات تمثل الرافد الدائم طوال العام.",
            examFrequencyBadge = "سؤال جغرافيا أساسي في امتحانات 2025 / 2026 🌊"
        ),

        // --- الوحدة الثانية: سكان إفريقيا والموارد والتنمية المستدامة ---
        Question(
            id = "soc_3",
            subject = Subject.SOCIAL_STUDIES,
            unit = "الوحدة الثانية: سكان إفريقيا والموارد والتنمية المستدامة",
            type = QuestionType.EXPLAIN_WHY,
            questionText = "علل لما يأتي: تُعد قارة إفريقيا من أغنى قارات العالم في الموارد الطبيعية والمعدنية؟",
            modelAnswer = "لاحتوائها على احتياطيات هائلة من الذهب والماس والحديد والمنجنيز، بالإضافة إلى مصادر الطاقة المتجددة (الشمسية والرياح والكهرومائية) ومصادر النفط والغاز.",
            jannatTip = "💡 منهج 2025/2026 يركز على مفهوم (التنمية المستدامة) والتحول الأخضر في إفريقيا واستثمار طاقات الشباب.",
            examFrequencyBadge = "الوحدة الثانية: الموارد والتنمية 2025 / 2026"
        ),
        Question(
            id = "soc_7",
            subject = Subject.SOCIAL_STUDIES,
            unit = "الوحدة الثانية: سكان إفريقيا والموارد والتنمية المستدامة",
            type = QuestionType.EXPLAIN_WHY,
            questionText = "بم تفسر: تركز السكان في قارة إفريقيا في السهول الفيضية والمدن الساحلية ومناطق التعدين؟",
            modelAnswer = "لوفرة المياه العذبة وخصوبة التربة لقيام الزراعة، واعتدال المناخ في المناطق الساحلية، وتوافر فرص العمل والخدمات والأنشطة الصناعية في المدن ومناطق استخراج المعادن.",
            jannatTip = "💡 يقل السكان في إفريقيا في المناطق الصحراوية الحارة الشديدة الجفاف والمناطق الاستوائية ذات الغابات الكثيفة والأمراض.",
            examFrequencyBadge = "توزيع السكان والموارد بإفريقيا 2025 / 2026"
        ),

        // --- الوحدة الثالثة: تاريخ مصر القديم وعلاقتها بإفريقيا ---
        Question(
            id = "soc_4",
            subject = Subject.SOCIAL_STUDIES,
            unit = "الوحدة الثالثة: تاريخ مصر القديم وعلاقتها بإفريقيا",
            type = QuestionType.SCIENTIFIC_TERM,
            questionText = "من هو الملك المصري الذي قاد رحلة استكشافية وتجارية تاريخية مشهورة حول قارة إفريقيا استغرقت 3 سنوات بالاستعانة ببحارة فينيقيين؟",
            modelAnswer = "الملك نخاو الثاني (من ملوك العصر المتأخر - الأسرة السادسة والعشرين).",
            jannatTip = "💡 هذا الحدث يبرز ريادة مصر البحرية والاتصال الحضاري مع قارة إفريقيا منذ فجر التاريخ.",
            examFrequencyBadge = "تاريخ مصر وإفريقيا 2025 / 2026 ⭐"
        ),
        Question(
            id = "soc_5",
            subject = Subject.SOCIAL_STUDIES,
            unit = "الوحدة الثالثة: تاريخ مصر القديم وعلاقتها بإفريقيا",
            type = QuestionType.MCQ,
            questionText = "أرسلت الملكة حتشبسوت بعثتها التجارية الشهيرة المكونة من سفن ضخمة إلى بلاد ............. (الصومال حالياً) لجلب البخور والعاج والأحجار الكريمة.",
            options = listOf("بونت", "النوبة", "فينيقيا", "كريت"),
            correctAnswerIndex = 0,
            modelAnswer = "بلاد بونت (الصومال حالياً)، وهو نموذج ساطع للتبادل التجاري والسلام بين مصر والعمق الإفريقي.",
            jannatTip = "💡 صور معبد حتشبسوت بالدير البحري في الأقصر توثق تفاصيل رحلة بلاد بونت بنقوش بديعة.",
            examFrequencyBadge = "أسئلة امتحانات التاريخ المطور 2025 / 2026"
        ),
        Question(
            id = "soc_8",
            subject = Subject.SOCIAL_STUDIES,
            unit = "الوحدة الثالثة: تاريخ مصر القديم وعلاقتها بإفريقيا",
            type = QuestionType.WHAT_RESULTS,
            questionText = "دلل تاريخياً على: كانت منطقة النوبة تمثل عمقاً استراتيجياً وتواصلاً حضارياً وتجارياً وثيقاً لمصر القديمة؟",
            modelAnswer = "1. أقام ملوك مصر القلاع والحصون لحماية الحدود الجنوبية (مثل قلاع سمنة وقمنة في عهد سنوسرت الثالث).\n2. تدفق التجارة وتبادل المنتجات وتبادل الخبرات المعمارية والحرفية.\n3. اشتراك أهل النوبة في الجيش المصري وعلاقات المصاهرة والصداقة والسلام.",
            jannatTip = "💡 سؤال (دلل تاريخياً) يتطلب ذكر أدلة ووقائع تاريخية واضحة مدعمة بأسماء الملوك والقلاع أو الآثار.",
            examFrequencyBadge = "سؤال للمتفوقين في تاريخ 2025 / 2026 🏛️"
        ),

        // --- كراسة الأداءات والمهام الميدانية للدراسات ---
        Question(
            id = "soc_eval_1",
            subject = Subject.SOCIAL_STUDIES,
            unit = "كراسة الأداءات والمهام الميدانية للدراسات",
            type = QuestionType.SCIENTIFIC_TERM,
            questionText = "مهمة أدائية أسبوعية: اذكر مقترحاً إيجابياً لتعزيز التعاون الاقتصادي والمائي والزراعي بين مصر ودول حوض النيل؟",
            modelAnswer = "1. إنشاء مشروعات مشتركة لتوليد الطاقة الكهربائية النظيفة واستصلاح الأراضي الزراعية.\n2. تفعيل التبادل التجاري بدون جمارك وتدريب الكوادر الإفريقية بالمراكز المصرية المتخصصة.\n3. الحفاظ على الموارد المائية ومكافحة التلوث وتطهير مجرى النيل من الحشائش.",
            jannatTip = "💡 المهام الأدائية الأسبوعية تمنحك 10 درجات مضافة للمجموع، فاحرص على كتابة أفكار إبداعية واقعية!",
            examFrequencyBadge = "المهام الأدائية الرسمية المعتمدة 2025 / 2026 📝"
        ),

        // ==============================================================================
        // 📖 مادة اللغة العربية (كتاب الطالب "الهوية وبناء الشخصية وتطوير الذات" 2025/2026)
        // ==============================================================================
        // --- المحور الأول: الهوية وبناء الشخصية والنحو ---
        Question(
            id = "ara_1",
            subject = Subject.ARABIC,
            unit = "المحور الأول: الهوية وبناء الشخصية والنحو",
            type = QuestionType.MCQ,
            questionText = "في نص (من مكارم الأخلاق)، قال رسول الله ﷺ: «ما نَقَصَتْ صَدَقَةٌ مِنْ مَالٍ، وَمَا زَادَ اللهُ عَبْدًا بِعَفْوٍ إِلاَّ عِزًّا...». مضاد كلمة (عِزًّا):",
            options = listOf("ذُلاً وهواناً", "فقراً وحاجة", "ضعفاً وخوفاً", "حزناً وألماً"),
            correctAnswerIndex = 0,
            modelAnswer = "ذُلاً (العز يعني الرفعة والشرف، ومضاده الذل والهوان والضعة).",
            jannatTip = "💡 يدعو الحديث الشريف إلى 3 خصال حميدة: التصدق بالمال، العفو والتسامح عند المقدرة، والتواضع لله تعالى.",
            examFrequencyBadge = "حديث متكرر في جميع المحافظات 2025 / 2026 ⭐"
        ),
        Question(
            id = "ara_2",
            subject = Subject.ARABIC,
            unit = "المحور الأول: الهوية وبناء الشخصية والنحو",
            type = QuestionType.SCIENTIFIC_TERM,
            questionText = "قواعد النحو: حدد نوع الخبر في الجملة الآتية مع ذكر الرابط: «مصرُ تاريخُها عظيمٌ».",
            modelAnswer = "نوع الخبر: خبر جملة اسمية في محل رفع.\nالمبتدأ الثاني: تاريخُها، خبر المبتدأ الثاني: عظيمٌ.\nالرابط: ضمير الهاء المتصل في (تاريخها) العائد على المبتدأ الأول (مصر).",
            jannatTip = "💡 السر الذهبي للخبر الجملة الاسمية: اسم به ضمير رابط يعود على المبتدأ الأول + اسم متمم لمعناه!",
            examFrequencyBadge = "سؤال أساسي في قطعة النحو 2025 / 2026"
        ),
        Question(
            id = "ara_5",
            subject = Subject.ARABIC,
            unit = "المحور الأول: الهوية وبناء الشخصية والنحو",
            type = QuestionType.SCIENTIFIC_TERM,
            questionText = "قواعد الإملاء والنحو: بين نوع الهمزة مع ذكر السبب في الكلمتين: (أَكْرَمَ) و (ابْن).",
            modelAnswer = "1. (أكرم): همزة قطع لأنها ماضي فعل رباعي (أكرمَ - يُكرِمُ - إكراماً).\n2. (ابن): ألف وصل لأنها من الأسماء التسعة المسموعة عن العرب التي تبدأ بألف وصل دون همزة.",
            jannatTip = "💡 ضع حرف الواو قبل الكلمة: إذا نطقت الهمزة فهي قطع (وأكرم)، وإذا سقطت في النطق فهي وصل (وابن)!",
            examFrequencyBadge = "سؤال همزة القطع وألف الوصل 2025 / 2026 ✍️"
        ),
        Question(
            id = "ara_6",
            subject = Subject.ARABIC,
            unit = "المحور الأول: الهوية وبناء الشخصية والنحو",
            type = QuestionType.EXPLAIN_WHY,
            questionText = "من موضوع القراءة (الحرية) للكاتب مصطفى لطفي المنفلوطي: بماذا شبه الكاتب الحرية؟ وما حال الإنسان المحروم منها؟",
            modelAnswer = "1. شبه الكاتب الحرية بالشمس التي يجب أن تشرق في كل نفس، وبالحياة التي بدونها تكون حياة الإنسان كحياة اللعب المتحركة في أيدي الأطفال.\n2. الإنسان المحروم من الحرية يعيش في ظلمة حالكة وسواد مستمر، ويفقد إنسانيته وكرامته.",
            jannatTip = "💡 موضوع الحرية وقصة الهرة يمثلان درساً بليغاً في أن الكائنات حتى الحيوانات تدرك معنى الحرية وترفض الحبس والتقييد!",
            examFrequencyBadge = "نصوص وقراءة المنهج المطور 2025 / 2026 📖"
        ),

        // --- المحور الثاني: مجتمعي وتطوير الذات والنصوص ---
        Question(
            id = "ara_3",
            subject = Subject.ARABIC,
            unit = "المحور الثاني: مجتمعي وتطوير الذات والنصوص",
            type = QuestionType.SCIENTIFIC_TERM,
            questionText = "صنّف الأفعال الآتية إلى (صحيح ومعتل) مع بيان نوع كل منها: (قَرَأَ - وَعَدَ - زَلْزَلَ - صَامَ).",
            modelAnswer = "1. (قرأ): فعل صحيح مهموز (لأن أحد حروفه الأصلية همزة وخالٍ من حروف العلة).\n2. (وعد): فعل معتل مثال (لأن أول حروفه الأصلية حرف علة).\n3. (زلزل): فعل صحيح مضعف رباعي.\n4. (صام): فعل معتل أجوف (لأن وسطه حرف علة).",
            jannatTip = "💡 احكم على الفعل دائماً بعد رده إلى الماضي المجرد الثلاثي أو الرباعي وتجريده من الضمائر وحروف الزيادة!",
            examFrequencyBadge = "النحو التراكمي المطور 2025 / 2026"
        ),
        Question(
            id = "ara_4",
            subject = Subject.ARABIC,
            unit = "المحور الثاني: مجتمعي وتطوير الذات والنصوص",
            type = QuestionType.MCQ,
            questionText = "في نص (فضل المعلم) لأحمد شوقي: «قُم لِلمُعَلِّمِ وَفِّهِ التَبجيلا ... كادَ المُعَلِّمُ أَن يَكونَ رَسولا». الغرض من أسلوب الأمر في (قم وفّهِ):",
            options = listOf("النصح والإرشاد والتعظيم", "التهديد والوعيد", "التعجب والدهشة", "التحذير والنهي"),
            correctAnswerIndex = 0,
            modelAnswer = "النصح والإرشاد والحث مع تعظيم مكانة المعلم ورسالته السامية في بناء العقول والأجيال.",
            jannatTip = "💡 علاقة (كاد المعلم أن يكون رسولا) بما قبلها: تعليل لطلب الوقوف والتبجيل.",
            examFrequencyBadge = "نصوص شعرية أساسية 2025 / 2026"
        ),
        Question(
            id = "ara_7",
            subject = Subject.ARABIC,
            unit = "المحور الثاني: مجتمعي وتطوير الذات والنصوص",
            type = QuestionType.SCIENTIFIC_TERM,
            questionText = "قواعد النحو: ميز بين الفعل اللازم والفعل المتعدي في الجملتين مع الإعراب:\n1. «تَفَتَّحَتِ الأزهارُ في الحديقةِ»\n2. «يَكْتُبُ الطالبُ المقالَ بعنايةٍ»",
            modelAnswer = "1. الفعل (تفتحت): فعل لازم؛ لأنه رفع فاعلاً (الأزهار) واكتفى به ولم ينصب مفعولاً به.\n2. الفعل (يكتب): فعل متعدٍ؛ لأنه لم يكتفِ بفاعله بل تعدى ونصب مفعولاً به وهو (المقالَ: مفعول به منصوب وعلامة نصبه الفتحة الظاهرة).",
            jannatTip = "💡 للتفرقة السريعة: ضع ضمير الهاء المتصل (هـ) في آخر الفعل؛ إذا قبله فهو متعدٍ (يكتبه ✔)، وإذا لم يقبله فهو لازم (تفتحه ✖)!",
            examFrequencyBadge = "الفعل اللازم والمتعدي 2025 / 2026 🎯"
        ),

        // --- كراسة الأداءات والتقييمات الأسبوعية والتعبير ---
        Question(
            id = "ara_eval_1",
            subject = Subject.ARABIC,
            unit = "كراسة الأداءات والتقييمات الأسبوعية والتعبير",
            type = QuestionType.SCIENTIFIC_TERM,
            questionText = "تقييم أسبوعي: أعرب الضمير المتصل (نا) في الجملتين الآتيتين مبيناً موقعه الإعرابي:\n1. «أَكْرَمَنَا المعلمُ بالمكافأةِ»\n2. «أَكْرَمْنَا الضيفَ في بيتنا»",
            modelAnswer = "1. في (أكرمَنا المعلمُ): (نا) المفعولين، ضمير متصل مبني في محل نصب مفعول به مقدم (الفعل مبني على الفتح والمعلم هو الفاعل).\n2. في (أكرمْنا الضيفَ): (نا) الفاعلين، ضمير متصل مبني في محل رفع فاعل (الفعل مبني على السكون لاتصاله بنا الفاعلين، والضيف مفعول به).",
            jannatTip = "💡 سر إعرابي متكرر في الامتحانات: انتبه لحركة الحرف الأخير قبل (نا)؛ الفتحة تدل على المفعولين، والسكون تدل على الفاعلين!",
            examFrequencyBadge = "سؤال التقييم الأسبوعي السادس 2025 / 2026 📝"
        ),

        // ==============================================================================
        // 🇬🇧 مادة اللغة الإنجليزية (New Hello Prep 1 - Student's Book & Weekly Booklet 2025/2026)
        // ==============================================================================
        // --- Unit 1: Who Am I? (Family, Hobbies, Present Simple) ---
        Question(
            id = "eng_1",
            subject = Subject.ENGLISH,
            unit = "Unit 1: Who Am I? (Family, Hobbies, Present Simple)",
            type = QuestionType.MCQ,
            questionText = "Choose the correct answer:\nSamy is very clever. He ............. speaks English fluently during the lessons.",
            options = listOf("always", "never", "hardly ever", "don't"),
            correctAnswerIndex = 0,
            modelAnswer = "always (Adverbs of frequency come before main verbs and describe a regular habit or high capability: 100%).",
            jannatTip = "💡 Remember: Adverbs of frequency (always, usually, often, sometimes, never) come BEFORE main verbs, but AFTER verb to be (am/is/are)!",
            examFrequencyBadge = "Unit 1 - New Hello 2025 / 2026 ⭐"
        ),
        Question(
            id = "eng_2",
            subject = Subject.ENGLISH,
            unit = "Unit 1: Who Am I? (Family, Hobbies, Present Simple)",
            type = QuestionType.SCIENTIFIC_TERM,
            questionText = "Vocabulary & Definitions:\nWhat do we call work you do to help others without getting any money?",
            modelAnswer = "Voluntary work (العمل التطوعي).",
            jannatTip = "💡 An orphan is a child who has no mother or father. Twins are two children born at the same time to the same mother.",
            examFrequencyBadge = "New Hello Key Vocab 2025 / 2026"
        ),
        Question(
            id = "eng_6",
            subject = Subject.ENGLISH,
            unit = "Unit 1: Who Am I? (Family, Hobbies, Present Simple)",
            type = QuestionType.SCIENTIFIC_TERM,
            questionText = "Read and correct the underlined words:\n1. This is my sisters bag. She bought it yesterday. [sisters]\n2. We visits our grandparents on Fridays. [visits]",
            modelAnswer = "1. sister's (Possessive 's is used for singular owner: sister's bag).\n2. visit (With subject 'We', the present simple verb takes the base form without 's').",
            jannatTip = "💡 Singular possessive: student's book. Regular plural ending in s: students' books!",
            examFrequencyBadge = "Grammar Mistakes Correction 2025 / 2026 ✍️"
        ),

        // --- Unit 2: Daily Life & School Routine ---
        Question(
            id = "eng_3",
            subject = Subject.ENGLISH,
            unit = "Unit 2: Daily Life & School Routine",
            type = QuestionType.MCQ,
            questionText = "At school, we learn about numbers and solve algebraic equations in ............. lessons.",
            options = listOf("Maths", "Drama", "Social Studies", "Science"),
            correctAnswerIndex = 0,
            modelAnswer = "Maths (Mathematics). We learn about how things work in Science, and about countries in Social Studies.",
            jannatTip = "💡 Notice that subjects ending in 's' like Maths, Physics, and Social Studies take a singular verb: Maths is my favourite subject.",
            examFrequencyBadge = "Unit 2 School Subjects 2025 / 2026"
        ),
        Question(
            id = "eng_7",
            subject = Subject.ENGLISH,
            unit = "Unit 2: Daily Life & School Routine",
            type = QuestionType.SCIENTIFIC_TERM,
            questionText = "Dialogue Completion:\nComplete the dialogue:\nAli: What is your favourite school subject, Omar?\nOmar: ....................................................\nAli: Why do you like it?\nOmar: Because I love speaking other languages and travelling!",
            modelAnswer = "Omar: My favourite subject is English. (or: I like English best).",
            jannatTip = "💡 In dialogue questions, read the whole exchange first to understand the context and tone before writing your answer!",
            examFrequencyBadge = "Exam Dialogue Practice 2025 / 2026 💬"
        ),

        // --- Unit 3: Science & Nature (Animals & Habitats) ---
        Question(
            id = "eng_4",
            subject = Subject.ENGLISH,
            unit = "Unit 3: Science & Nature (Animals & Habitats)",
            type = QuestionType.MCQ,
            questionText = "Choose the correct answer:\nMohamed Salah is a famous Egyptian footballer. He ............. score goals easily and has got a friendly smile.",
            options = listOf("can", "mustn't", "can't", "is"),
            correctAnswerIndex = 0,
            modelAnswer = "can (expresses ability in the present). He has got dark curly hair and a beard.",
            jannatTip = "💡 Rule: have got / has got for possession and physical appearance. He / She / It has got; I / You / We / They have got.",
            examFrequencyBadge = "Unit 3 Describing People 2025 / 2026"
        ),

        // --- Unit 4: Modern Technology & Digital Safety ---
        Question(
            id = "eng_5",
            subject = Subject.ENGLISH,
            unit = "Unit 4: Modern Technology & Digital Safety",
            type = QuestionType.EXPLAIN_WHY,
            questionText = "Digital Safety & Ethics:\nWhat should you do if an unknown person sends you a strange or threatening message online?",
            modelAnswer = "1. Don't reply to the message.\n2. Tell your parents or teacher immediately.\n3. Keep your personal passwords and phone number secret.",
            jannatTip = "💡 Important rule from Unit 4: Never share private photos or passwords with strangers on social media or gaming sites!",
            examFrequencyBadge = "Unit 4 Digital Safety Life Skills 2025 / 2026 💻"
        ),
        Question(
            id = "eng_8",
            subject = Subject.ENGLISH,
            unit = "Unit 4: Modern Technology & Digital Safety",
            type = QuestionType.SCIENTIFIC_TERM,
            questionText = "Grammar: Complete the sentence with the correct form of the verb in brackets:\nLook! The students ............. (do) an important experiment in the science lab at the moment.",
            modelAnswer = "are doing (Present Continuous: am/is/are + verb-ing, used with key words like: Look!, Listen!, at the moment, now).",
            jannatTip = "💡 Plural subject 'The students' takes 'are', then add -ing to the base verb: are doing.",
            examFrequencyBadge = "Present Continuous Rules 2025 / 2026 🔬"
        ),

        // --- Unit 5: Holidays & Traveling ---
        Question(
            id = "eng_9",
            subject = Subject.ENGLISH,
            unit = "Unit 5: Holidays & Traveling",
            type = QuestionType.MCQ,
            questionText = "Choose the correct past simple form:\nLast summer holiday, our family ............. to Hurghada on the Red Sea and visited the beautiful Giftun Island.",
            options = listOf("travelled", "travels", "is travelling", "will travel"),
            correctAnswerIndex = 0,
            modelAnswer = "travelled (Past Simple tense with key time expression 'Last summer holiday').",
            jannatTip = "💡 Red Sea cities like Hurghada and Safaga are famous tourist destinations in Egypt for swimming, diving and coral reefs!",
            examFrequencyBadge = "Unit 5 Past Simple & Travel 2025 / 2026 🏖️"
        ),

        // --- Performance Tasks & Weekly Assessments Prep 1 ---
        Question(
            id = "eng_eval_1",
            subject = Subject.ENGLISH,
            unit = "Performance Tasks & Weekly Assessments Prep 1",
            type = QuestionType.SCIENTIFIC_TERM,
            questionText = "Weekly Assessment Task:\nWrite four sentences describing your daily school routine and what you usually do after school.",
            modelAnswer = "1. I get up at six o'clock every morning.\n2. I walk to school with my best friends.\n3. We study interesting subjects like Science and Computer.\n4. In the afternoon, I do my homework and play football with my brothers.",
            jannatTip = "💡 Start each sentence with a capital letter, use Present Simple with adverbs of frequency, and end with a full stop (.)!",
            examFrequencyBadge = "Weekly Performance Booklet 2025 / 2026 📝"
        ),

        // ==============================================================================
        // 📐 مادة الرياضيات (كتاب الجبر والإحصاء والهندسة + كراسة الواجبات والتقييمات 2025/2026)
        // ==============================================================================
        // --- الوحدة الأولى: الأعداد النسبية والعمليات عليها ---
        Question(
            id = "math_1",
            subject = Subject.MATH,
            unit = "الوحدة الأولى: الأعداد النسبية والعمليات عليها",
            type = QuestionType.MCQ,
            questionText = "العدد (س - 3) / (س + 5) ينتمي لمجموعة الأعداد النسبية (ن) إذا كانت س ≠ .............",
            options = listOf("-5", "3", "5", "صفر"),
            correctAnswerIndex = 0,
            modelAnswer = "-5؛ لأن شرط العدد النسبي أن يكون المقام لا يساوي الصفر (س + 5 ≠ 0 ⇐ س ≠ -5).",
            jannatTip = "💡 فرّق بين سؤالين: (ينتمي لـ ن ⇐ المقام ≠ 0)، و (يساوي صفراً ⇐ البسط هو الذي = 0 أي س = 3)!",
            examFrequencyBadge = "سؤال أساسي في الجبر 2025 / 2026 ⭐"
        ),
        Question(
            id = "math_2",
            subject = Subject.MATH,
            unit = "الوحدة الأولى: الأعداد النسبية والعمليات عليها",
            type = QuestionType.PROBLEM_SOLVE,
            questionText = "باستخدام خاصية التوزيع وبدون استخدام الآلة الحاسبة، أوجد قيمة المقدار:\n(5/7 × 8) + (5/7 × 5) + 5/7",
            modelAnswer = "الحل:\n= 5/7 × (8 + 5 + 1)\n= 5/7 × 14\n= 5 × 2 = 10.",
            jannatTip = "💡 خدعة الامتحان: الحد الأخير (5/7) يعني (5/7 × 1)، لا تنسَ كتابة رقم 1 داخل القوس وإلا ضاعت الدرجة!",
            examFrequencyBadge = "مسألة نصف العام المضمونة في الجبر 2025 / 2026"
        ),
        Question(
            id = "math_7",
            subject = Subject.MATH,
            unit = "الوحدة الأولى: الأعداد النسبية والعمليات عليها",
            type = QuestionType.PROBLEM_SOLVE,
            questionText = "أوجد ثلاثة أعداد نسبية تقع بين العددين: 1/3 و 1/2.",
            modelAnswer = "1. توحيد المقامات على 6: 1/3 = 2/6، و 1/2 = 3/6.\n2. نضرب حدي الكسرين في 10 (تكبير المقامات على 60):\n2/6 = 20/60، و 3/6 = 30/60.\n3. الأعداد النسبية المحصورة هي: 21/60، 22/60، 23/60 (أو أي ثلاثة أعداد بين 20 و 30 على 60).",
            jannatTip = "💡 خاصية كثافة الأعداد النسبية: بين أي عددين نسبيين مختلفين يوجد عدد لا نهائي من الأعداد النسبية!",
            examFrequencyBadge = "مسائل كثافة الأعداد النسبية 2025 / 2026 🔢"
        ),

        // --- الوحدة الثانية: الجبر والمقادير والحدود الجبرية ---
        Question(
            id = "math_3",
            subject = Subject.MATH,
            unit = "الوحدة الثانية: الجبر والمقادير والحدود الجبرية",
            type = QuestionType.PROBLEM_SOLVE,
            questionText = "ما زيادة المقدار (5س + 7ص - 3) عن المقدار (2س - 3ص + 5)؟",
            modelAnswer = "قانون الزيادة = الأول - الثاني\n= (5س + 7ص - 3) - (2س - 3ص + 5)\n= 5س + 7ص - 3 - 2س + 3ص - 5\n= (5س - 2س) + (7ص + 3ص) + (-3 - 5)\n= 3س + 10ص - 8.",
            jannatTip = "💡 احفظ كلمات الطرح: (ما زيادة = الأول - الثاني)، بينما (ما نقص أو اطرح = الثاني - الأول وتغيير إشارات المقدار المطروح)!",
            examFrequencyBadge = "جبر الصف الأول الإعدادي 2025 / 2026"
        ),
        Question(
            id = "math_4",
            subject = Subject.MATH,
            unit = "الوحدة الثانية: الجبر والمقادير والحدود الجبرية",
            type = QuestionType.PROBLEM_SOLVE,
            questionText = "حلل بإخراج العامل المشترك الأكبر (ع.م.أ):\n12 س² ص³ - 18 س³ ص² + 6 س ص",
            modelAnswer = "1. ع.م.أ للأعداد (12، 18، 6) هو 6.\n2. ع.م.أ للرموز بأصغر أس هو س ص.\n3. العامل المشترك الأكبر = 6 س ص.\nالمقدار = 6 س ص (2 س ص² - 3 س² ص + 1).",
            jannatTip = "💡 انتبه: 6 س ص ÷ 6 س ص = 1 (الواحد محايد ضربي ويحفظ خانة الحد الأخير داخل القوس).",
            examFrequencyBadge = "مسألة التحليل الأساسية 2025 / 2026 🎯"
        ),
        Question(
            id = "math_8",
            subject = Subject.MATH,
            unit = "الوحدة الثانية: الجبر والمقادير والحدود الجبرية",
            type = QuestionType.PROBLEM_SOLVE,
            questionText = "اختصر لأبسط صورة المقدار: (2س - 3) (2س + 3) + 9، ثم أوجد القيمة العددية للناتج عندما س = -2.",
            modelAnswer = "1. ناتج ضرب القوسين المترافقين = مربع الأول - مربع الثاني = 4س² - 9.\n2. المقدار = 4س² - 9 + 9 = 4س².\n3. القيمة العددية عندما س = -2:\n4 × (-2)² = 4 × 4 = 16.",
            jannatTip = "💡 حاصل ضرب مجموع حدين في الفرق بينهما لا يحتوي على حد أوسط! مربع الأول - مربع الثاني مباشرة.",
            examFrequencyBadge = "مسألة متوقعة في الامتحان 2025 / 2026 🏆"
        ),

        // --- الوحدة الثالثة: الهندسة والقياس وتطابق المثلثات والتوازي ---
        Question(
            id = "math_5",
            subject = Subject.MATH,
            unit = "الوحدة الثالثة: الهندسة والقياس وتطابق المثلثات والتوازي",
            type = QuestionType.MCQ,
            questionText = "الزاويتان المتتامتان مجموع قياسهما ............. درجة، بينما الزاويتان المتكاملتان مجموع قياسهما ............. درجة.",
            options = listOf("90 و 180", "180 و 90", "90 و 360", "180 و 360"),
            correctAnswerIndex = 0,
            modelAnswer = "90° للمتتامتان، و 180° للمتكاملتان (مجموع الزوايا المتجمعة حول نقطة واحدة = 360°).",
            jannatTip = "💡 احفظ القاعدة: متممة الزاوية الحادة حادة، ومكملتها منفرجة. والزاويتان المتقابلتان بالرأس متساويتان في القياس!",
            examFrequencyBadge = "أساسيات الهندسة 2025 / 2026"
        ),
        Question(
            id = "math_6",
            subject = Subject.MATH,
            unit = "الوحدة الثالثة: الهندسة والقياس وتطابق المثلثات والتوازي",
            type = QuestionType.EXPLAIN_WHY,
            questionText = "اذكر حالتين من حالات تطابق مثلثين في الهندسة؟",
            modelAnswer = "1. يتطابق المثلثان إذا تطابق ضلعان والزاوية المحصورة بينهما في أحدهما مع نظائرها في المثلث الآخر (ضلعان وزاوية محصورة).\n2. يتطابق المثلثان إذا تطابقت زاويتان والضلع الواصل بين رأسيهما في أحدهما مع نظائرها في المثلث الآخر (زاويتان وضلع واصل).\n(وحالة الأضلاع الثلاثة، وحالة الوتر وضلع في المثلث القائم).",
            jannatTip = "💡 نصيحة جنات: اكتب نص الحالة كاملة بدقة ولا تختصرها بالحروف فقط في ورقة الإجابة لنيل الدرجة كاملة!",
            examFrequencyBadge = "سؤال نظري مضمون في الهندسة 2025 / 2026"
        ),
        Question(
            id = "math_9",
            subject = Subject.MATH,
            unit = "الوحدة الثالثة: الهندسة والقياس وتطابق المثلثات والتوازي",
            type = QuestionType.PROBLEM_SOLVE,
            questionText = "في الشكل المقابل: المستقيم أ ب // المستقيم جـ د، والمستقيم هـ و قاطع لهما في النقطتين س، ص. إذا كان ق(∠ أ س ص) = 65°، أوجد بالبرهان قياس الزوايا الآتية:\n1. ق(∠ س ص د) بالتبادل.\n2. ق(∠ س ص جـ) بالتداخل.",
            modelAnswer = "البرهان:\nبما أن المستقيم أ ب // المستقيم جـ د، والمستقيم هـ و قاطع لهما:\n1. إذن: ق(∠ س ص د) = ق(∠ أ س ص) = 65° (بالتبادل - حرف Z).\n2. إذن: ق(∠ س ص جـ) = 180° - 65° = 115° (لأنهما زاويتان داخلتان وفي جهة واحدة من القاطع متكاملتان - حرف U).",
            jannatTip = "💡 مفاتيح التوازي الثلاثة: التبادل (Z متساويتان)، التناظر (F متساويتان)، والتداخل (U مجموعهما 180°).",
            examFrequencyBadge = "مسألة برهان التوازي المعتمدة 2025 / 2026 📐"
        ),

        // --- الوحدة الرابعة: الإحصاء ومقاييس النزعة المركزية ---
        Question(
            id = "math_10",
            subject = Subject.MATH,
            unit = "الوحدة الرابعة: الإحصاء ومقاييس النزعة المركزية",
            type = QuestionType.PROBLEM_SOLVE,
            questionText = "أوجد كلاً من: الوسط الحسابي، والوسيط، والمنوال لمجموعة الدرجات الآتية:\n7 ، 5 ، 9 ، 5 ، 4",
            modelAnswer = "1. الوسط الحسابي = مجموع القيم ÷ عددها = (7 + 5 + 9 + 5 + 4) ÷ 5 = 30 ÷ 5 = 6.\n2. لإيجاد الوسيط، نرتب القيم تصاعدياً أولاً: 4 ، 5 ، 5 ، 7 ، 9. القيمة التي تقع في المنتصف هي 5، إذن الوسيط = 5.\n3. المنوال = القيمة الأكثر شيوعاً أو تكراراً = 5.",
            jannatTip = "💡 لا تنسَ الترتيب أولاً قبل حساب الوسيط! إذا كان عدد القيم زوجياً نأخذ متوسط القيمتين اللتين في المنتصف.",
            examFrequencyBadge = "مسألة إحصاء أساسية 2025 / 2026 📊"
        ),

        // --- كراسة الأداءات والتقييمات الأسبوعية للرياضيات ---
        Question(
            id = "math_eval_1",
            subject = Subject.MATH,
            unit = "كراسة الأداءات والتقييمات الأسبوعية للرياضيات",
            type = QuestionType.PROBLEM_SOLVE,
            questionText = "تقييم أسبوعي: أوجد العدد النسبي الذي يقع عند منتصف المسافة بين العددين 2/5 و 4/5؟",
            modelAnswer = "القانون: العدد عند منتصف المسافة = ½ × (مجموع العددين)\n= ½ × (2/5 + 4/5)\n= ½ × 6/5 = 6/10 = 3/5.",
            jannatTip = "💡 من أسئلة كراسة الواجب والتقييم الأسبوعي الرسمي 2025 / 2026، سؤال سهل جداً ومضمون درجته!",
            examFrequencyBadge = "تقييم الأسبوع الثاني للرياضيات 2025 / 2026 📝"
        ),

        // ==============================================================================
        // 🕌 مادة التربية الدينية الإسلامية (كتاب الطالب وكراسة الأنشطة 2025/2026)
        // ==============================================================================
        // --- الوحدة الأولى: عقيدتي (التفكر في الكون والإيمان بالرسل) ---
        Question(
            id = "rel_1",
            subject = Subject.ISLAMIC_RELIGION,
            unit = "الوحدة الأولى: عقيدتي (التفكر في الكون والإيمان بالرسل)",
            type = QuestionType.EXPLAIN_WHY,
            questionText = "علل لما يأتي: حث القرآن الكريم الإنسان على التأمل والتفكر في خلق السموات والأرض والبحار؟",
            modelAnswer = "لأن التأمل يهدي إلى معرفة عظمة الخالق سبحانه وقدرته اللانهائية، فيقوى الإيمان في القلوب ويزداد المسلم شكراً لله وخشية منه وطاعة لأوامره.",
            jannatTip = "💡 قال تعالى: ﴿الَّذِينَ يَذْكُرُونَ اللَّهَ قِيَامًا وَقُعُودًا وَعَلَىٰ جُنُوبِهِمْ وَيَتَفَكَّرُونَ فِي خَلْقِ السَّمَاوَاتِ وَالأَرْضِ رَبَّنَا مَا خَلَقْتَ هَٰذَا بَاطِلاً سُبْحَانَكَ﴾.",
            examFrequencyBadge = "منهج الدين المطور 2025 / 2026 ⭐"
        ),
        Question(
            id = "rel_5",
            subject = Subject.ISLAMIC_RELIGION,
            unit = "الوحدة الأولى: عقيدتي (التفكر في الكون والإيمان بالرسل)",
            type = QuestionType.SCIENTIFIC_TERM,
            questionText = "من وصايا لقمان الحكيم لابنه في سورة لقمان المقررة: اذكر أربع وصايا اشتملت عليها الآيات الكريمة؟",
            modelAnswer = "1. النهي عن الشرك بالله؛ لأن الشرك ظلم عظيم.\n2. بر الوالدين والإحسان إليهما وشكرهما.\n3. إقامة الصلاة والأمر بالمعروف والنهي عن المنكر والصبر على الشدائد.\n4. عدم التكبر على الناس وخفض الصوت والتواضع في المشي.",
            jannatTip = "💡 سورة لقمان محطة رئيسية في امتحانات التربية الدينية، احفظ الآيات الكريمة ومعاني المفردات بدقة!",
            examFrequencyBadge = "سورة لقمان المقررة 2025 / 2026 📖"
        ),

        // --- الوحدة الثانية: عباداتي وأخلاقي (الصلاة والصوم وبر الوالدين) ---
        Question(
            id = "rel_2",
            subject = Subject.ISLAMIC_RELIGION,
            unit = "الوحدة الثانية: عباداتي وأخلاقي (الصلاة والصوم وبر الوالدين)",
            type = QuestionType.MCQ,
            questionText = "قال رسول الله ﷺ: «طَلَبُ العِلْمِ فَرِيضَةٌ عَلَى كُلِّ .............».",
            options = listOf("مسلمٍ ومسلمة", "كبيرٍ فقط", "غنيٍّ فقط", "عالمٍ"),
            correctAnswerIndex = 0,
            modelAnswer = "مسلم (ويشمل الذكر والأنثى؛ فالإسلام يوجب طلب العلم النافع الديني والدنيوي على الجميع دون تفرقة).",
            jannatTip = "💡 العلم في الإسلام يشمل الطب والهندسة والعلوم والتكنولوجيا وكل ما يعمر الأرض وينفع البشرية.",
            examFrequencyBadge = "سؤال الحديث النبوي الشريف 2025 / 2026"
        ),
        Question(
            id = "rel_3",
            subject = Subject.ISLAMIC_RELIGION,
            unit = "الوحدة الثانية: عباداتي وأخلاقي (الصلاة والصوم وبر الوالدين)",
            type = QuestionType.TRUE_FALSE,
            questionText = "صح أم خطأ مع التصويب: يجوز الوضوء بالماء النجس أو المخلوط بالصابون لتأدية الصلاة؟",
            modelAnswer = "خطأ (✖). التصويب: لا يصح الوضوء إلا بالماء الطهور الطاهر في نفسه والمطهر لغيره الذي لم يتغير لونه أو طعمه أو ريحه بنجاسة.",
            jannatTip = "💡 ماء الصنبور وماء المطر والآبار ماء طهور يرفع الحدث، أما الماء المتغير بالنجاسات فلا يصح التطهر به.",
            examFrequencyBadge = "فقه الطهارة والعبادات 2025 / 2026"
        ),

        // --- الوحدة الثالثة: سير وشخصيات (الهجرة النبوية المباركة) ---
        Question(
            id = "rel_4",
            subject = Subject.ISLAMIC_RELIGION,
            unit = "الوحدة الثالثة: سير وشخصيات (الهجرة النبوية المباركة)",
            type = QuestionType.MCQ,
            questionText = "من هو أول فدائي في الإسلام الذي نام في فراش النبي ﷺ ليلة الهجرة النبوية الشريفة؟",
            options = listOf("علي بن أبي طالب رضي الله عنه", "أبو بكر الصديق", "عمر بن الخطاب", "عثمان بن عفان"),
            correctAnswerIndex = 0,
            modelAnswer = "علي بن أبي طالب رضي الله عنه (نام في فراش النبي ليرد الأمانات لأهل مكة وليوهم المشركين بأن النبي ما زال نائماً في بيته).",
            jannatTip = "💡 اذكر الفضيلتين: الشجاعة الفائقة لافتداء النبي بروحه، وأداء الأمانات لأصحابها ولو كانوا مشركين!",
            examFrequencyBadge = "سيرة وشخصيات إسلامية 2025 / 2026"
        ),
        Question(
            id = "rel_6",
            subject = Subject.ISLAMIC_RELIGION,
            unit = "الوحدة الثالثة: سير وشخصيات (الهجرة النبوية المباركة)",
            type = QuestionType.EXPLAIN_WHY,
            questionText = "ما أهم الدروس المستفادة من التخطيط الدقيق للهجرة النبوية الشريفة من مكة إلى المدينة المنورة؟",
            modelAnswer = "1. الأخذ بالأسباب الدنيوية الكاملة مع التوكل التام واليقين بنصر الله ورعايته.\n2. اختيار الصاحب الصالح والأمين (أبو بكر الصديق رضي الله عنه).\n3. الاستعانة بذوي الكفاءة والأمانة (مثل عبد الله بن أريقط دليلاً للطريق).\n4. دور الشباب والنساء الإيجابي الفاعل (علي بن أبي طالب، وعبد الله بن أبي بكر، وأسماء ذات النطاقين).",
            jannatTip = "💡 التخطيط والأخذ بالأسباب عبادة شرعية لا تنافي التوكل على الله بل تكمله!",
            examFrequencyBadge = "دروس الهجرة النبوية 2025 / 2026 🌟"
        ),

        // --- كراسة الأنشطة والتقييمات الدينية الأسبوعية ---
        Question(
            id = "rel_eval_1",
            subject = Subject.ISLAMIC_RELIGION,
            unit = "كراسة الأنشطة والتقييمات الدينية الأسبوعية",
            type = QuestionType.SCIENTIFIC_TERM,
            questionText = "تقييم أسبوعي: علل: تأكيد الإسلام على بر الوالدين حتى لو كانا غير مسلمين؟",
            modelAnswer = "اعترافاً بفضلهما العظيم في التربية والرعاية، ولأن رابطة البر والإحسان الإنساني واجبة، بشرط ألا يطيعهما في معصية الله أو الشرك به (وصاحبهما في الدنيا معروفاً).",
            jannatTip = "💡 من أسئلة كراسة التقييمات الأسبوعية الرسمية لوزارة التربية والتعليم 2025 / 2026.",
            examFrequencyBadge = "كراسة تقييمات الأداء الأسبوعي الرسمية 2025 / 2026 📝"
        ),

        // ==============================================================================
        // ⛪ مادة التربية الدينية المسيحية (كتاب التلميذ وكراسة الأنشطة 2025/2026)
        // ==============================================================================
        // --- الوحدة الأولى: عقيدتي وإيماني بالله (الخالق وضابط الكل والكتاب المقدس) ---
        Question(
            id = "chr_1",
            subject = Subject.CHRISTIAN_RELIGION,
            unit = "الوحدة الأولى: عقيدتي وإيماني بالله (الخالق وضابط الكل والكتاب المقدس)",
            type = QuestionType.MCQ,
            questionText = "أول سفر في أسفار العهد القديم في الكتاب المقدس هو سفر .............",
            options = listOf("التكوين", "الخروج", "المزامير", "إشعياء"),
            correctAnswerIndex = 0,
            modelAnswer = "التكوين (Genesis)، ويبدأ بآية الخلق الأولى: «فِي الْبَدْءِ خَلَقَ اللهُ السَّمَاوَاتِ وَالأَرْضَ».",
            jannatTip = "💡 يتكون الكتاب المقدس من 66 سفراً قانونياً: 39 سفراً في العهد القديم، و 27 سفراً في العهد الجديد.",
            examFrequencyBadge = "منهج الدين المسيحي المطور 2025 / 2026 ⭐"
        ),
        Question(
            id = "chr_2",
            subject = Subject.CHRISTIAN_RELIGION,
            unit = "الوحدة الأولى: عقيدتي وإيماني بالله (الخالق وضابط الكل والكتاب المقدس)",
            type = QuestionType.EXPLAIN_WHY,
            questionText = "علل لما يأتي: يُوصف الله في قانون الإيمان المسيحي بـ «ضابط الكل ومحب البشر»؟",
            modelAnswer = "لأنه الخالق القدير الذي يدبر كل أمور الكون بحكمته وعنايته الأبوية الدائمة، ومحبته للبشر محبة أزلية غير مشروطة تفيض بالخير والرحمة على الجميع.",
            jannatTip = "💡 «ضابط الكل» تعني أن كل تفاصيل حياتنا تحت رعايته الإلهية الحانية ولا ينسى أحداً من خلقه.",
            examFrequencyBadge = "سؤال أساسي في العقيدة المسيحية 2025 / 2026"
        ),
        Question(
            id = "chr_3",
            subject = Subject.CHRISTIAN_RELIGION,
            unit = "الوحدة الأولى: عقيدتي وإيماني بالله (الخالق وضابط الكل والكتاب المقدس)",
            type = QuestionType.SCIENTIFIC_TERM,
            questionText = "اكتب المصطلح الإيماني: إرشاد الروح القدس لكتّاب أسفار الكتاب المقدس عبر العصور لتدوين كلام الله دون خطأ.",
            modelAnswer = "الوحي الإلهي (Divine Inspiration)؛ كما جاء في رسالة بولس الرسول: «كُلُّ الْكِتَابِ هُوَ مُوحىً بِهِ مِنَ اللهِ، وَنَافِعٌ لِلتَّعْلِيمِ وَالتَّوْبِيخِ، لِلتَّقْوِيمِ وَالتَّأْدِيبِ» (2 تي 3: 16).",
            jannatTip = "💡 الوحي الإلهي لا يلغي شخصية الكاتب وأسلوبه، بل عصمهم الروح القدس من الخطأ أثناء التدوين.",
            examFrequencyBadge = "مفاهيم كتاب التلميذ 2025 / 2026 📖"
        ),

        // --- الوحدة الثانية: عبادتي وسلوكي المسيحي (الصلاة والوصايا والفضائل) ---
        Question(
            id = "chr_4",
            subject = Subject.CHRISTIAN_RELIGION,
            unit = "الوحدة الثانية: عبادتي وسلوكي المسيحي (الصلاة والوصايا والفضائل)",
            type = QuestionType.MCQ,
            questionText = "علّم السيد المسيح تلاميذه الصلاة النموذجية الجامعة في الموعظة على الجبل والمعروفة بـ .............",
            options = listOf("الصلاة الربانية (أبانا الذي في السماوات)", "صلاة الشكر", "صلاة النوم", "صلاة الستار"),
            correctAnswerIndex = 0,
            modelAnswer = "الصلاة الربانية («أَبَانَا الَّذِي فِي السَّمَاوَاتِ، لِيَتَقَدَّسِ اسْمُكَ، لِيَأْتِ مَلَكُوتُكَ، لِتَكُنْ مَشِيئَتُكَ كَمَا فِي السَّمَاءِ كَذلِكَ عَلَى الأَرْضِ...»).",
            jannatTip = "💡 افتتاح الصلاة بـ «أبانا» يعلمنا دالة البنوة لله وأخوة كل البشر، وطلب الغفران مشروط بأن نغفر نحن أيضاً للمسيئين إلينا!",
            examFrequencyBadge = "أساسيات الصلاة المسيحية 2025 / 2026 ⭐"
        ),
        Question(
            id = "chr_5",
            subject = Subject.CHRISTIAN_RELIGION,
            unit = "الوحدة الثانية: عبادتي وسلوكي المسيحي (الصلاة والوصايا والفضائل)",
            type = QuestionType.EXPLAIN_WHY,
            questionText = "وضح أهمية فضيلتي التسامح والمحبة العملية في حياة المؤمن والمجتمع؟",
            modelAnswer = "لأن المحبة هي قمة الفضائل المسيحية («اللهَ مَحَبَّةٌ»)، والتسامح ينقي القلب من الكراهية والخصام، ويصنع السلام والتعاون والتآخي بين جميع أبناء الوطن الواحد.",
            jannatTip = "💡 المحبة الحقيقية تترجم إلى أفعال وسلوك: إغاثة المحتاجين، مساعدة الزملاء، والكلمة الطيبة.",
            examFrequencyBadge = "الفضائل والأخلاق المسيحية 2025 / 2026"
        ),

        // --- الوحدة الثالثة: سير وقديسون وشخصيات وطنية ملهمة ---
        Question(
            id = "chr_6",
            subject = Subject.CHRISTIAN_RELIGION,
            unit = "الوحدة الثالثة: سير وقديسون وشخصيات وطنية ملهمة",
            type = QuestionType.MCQ,
            questionText = "من هو كاروز الديار المصرية ومؤسس الكنيسة القبطية الأرثوذكسية في الإسكندرية وكاتب الإنجيل الثاني؟",
            options = listOf("القديس مرقس الرسول", "القديس بولس الرسول", "القديس بطرس الرسول", "القديس متى الإنجيلي"),
            correctAnswerIndex = 0,
            modelAnswer = "القديس مرقس الرسول (ناظر الإله الإنجيلي ومؤسس كنيسة الإسكندرية ومدرستها اللاهوتية العريقة في القرن الأول الميلادي).",
            jannatTip = "💡 رمز القديس مرقس هو الأسد؛ لأن إنجيله يبدأ بصوت صارخ في البرية، وشجاعته في نشر الإيمان بمصر.",
            examFrequencyBadge = "تاريخ الكنيسة والشخصيات 2025 / 2026 🏛️"
        ),
        Question(
            id = "chr_7",
            subject = Subject.CHRISTIAN_RELIGION,
            unit = "الوحدة الثالثة: سير وقديسون وشخصيات وطنية ملهمة",
            type = QuestionType.WHAT_RESULTS,
            questionText = "دلل على الدور الوطني الأصيل للأقباط والكنيسة المصرية في الدفاع عن وحدة مصر واستقلالها؟",
            modelAnswer = "1. ارتباط الأقباط العميق بتراب مصر والدفاع عن سيادتها في كل العصور ومقاومة المعتدين.\n2. التلاحم الوطني التاريخي وتجسيد قيم الوحدة الوطنية والمواطنة المتساوية.\n3. المساهمة الفعالة لرجال الدين والعلماء والمفكرين الأقباط في بناء مصر الحديثة والمشروعات القومية.",
            jannatTip = "💡 المواطنة والوحدة الوطنية هما صمام أمان مصر القوي، والتاريخ يشهد بتماسك الشعب المصري كنسيج واحد لا ينفصم.",
            examFrequencyBadge = "المواطنة والوحدة الوطنية 2025 / 2026 🇪🇬"
        ),

        // --- كراسة الأنشطة والتقييمات الأسبوعية للتربية المسيحية ---
        Question(
            id = "chr_eval_1",
            subject = Subject.CHRISTIAN_RELIGION,
            unit = "كراسة الأنشطة والتقييمات الأسبوعية للتربية المسيحية",
            type = QuestionType.SCIENTIFIC_TERM,
            questionText = "تقييم أسبوعي: كيف يطبق طالب الصف الأول الإعدادي وصية «أَكْرِمْ أَبَاكَ وَأُمَّكَ» في سلوكه اليومي؟",
            modelAnswer = "1. طاعة الوالدين والاستماع لنصائحهما وتقدير تضحياتهما.\n2. مساعدة الأسرة في الواجبات المنزلية والحرص على عدم إرهاقهما.\n3. التفوق في الدراسة والأخلاق الحسنة لإدخال الفرح لقلوبهما، والدعاء لهما بالصحة والبركة.",
            jannatTip = "💡 هي أول وصية بوعد إلهي: «لِكَيْ تَطُولَ أَيَّامُكَ عَلَى الأَرْضِ الَّتِي يُعْطِيكَ الرَّبُّ إِلهُكَ».",
            examFrequencyBadge = "التقييم الأسبوعي الرسمي للدين المسيحي 2025 / 2026 📝"
        )
    )

    // ==========================================
    // 🌈 بطاقات المراجعة السريعة (Rainbow Flashcards 2025/2026)
    // ==========================================
    val flashcards: List<Flashcard> = listOf(
        Flashcard(
            id = "fc_1",
            subject = Subject.SCIENCE,
            category = "قانون العلوم الأساسي 2025/2026",
            frontTitle = "ما هو قانون الكثافة ووحدة قياسها؟",
            backAnswer = "الكثافة = الكتلة ÷ الحجم\nوحدة القياس: جم/سم³\nكثافة الماء النقي = 1 جم/سم³",
            jannatAdvice = "إذا كان الجسم كثافته أقل من 1 يطفو، وأكثر من 1 يغوص في الماء النقي!"
        ),
        Flashcard(
            id = "fc_2",
            subject = Subject.SCIENCE,
            category = "التركيب الذري",
            frontTitle = "ما مكونات النواة وشحناتها؟",
            backAnswer = "1. بروتونات موجبة (+)\n2. نيوترونات متعادلة (±)\nوتدور حول النواة إلكترونات سالبة (-) في مستويات الطاقة (K, L, M, N, O, P, Q).",
            jannatAdvice = "الكتلة تتركز داخل النواة، وشحنة النواة موجبة!"
        ),
        Flashcard(
            id = "fc_3",
            subject = Subject.SOCIAL_STUDIES,
            category = "كتاب إفريقيا ومصر 2025/2026",
            frontTitle = "ما أهمية الموقع الجغرافي لقارة إفريقيا؟",
            backAnswer = "قارة متوسطة بين قارات العالم القديم، يحدها البحر المتوسط شمالاً والأحمر شرقاً والأطلنطي غرباً، ويمر بها خط الاستواء والمداران معاً.",
            jannatAdvice = "تنوع الموقع جعل إفريقيا مهداً لأعظم الحضارات ومركزاً لطرق التجارة والتنمية العالمية!"
        ),
        Flashcard(
            id = "fc_4",
            subject = Subject.ARABIC,
            category = "قواعد النحو المطور",
            frontTitle = "ما أنواع الخبر في الجملة الاسمية؟",
            backAnswer = "1. خبر مفرد (ليس جملة ولا شبه جملة)\n2. خبر جملة (اسمية بها رابط ضمير، أو فعلية)\n3. خبر شبه جملة (جار ومجرور، أو ظرف زمان/مكان).",
            jannatAdvice = "الضمير الرابط في الخبر الجملة الاسمية شرط أساسي لاكتمال المعنى!"
        ),
        Flashcard(
            id = "fc_5",
            subject = Subject.MATH,
            category = "قواعد الجبر 2025/2026",
            frontTitle = "متى يكون العدد النسبي مساوياً للصفر؟",
            backAnswer = "إذا كان البسط هو الذي يساوي الصفر (أ = 0 في الكسر أ/ب حيث ب ≠ 0).",
            jannatAdvice = "فرّق: ينتمي لـ ن (المقام ≠ 0)، يساوي صفراً (البسط = 0)!"
        ),
        Flashcard(
            id = "fc_6",
            subject = Subject.ENGLISH,
            category = "English Grammar 2025/2026",
            frontTitle = "What are the Adverbs of Frequency?",
            backAnswer = "Always (100%), Usually (80%), Often (70%), Sometimes (50%), Never (0%).\nRule: Before main verbs, but After verb to be (am/is/are).",
            jannatAdvice = "He is always happy / He always plays tennis. Notice the position!"
        ),
        Flashcard(
            id = "fc_7",
            subject = Subject.ISLAMIC_RELIGION,
            category = "التربية الإسلامية 2025/2026",
            frontTitle = "ما تعريف الماء الطهور وما حكمه؟",
            backAnswer = "الماء الطهور: هو الماء الطاهر في نفسه والمطهر لغيره الذي بقي على أصل خلقته دون تغير (ماء المطر، العيون، الأنهار، البحار)، وحكمه يرفع الحدث ويزيل الخبث وتصح به الصلاة.",
            jannatAdvice = "الماء النجس لا تصح به الطهارة أبداً ويجب اجتنابه."
        ),
        Flashcard(
            id = "fc_8",
            subject = Subject.CHRISTIAN_RELIGION,
            category = "التربية المسيحية 2025/2026",
            frontTitle = "ما هي الوصية العظمى في المسيحية؟",
            backAnswer = "«تُحِبُّ الرَّبَّ إِلهَكَ مِنْ كُلِّ قَلْبِكَ وَمِنْ كُلِّ نَفْسِكَ وَمِنْ كُلِّ فِكْرِكَ... وَتُحِبُّ قَرِيبَكَ كَنَفْسِكَ».",
            jannatAdvice = "المحبة الصادقة لله والناس تلخص كل الوصايا والفضائل!"
        )
    )

    fun getQuestionsForSubject(subject: Subject): List<Question> {
        return if (subject == Subject.ALL) allQuestions else allQuestions.filter { it.subject == subject }
    }

    fun getMockExamQuestions(subject: Subject, count: Int = 10): List<Question> {
        val pool = getQuestionsForSubject(subject)
        return pool.shuffled().take(count.coerceAtMost(pool.size))
    }
}
