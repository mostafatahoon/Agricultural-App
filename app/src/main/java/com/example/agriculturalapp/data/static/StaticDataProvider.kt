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
    val optionsAr: List<String> = emptyList()
)

enum class FieldType {
    TEXT, TEXTAREA, DROPDOWN, NUMBER
}

object StaticDataProvider {

    private val SOIL_TYPES_EN = listOf("Sandy", "Clay", "Loamy", "Silty", "Peaty", "Chalky")
    private val SOIL_TYPES_AR = listOf("رملية", "طينية", "طميية", "غرينية", "خثية", "طباشيرية")

    private val GROWTH_STAGES_EN = listOf("Seedling", "Vegetative", "Flowering", "Fruiting", "Harvesting")
    private val GROWTH_STAGES_AR = listOf("بادرة", "خضري", "إزهار", "إثمار", "حصاد")

    private val WEATHER_EN = listOf("Sunny", "Cloudy", "Rainy", "Stormy", "Windy", "Humid")
    private val WEATHER_AR = listOf("مشمس", "غائم", "ممطر", "عاصف", "ريح", "رطب")

    private val SEASONS_EN = listOf("Spring", "Summer", "Fall", "Winter")
    private val SEASONS_AR = listOf("الربيع", "الصيف", "الخريف", "الشتاء")

    private val CROPS_EN = listOf("Wheat", "Corn", "Rice", "Soybeans", "Cotton", "Fruits", "Vegetables")
    private val CROPS_AR = listOf("قمح", "ذرة", "أرز", "فول صويا", "قطن", "فواكه", "خضروات")

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
            "water",
            "Water Amount (mm/day)",
            "كمية المياه (مم/يوم)",
            "Enter water amount",
            "أدخل كمية المياه",
            FieldType.NUMBER
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
            "weather",
            "Current Weather",
            "الطقس الحالي",
            "Select weather",
            "اختر الطقس",
            FieldType.DROPDOWN,
            WEATHER_EN,
            WEATHER_AR
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

    private const val IRRIGATION_PROMPT_TEMPLATE = """
        You are an intelligent agricultural expert specialized in irrigation management.
        
        Task: Provide comprehensive irrigation recommendations based on the farm data provided.
        
        Farm Data:
        %FARM_DATA%
        
        Please provide your analysis in the following structure:
        1. Current Analysis: Assess the current irrigation situation
        2. Recommendations: Specific irrigation strategies
        3. Risks: Potential challenges and risks
        4. Action Steps: Practical steps to implement
        5. Resources Needed: Tools and materials required
        6. Success Indicators: How to measure success
        
        Make recommendations specific and actionable for the farmer.
    """

    private const val DISEASE_PROMPT_TEMPLATE = """
        You are an intelligent agricultural expert specialized in plant disease diagnosis.
        
        Task: Diagnose plant diseases based on symptoms and provide treatment recommendations.
        
        Farm Data:
        %FARM_DATA%
        
        Please provide your analysis in the following structure:
        1. Disease Diagnosis: Identify possible diseases
        2. Recommendations: Treatment strategies
        3. Risks: Health and economic risks if untreated
        4. Action Steps: Practical treatment steps
        5. Resources Needed: Pesticides, equipment, or professional help
        6. Success Indicators: Signs of recovery and improvement
        
        Make recommendations specific and actionable for the farmer.
    """

    private const val FERTILIZER_PROMPT_TEMPLATE = """
        You are an intelligent agricultural expert specialized in fertilizer planning.
        
        Task: Create an optimal fertilization plan based on soil analysis and crop requirements.
        
        Farm Data:
        %FARM_DATA%
        
        Please provide your analysis in the following structure:
        1. Soil Analysis: Interpret the soil analysis results
        2. Recommendations: Specific fertilizer plan with quantities
        3. Risks: Risks of over/under-fertilization
        4. Action Steps: Application schedule and methods
        5. Resources Needed: Fertilizer types, quantities, equipment
        6. Success Indicators: Expected yield and soil improvements
        
        Make recommendations specific and actionable for the farmer.
    """

    private const val CLIMATE_PROMPT_TEMPLATE = """
        You are an intelligent agricultural expert specialized in climate risk assessment.
        
        Task: Assess climate-related risks and provide mitigation strategies.
        
        Farm Data:
        %FARM_DATA%
        
        Please provide your analysis in the following structure:
        1. Risk Assessment: Identify climate-related risks
        2. Recommendations: Risk mitigation strategies
        3. Impacts: Potential impacts on crop yield
        4. Action Steps: Preventive measures to take
        5. Resources Needed: Seeds, equipment, infrastructure
        6. Success Indicators: Reduced losses and better resilience
        
        Make recommendations specific and actionable for the farmer.
    """

    private const val MARKET_PROMPT_TEMPLATE = """
        You are an intelligent agricultural expert specialized in market analysis and planning.
        
        Task: Provide market analysis and recommendations for optimal pricing and sales strategy.
        
        Farm Data:
        %FARM_DATA%
        
        Please provide your analysis in the following structure:
        1. Market Analysis: Current market situation and trends
        2. Recommendations: Pricing and sales strategy
        3. Risks: Market risks and challenges
        4. Action Steps: Steps to increase profitability
        5. Resources Needed: Marketing channels, certifications
        6. Success Indicators: Revenue goals and market share
        
        Make recommendations specific and actionable for the farmer.
    """
}
