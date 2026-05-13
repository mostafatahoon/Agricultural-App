package com.example.agriculturalapp.data.static

import com.example.agriculturalapp.domain.entity.AnalysisType

data class AnalysisField(
    val id: String,
    val labelEn: String,
    val labelAr: String,
    val placeHolderEn: String,
    val placeHolderAr: String,
    val type: FieldType,
    val optionsEn: List<String> = emptyList(),
    val optionsAr: List<String> = emptyList(),
    val dependsOn: String? = null,
    val showIfValue: String? = null
)

enum class FieldType {
    TEXT, TEXTAREA, DROPDOWN, NUMBER, LOCATION, AREA_UNIT
}

object StaticDataProvider {

    private val SOIL_TYPES_EN = listOf("Sandy", "Clay", "Loamy", "Silty", "Peaty", "Chalky")
    private val SOIL_TYPES_AR = listOf("رملية", "طينية", "طميية", "غرينية", "خثية", "طباشيرية")

    private val GROWTH_STAGES_EN = listOf("Seedling", "Vegetative", "Flowering", "Fruiting", "Harvesting")
    private val GROWTH_STAGES_AR = listOf("بادرة", "نمو خضري", "إزهار", "إثمار", "حصاد")

    private val WEATHER_EN = listOf("Sunny", "Cloudy", "Rainy", "Stormy", "Windy", "Humid")
    private val WEATHER_AR = listOf("مشمس", "غائم", "ممطر", "عاصف", "ريح", "رطب")

    private val SEASONS_EN = listOf("Spring", "Summer", "Fall", "Winter")
    private val SEASONS_AR = listOf("الربيع", "الصيف", "الخريف", "الشتاء")

    private val CROPS_EN = listOf("Wheat", "Corn", "Rice", "Soybeans", "Cotton", "Fruits", "Vegetables")
    private val CROPS_AR = listOf("قمح", "ذرة", "أرز", "فول صويا", "قطن", "فواكه", "خضروات")

    private val UNITS_EN = listOf("Feddan", "Carat", "Acre", "Hectare")
    private val UNITS_AR = listOf("فدان", "قيراط", "أكر", "هكتار")

    private val IRRIGATION_TYPES_EN = listOf("Flood", "Drip", "Sprinkler")
    private val IRRIGATION_TYPES_AR = listOf("غمر", "تنقيط", "رش")

    private val WATER_SOURCES_EN = listOf("Canal", "Well", "Nile", "Rain")
    private val WATER_SOURCES_AR = listOf("ترعة", "بئر", "نيل", "مطر")

    fun getFieldsForAnalysis(analysisType: AnalysisType): List<AnalysisField> {
        return when (analysisType) {
            AnalysisType.IRRIGATION -> getIrrigationFields()
            AnalysisType.DISEASE -> getDiseaseFields()
            AnalysisType.FERTILIZER -> getFertilizerFields()
            AnalysisType.CLIMATE -> getClimateFields()
            AnalysisType.MARKET -> getMarketFields()
        }
    }

    private fun getIrrigationFields(): List<AnalysisField> = listOf(
        AnalysisField(
            "location",
            "Location",
            "الموقع",
            "",
            "",
            FieldType.LOCATION
        ),
        AnalysisField(
            "crop",
            "Crop Type",
            "نوع المحصول",
            "Select crop",
            "اختر المحصول",
            FieldType.DROPDOWN,
            CROPS_EN,
            CROPS_AR
        ),
        AnalysisField(
            "soil",
            "Soil Type",
            "نوع التربة",
            "Select soil type",
            "اختر نوع التربة",
            FieldType.DROPDOWN,
            SOIL_TYPES_EN,
            SOIL_TYPES_AR
        ),
        AnalysisField(
            "growth",
            "Growth Stage",
            "مرحلة النمو",
            "Select growth stage",
            "اختر مرحلة النمو",
            FieldType.DROPDOWN,
            GROWTH_STAGES_EN,
            GROWTH_STAGES_AR
        ),
        AnalysisField(
            "area",
            "Land Area",
            "مساحة الأرض",
            "e.g. 5",
            "مثال: 5",
            FieldType.AREA_UNIT,
            UNITS_EN,
            UNITS_AR
        ),
        AnalysisField(
            "irrigation_type",
            "Irrigation Type",
            "نوع الري",
            "Select irrigation type",
            "اختر نوع الري",
            FieldType.DROPDOWN,
            IRRIGATION_TYPES_EN,
            IRRIGATION_TYPES_AR
        ),
        // Conditional fields for Flood Irrigation
        AnalysisField(
            "irrigation_hours",
            "Irrigation Hours",
            "عدد ساعات الري",
            "e.g. 3",
            "مثال: 3",
            FieldType.NUMBER,
            dependsOn = "irrigation_type",
            showIfValue = "غمر" // Arabic value for Flood
        ),
        AnalysisField(
            "water_source",
            "Water Source",
            "مصدر المياه",
            "Select water source",
            "اختر مصدر المياه",
            FieldType.DROPDOWN,
            WATER_SOURCES_EN,
            WATER_SOURCES_AR,
            dependsOn = "irrigation_type",
            showIfValue = "غمر"
        )
    )

