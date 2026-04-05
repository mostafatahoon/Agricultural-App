package com.example.agriculturalapp.data.local

/**
 * Static data provider for analysis types, input fields, and dropdown options
 */
object StaticDataProvider {

    // Analysis Types
    val analysisTypes = listOf(
        "IRRIGATION",
        "DISEASE",
        "FERTILIZER",
        "CLIMATE",
        "MARKET"
    )

    // Input fields per analysis type
    val inputFieldsByType: Map<String, List<String>> = mapOf(
        "IRRIGATION" to listOf("crop", "waterAmount", "soilType", "growthStage", "weather"),
        "DISEASE" to listOf("crop", "symptoms", "season", "humidity"),
        "FERTILIZER" to listOf("soilNPK", "crop", "growthStage", "location"),
        "CLIMATE" to listOf("weatherForecast", "crop", "soilType", "location"),
        "MARKET" to listOf("country", "crop", "currentPrice", "productionCost", "demand", "competition")
    )

    // Dropdown options
    data class DropdownOptions(
        val countries: List<String> = listOf("Egypt", "Saudi Arabia", "UAE", "Morocco"),
        val demandLevels: List<String> = listOf("High", "Medium", "Low"),
        val competitionLevels: List<String> = listOf("High", "Medium", "Low"),
        val seasons: List<String> = listOf("Spring", "Summer", "Autumn", "Winter"),
        val soilTypes: List<String> = listOf("Clay", "Sandy", "Loamy", "Silty"),
        val growthStages: List<String> = listOf("Germination", "Seedling", "Vegetative", "Flowering", "Fruiting", "Maturity")
    )

    val dropdownOptions = DropdownOptions()

    // Detailed field descriptions for UI hint
    val fieldDescriptions: Map<String, String> = mapOf(
        // Irrigation fields
        "crop" to "Type of crop being analyzed",
        "waterAmount" to "Amount of water to apply (in mm or liters)",
        "soilType" to "Type of soil in the field",
        "growthStage" to "Current growth stage of the crop",
        "weather" to "Current weather conditions",

        // Disease fields
        "symptoms" to "Description of visible disease symptoms",
        "season" to "Current season",
        "humidity" to "Humidity level (%)",

        // Fertilizer fields
        "soilNPK" to "Nitrogen-Phosphorus-Potassium ratio of soil",
        "location" to "Geographic location/region",

        // Climate fields
        "weatherForecast" to "Weather forecast information",

        // Market fields
        "country" to "Country for market analysis",
        "currentPrice" to "Current market price per unit",
        "productionCost" to "Production cost per unit",
        "demand" to "Market demand level",
        "competition" to "Competition level in market"
    )

    /**
     * Get all fields required for a specific analysis type
     */
    fun getFieldsForAnalysisType(analysisType: String): List<String> {
        return inputFieldsByType[analysisType] ?: emptyList()
    }

    /**
     * Get description for a field
     */
    fun getFieldDescription(fieldName: String): String {
        return fieldDescriptions[fieldName] ?: ""
    }

    /**
     * Get dropdown options for a specific field
     */
    fun getDropdownOptions(fieldName: String): List<String>? {
        return when (fieldName) {
            "country" -> dropdownOptions.countries
            "demand" -> dropdownOptions.demandLevels
            "competition" -> dropdownOptions.competitionLevels
            "season" -> dropdownOptions.seasons
            "soilType" -> dropdownOptions.soilTypes
            "growthStage" -> dropdownOptions.growthStages
            else -> null
        }
    }

    /**
     * Build a default prompt for analysis type
     */
    fun buildPromptTemplate(analysisType: String, inputData: Map<String, String>): String {
        return when (analysisType) {
            "IRRIGATION" -> buildIrrigationPrompt(inputData)
            "DISEASE" -> buildDiseasePrompt(inputData)
            "FERTILIZER" -> buildFertilizerPrompt(inputData)
            "CLIMATE" -> buildClimatePrompt(inputData)
            "MARKET" -> buildMarketPrompt(inputData)
            else -> ""
        }
    }

    private fun buildIrrigationPrompt(data: Map<String, String>): String {
        return """
            As an agricultural expert, provide irrigation recommendations for:
            - Crop: ${data["crop"] ?: "unknown"}
            - Water Amount: ${data["waterAmount"] ?: "to be determined"}
            - Soil Type: ${data["soilType"] ?: "unknown"}
            - Growth Stage: ${data["growthStage"] ?: "unknown"}
            - Weather: ${data["weather"] ?: "normal"}
            
            Please provide:
            1. Optimal irrigation schedule
            2. Water quantity recommendations
            3. Best irrigation method
            4. Seasonal adjustments needed
        """.trimIndent()
    }

    private fun buildDiseasePrompt(data: Map<String, String>): String {
        return """
            As a crop disease specialist, analyze the following disease symptoms:
            - Crop: ${data["crop"] ?: "unknown"}
            - Symptoms: ${data["symptoms"] ?: "not provided"}
            - Season: ${data["season"] ?: "current"}
            - Humidity: ${data["humidity"] ?: "unknown"}%
            
            Please provide:
            1. Likely disease identification
            2. Severity assessment
            3. Treatment recommendations
            4. Prevention measures for future
            5. When to consult a professional
        """.trimIndent()
    }

    private fun buildFertilizerPrompt(data: Map<String, String>): String {
        return """
            As a soil and fertilizer expert, recommend fertilization strategy:
            - Soil NPK Ratio: ${data["soilNPK"] ?: "unknown"}
            - Crop: ${data["crop"] ?: "unknown"}
            - Growth Stage: ${data["growthStage"] ?: "unknown"}
            - Location: ${data["location"] ?: "unknown"}
            
            Please provide:
            1. Nutrient deficiency analysis
            2. Recommended fertilizer type and amount
            3. Application timing and method
            4. Cost-effective alternatives
            5. Environmental considerations
        """.trimIndent()
    }

    private fun buildClimatePrompt(data: Map<String, String>): String {
        return """
            As an agricultural climatologist, analyze climate impact:
            - Weather Forecast: ${data["weatherForecast"] ?: "standard forecast"}
            - Crop: ${data["crop"] ?: "unknown"}
            - Soil Type: ${data["soilType"] ?: "unknown"}
            - Location: ${data["location"] ?: "unknown"}
            
            Please provide:
            1. Climate suitability assessment
            2. Potential weather-related risks
            3. Adaptation strategies
            4. Optimal planting/harvesting windows
            5. Climate change considerations
        """.trimIndent()
    }

    private fun buildMarketPrompt(data: Map<String, String>): String {
        return """
            As an agricultural market analyst, provide market insights:
            - Country: ${data["country"] ?: "unknown"}
            - Crop: ${data["crop"] ?: "unknown"}
            - Current Price: ${data["currentPrice"] ?: "market rate"}
            - Production Cost: ${data["productionCost"] ?: "unknown"}
            - Demand: ${data["demand"] ?: "standard"}
            - Competition: ${data["competition"] ?: "moderate"}
            
            Please provide:
            1. Market viability assessment
            2. Price trend analysis
            3. Profit margin estimation
            4. Market entry strategy
            5. Risk factors and opportunities
        """.trimIndent()
    }
}

