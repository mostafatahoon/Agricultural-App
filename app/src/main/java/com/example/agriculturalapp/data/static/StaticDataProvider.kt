package com.example.agriculturalapp.data.static

import com.example.agriculturalapp.domain.entity.AnalysisType

data class AnalysisField(
    val id: String,
    val labelEn: String,
    val labelAr: String,
    val placeHolderEn: String,
    val placeHolderAr: String,
    val type: FieldType,
    val options: List<String> = emptyList()
)

enum class FieldType {
    TEXT, TEXTAREA, DROPDOWN, NUMBER
}

object StaticDataProvider {

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
            "Enter crop name",
            "أدخل نوع المحصول",
            FieldType.TEXT
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
            "e.g., Sandy, Clay, Loamy",
            "مثال: رملية، طينية، طميية",
            FieldType.TEXT
        ),
        AnalysisField(
            "growth",
            "Growth Stage",
            "مرحلة النمو",
            "e.g., Seedling, Vegetative, Flowering, Fruiting",
            "مثال: الإنبات، النمو، الإزهار، الإثمار",
            FieldType.TEXT
        ),
        AnalysisField(
            "weather",
            "Current Weather",
            "الطقس الحالي",
            "e.g., Sunny, Cloudy, Rainy",
            "مثال: مشمس، غائم، ممطر",
            FieldType.TEXT
        )
    )

    private fun getDiseaseFields(): List<AnalysisField> = listOf(
        AnalysisField(
            "crop",
            "Crop Type",
            "نوع المحصول",
            "Enter crop name",
            "أدخل نوع المحصول",
            FieldType.TEXT
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
            "e.g., Spring, Summer, Fall, Winter",
            "مثال: الربيع، الصيف، الخريف، الشتاء",
            FieldType.TEXT
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
            "Enter crop name",
            "أدخل نوع المحصول",
            FieldType.TEXT
        ),
        AnalysisField(
            "growth",
            "Growth Stage",
            "مرحلة النمو",
            "e.g., Seedling, Vegetative, Flowering, Fruiting",
            "مثال: الإنبات، النمو، الإزهار، الإثمار",
            FieldType.TEXT
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
            "e.g., Sunny, Cloudy, Rainy",
            "مثال: مشمس، غائم، ممطر",
            FieldType.TEXT
        ),
        AnalysisField(
            "crop",
            "Crop Type",
            "نوع المحصول",
            "Enter crop name",
            "أدخل نوع المحصول",
            FieldType.TEXT
        ),
        AnalysisField(
            "soil",
            "Soil Type",
            "نوع التربة",
            "e.g., Sandy, Clay, Loamy",
            "مثال: رملية، طينية، طميية",
            FieldType.TEXT
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
            listOf("Egypt", "Saudi Arabia", "UAE", "Morocco")
        ),
        AnalysisField(
            "crop",
            "Crop Type",
            "نوع المحصول",
            "Enter crop name",
            "أدخل نوع المحصول",
            FieldType.TEXT
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
            listOf("High", "Medium", "Low")
        ),
        AnalysisField(
            "competition",
            "Competition Level",
            "مستوى المنافسة",
            "Select competition level",
            "اختر مستوى المنافسة",
            FieldType.DROPDOWN,
            listOf("High", "Medium", "Low")
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