    private fun getDiseaseFields(): List<AnalysisField> = listOf(
        AnalysisField(
            "crop",
            "Crop Type",
            "نوع المحصول",
            "Select crop",
            "اختر المحصول",
            FieldType.DROPDOWN,
            CROPS_EN,
            CROPS_AR
        ),
        AnalysisField(
            "symptoms",
            "Symptoms",
            "الأعراض",
            "Describe visible symptoms",
            "صف الأعراض المرئية",
            FieldType.TEXTAREA
        ),
        AnalysisField(
            "season",
            "Current Season",
            "الموسم الحالي",
            "Select season",
            "اختر الموسم",
            FieldType.DROPDOWN,
            SEASONS_EN,
            SEASONS_AR
        ),
        AnalysisField(
            "humidity",
            "Humidity Level (%)",
            "مستوى الرطوبة (%)",
            "Enter humidity percentage",
            "أدخل نسبة الرطوبة",
            FieldType.NUMBER
        )
    )

    private fun getFertilizerFields(): List<AnalysisField> = listOf(
        AnalysisField(
            "soilAnalysis",
            "Soil Analysis Results",
            "نتائج تحليل التربة",
            "Enter N-P-K values or description",
            "أدخل قيم NPK أو وصف",
            FieldType.TEXTAREA
        ),
        AnalysisField(
            "crop",
            "Crop Type",
            "نوع المحصول",
            "Select crop",
            "اختر المحصول",
            FieldType.DROPDOWN,
            CROPS_EN,
            CROPS_AR
        ),
        AnalysisField(
            "growth",
            "Growth Stage",
            "مرحلة النمو",
            "Select growth stage",
            "اختر مرحلة النمو",
            FieldType.DROPDOWN,
            GROWTH_STAGES_EN,
            GROWTH_STAGES_AR
        ),
        AnalysisField(
            "location",
            "Farm Location",
            "موقع المزرعة",
            "Enter region or coordinates",
            "أدخل المنطقة أو الإحداثيات",
            FieldType.TEXT
        )
    )

    private fun getClimateFields(): List<AnalysisField> = listOf(
        AnalysisField(
            "weather",
            "Current Weather",
            "الطقس الحالي",
            "Select weather",
            "اختر الطقس",
            FieldType.DROPDOWN,
            WEATHER_EN,
            WEATHER_AR
        ),
        AnalysisField(
            "crop",
            "Crop Type",
            "نوع المحصول",
            "Select crop",
            "اختر المحصول",
            FieldType.DROPDOWN,
            CROPS_EN,
            CROPS_AR
        ),
        AnalysisField(
            "soil",
            "Soil Type",
            "نوع التربة",
            "Select soil type",
            "اختر نوع التربة",
            FieldType.DROPDOWN,
            SOIL_TYPES_EN,
            SOIL_TYPES_AR
        ),
        AnalysisField(
            "location",
            "Farm Location",
            "موقع المزرعة",
            "Enter region or coordinates",
            "أدخل المنطقة أو الإحداثيات",
            FieldType.TEXT
        )
    )

    private fun getMarketFields(): List<AnalysisField> = listOf(
        AnalysisField(
            "country",
            "Country",
            "الدولة",
            "Select target country",
            "اختر الدولة المستهدفة",
            FieldType.DROPDOWN,
            listOf("Egypt", "Saudi Arabia", "UAE", "Morocco"),
            listOf("مصر", "السعودية", "الإمارات", "المغرب")
        ),
        AnalysisField(
            "crop",
            "Crop Type",
            "نوع المحصول",
            "Select crop",
            "اختر المحصول",
            FieldType.DROPDOWN,
            CROPS_EN,
            CROPS_AR
        ),
        AnalysisField(
            "price",
            "Current Price (USD/unit)",
            "السعر الحالي",
            "Enter selling price",
            "أدخل سعر البيع",
            FieldType.NUMBER
        ),
        AnalysisField(
            "costs",
            "Production Costs (USD/unit)",
            "تكاليف الإنتاج",
            "Enter production costs",
            "أدخل تكاليف الإنتاج",
            FieldType.NUMBER
        ),
        AnalysisField(
            "demand",
            "Market Demand",
            "الطلب السوقي",
            "Select demand level",
            "اختر مستوى الطلب",
            FieldType.DROPDOWN,
            listOf("High", "Medium", "Low"),
            listOf("عالي", "متوسط", "منخفض")
        ),
        AnalysisField(
            "competition",
            "Competition Level",
            "مستوى المنافسة",
            "Select competition level",
            "اختر مستوى المنافسة",
            FieldType.DROPDOWN,
            listOf("High", "Medium", "Low"),
            listOf("عالي", "متوسط", "منخفض")
        )
    )

    fun getPromptTemplate(analysisType: AnalysisType): String {
        return when (analysisType) {
            AnalysisType.IRRIGATION -> IRRIGATION_PROMPT_TEMPLATE
            AnalysisType.DISEASE -> DISEASE_PROMPT_TEMPLATE
            AnalysisType.FERTILIZER -> FERTILIZER_PROMPT_TEMPLATE
            AnalysisType.CLIMATE -> CLIMATE_PROMPT_TEMPLATE
            AnalysisType.MARKET -> MARKET_PROMPT_TEMPLATE
        }
    }

    private val IRRIGATION_PROMPT_TEMPLATE = """
        أنت خبير زراعي ذكي متخصص في إدارة موارد المياه ونظم الري الحديثة والتقليدية.
        
        المهمة: تقديم استشارة فنية متكاملة وتوصيات ري دقيقة بناءً على بيانات المزرعة التفصيلية المقدمة.
        
        بيانات المزرعة (المدخلات):
        %FARM_DATA%
        
        يرجى تحليل المدخلات أعلاه بدقة متناهية، مع التركيز على الربط بين العناصر التالية:
        1. **الموقع الجغرافي**: تحليل الظروف المناخية للمنطقة وتأثيرها على معدلات التبخر والاحتياج المائي.
        2. **المحصول والتربة**: الموازنة بين الاحتياج المائي النوعي للمحصول وقوام التربة (طينية، رملية، إلخ) وقدرتها على صرف أو الاحتفاظ بالمياه.
        3. **مرحلة النمو**: تقديم جداول ري تتناسب مع احتياجات النبات في عمره الحالي (من البادرة حتى الحصاد).
        4. **المساحة الكلية**: حساب الاحتياجات المائية الإجمالية بناءً على المساحة والوحدة المستخدمة (فدان، قيراط، إلخ).
        5. **كفاءة نظام الري**: تقييم النظام الحالي (تنقيط، رش، أو غمر).
        6. **تحليل ري الغمر (إن وجد)**: إذا كان النظام هو الري بالغمر، قم بتحليل العلاقة بين (ساعات الري) و(مصدر المياه) لتقديم نصائح جوهرية لتقليل الهدر المائي ومنع تملح التربة.

        يجب أن يكون الرد باللغة العربية، منظماً بوضوح، وبالتنسيق التالي:
        1. **تحليل الوضع الراهن**: تقييم فني شامل لممارسات الري الحالية ومدى ملاءمتها للظروف المعطاة.
        2. **التوصيات الفنية المحددة**: (كمية المياه، توقيت الري خلال اليوم، عدد المرات أسبوعياً).
        3. **إدارة المخاطر والتحديات**: التنبيه من مخاطر (نقص المياه، الإجهاد المائي، أو زيادة الري التي تؤدي لأمراض الجذور).
        4. **خطة العمل التنفيذية**: خطوات عملية ومبسطة للمزارع لتحسين كفاءة استخدام المياه.
        5. **المعدات والموارد المقترحة**: قائمة بالأدوات أو التقنيات التي تساهم في تطوير نظام الري.
        6. **مؤشرات النجاح**: علامات ظاهرة يمكن للمزارع مراقبتها للتأكد من نجاح التوصيات المقترحة.
        
        اجعل التوصيات مفصلة، قابلة للقياس، وموجهة للعمل (Actionable) لضمان أعلى إنتاجية مع الحفاظ على الموارد المائية.
    """.trimIndent()

    private const val DISEASE_PROMPT_TEMPLATE = """
        أنت خبير زراعي ذكي متخصص في تشخيص أمراض النبات ووقايتها.
        
        المهمة: تشخيص الحالة المرضية بناءً على الأعراض والبيانات المقدمة، وتقديم خطة علاجية.
        
        بيانات المزرعة المدخلة:
        %FARM_DATA%
        
        يرجى تحليل المدخلات مع التركيز على:
        - الربط بين نوع المحصول والأعراض الموصوفة بدقة.
        - تأثير الموسم الحالي ومستويات الرطوبة على انتشار المرض.
        
        يجب أن يكون الرد باللغة العربية وبالتنسيق التالي:
        1. **التشخيص المرضي**: تحديد المرض أو الآفة المحتملة وأسبابها.
        2. **توصيات العلاج**: استراتيجيات المكافحة (عضوية، كيميائية، أو زراعية).
        3. **إدارة المخاطر**: المخاطر الاقتصادية والصحية في حال إهمال العلاج.
        4. **خطوات التنفيذ**: جدول زمني لعمليات الرش أو المعالجة.
        5. **الموارد المطلوبة**: أنواع المبيدات، الأدوات، أو المساعدات الفنية اللازمة.
        6. **مؤشرات النجاح**: علامات توقف المرض وبدء تعافي المحصول.
    """

    private const val FERTILIZER_PROMPT_TEMPLATE = """
        أنت خبير زراعي ذكي متخصص في تغذية النبات وخصوبة التربة.
        
        المهمة: وضع خطة تسميد مثالية بناءً على تحليل التربة واحتياجات المحصول.
        
        بيانات المزرعة المدخلة:
        %FARM_DATA%
        
        يرجى تحليل المدخلات مع التركيز على:
        - تفسير نتائج تحليل التربة (NPK) المذكورة في الوصف.
        - موازنة العناصر الغذائية بناءً على مرحلة النمو (بادرة، إزهار، إلخ).
        - مراعاة الموقع الجغرافي وتأثيره على امتصاص العناصر.
        
        يجب أن يكون الرد باللغة العربية وبالتنسيق التالي:
        1. **تحليل حالة التربة**: تفسير علمي لمدى توفر العناصر الغذائية حالياً.
        2. **التوصيات السمادية**: الكميات المحددة، الأنواع (يوريا، سوبر فوسفات، إلخ)، وطرق الإضافة.
        3. **إدارة المخاطر**: مخاطر التسميد الزائد (ملوحة) أو الناقص (ضعف الإنتاج).
        4. **جدول العمل**: مواعيد الإضافة المثالية خلال الموسم.
        5. **الموارد المطلوبة**: قائمة بالأسمدة والمعدات اللازمة.
        6. **مؤشرات النجاح**: التحسن المتوقع في المحصول وجودة التربة.
    """

    private const val CLIMATE_PROMPT_TEMPLATE = """
        أنت خبير زراعي ذكي متخصص في الأرصاد الجوية الزراعية وإدارة المخاطر المناخية.
        
        المهمة: تقييم المخاطر المناخية الحالية وتقديم استراتيجيات للتكيف وحماية المحصول.
        
        بيانات المزرعة المدخلة:
        %FARM_DATA%
        
        يرجى تحليل المدخلات مع التركيز على:
        - تأثير الطقس الحالي على نوع المحصول ونوع التربة (مثل تأثير الحرارة على التربة الرملية).
        - الموقع الجغرافي وتوقعات المخاطر المرتبطة به.
        
        يجب أن يكون الرد باللغة العربية وبالتنسيق التالي:
        1. **تقييم المخاطر المناخية**: تحديد التهديدات (صقيع، موجات حر، رياح شديدة، إلخ).
        2. **توصيات التكيف**: استراتيجيات لحماية المحصول (تعديل مواعيد الري، التغطية، إلخ).
        3. **التأثيرات المتوقعة**: كيف سيؤثر المناخ على حجم وجودة الإنتاج.
        4. **خطوات استباقية**: إجراءات وقائية يجب اتخاذها فوراً.
        5. **الموارد المطلوبة**: البنية التحتية أو الأدوات اللازمة للحماية.
        6. **مؤشرات النجاح**: تقليل الخسائر وزيادة مرونة المزرعة تجاه المناخ.
    """

    private const val MARKET_PROMPT_TEMPLATE = """
        أنت خبير زراعي متخصص في تحليل الأسواق والتخطيط الاقتصادي الزراعي.
        
        المهمة: تقديم تحليل سوقي وتوصيات لتعظيم الربحية بناءً على بيانات الاقتصادية المتاحة.
        
        بيانات المزرعة المدخلة:
        %FARM_DATA%
        
        يرجى تحليل المدخلات مع التركيز على:
        - مقارنة السعر الحالي بتكاليف الإنتاج في الدولة المستهدفة.
        - تأثير مستوى الطلب والمنافسة على استراتيجية البيع.
        
        يجب أن يكون الرد باللغة العربية وبالتنسيق التالي:
        1. **تحليل السوق الحالي**: نظرة عامة على الاتجاهات والفرص المتاحة للمحصول.
        2. **توصيات التسعير والبيع**: استراتيجية البيع المثالية وتوقيت الدخول للسوق.
        3. **إدارة المخاطر**: تحديات تقلب الأسعار أو المنافسة الشرسة.
        4. **خطوات زيادة الربحية**: إجراءات لتقليل التكاليف أو تحسين القيمة المضافة.
        5. **الموارد المطلوبة**: قنوات التسويق، الشهادات اللازمة، أو وسائل التغليف.
        6. **مؤشرات النجاح**: الأهداف المالية المتوقعة والحصة السوقية المستهدفة.
    """
}
