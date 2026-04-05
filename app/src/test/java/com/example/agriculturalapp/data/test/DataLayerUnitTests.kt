package com.example.agriculturalapp.data.test

import com.example.agriculturalapp.data.local.StaticDataProvider
import com.example.agriculturalapp.data.utils.DataUtils
import com.example.agriculturalapp.domain.entity.AnalysisResult
import com.example.agriculturalapp.domain.entity.AnalysisType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for Data Layer components
 * These tests demonstrate how to test the data layer
 */
class DataLayerUnitTests {

    // ========== StaticDataProvider Tests ==========

    @Test
    fun testAnalysisTypesAvailable() {
        val types = StaticDataProvider.analysisTypes
        assertEquals(5, types.size)
        assertTrue(types.contains("IRRIGATION"))
        assertTrue(types.contains("DISEASE"))
        assertTrue(types.contains("FERTILIZER"))
        assertTrue(types.contains("CLIMATE"))
        assertTrue(types.contains("MARKET"))
    }

    @Test
    fun testGetFieldsForIrrigation() {
        val fields = StaticDataProvider.getFieldsForAnalysisType("IRRIGATION")
        assertEquals(5, fields.size)
        assertTrue(fields.contains("crop"))
        assertTrue(fields.contains("waterAmount"))
        assertTrue(fields.contains("soilType"))
        assertTrue(fields.contains("growthStage"))
        assertTrue(fields.contains("weather"))
    }

    @Test
    fun testGetFieldsForMarket() {
        val fields = StaticDataProvider.getFieldsForAnalysisType("MARKET")
        assertEquals(6, fields.size)
        assertTrue(fields.contains("country"))
        assertTrue(fields.contains("crop"))
        assertTrue(fields.contains("currentPrice"))
        assertTrue(fields.contains("productionCost"))
        assertTrue(fields.contains("demand"))
        assertTrue(fields.contains("competition"))
    }

    @Test
    fun testGetDropdownOptions() {
        val countries = StaticDataProvider.getDropdownOptions("country")
        assertNotNull(countries)
        assertEquals(4, countries?.size)
        assertTrue(countries?.contains("Egypt") == true)
        assertTrue(countries?.contains("Saudi Arabia") == true)
        assertTrue(countries?.contains("UAE") == true)
        assertTrue(countries?.contains("Morocco") == true)
    }

    @Test
    fun testGetDemandOptions() {
        val demands = StaticDataProvider.getDropdownOptions("demand")
        assertNotNull(demands)
        assertEquals(3, demands?.size)
        assertTrue(demands?.contains("High") == true)
        assertTrue(demands?.contains("Medium") == true)
        assertTrue(demands?.contains("Low") == true)
    }

    @Test
    fun testGetCompetitionOptions() {
        val competition = StaticDataProvider.getDropdownOptions("competition")
        assertNotNull(competition)
        assertEquals(3, competition?.size)
    }

    @Test
    fun testGetGrowthStageOptions() {
        val stages = StaticDataProvider.getDropdownOptions("growthStage")
        assertNotNull(stages)
        assertEquals(6, stages?.size)
        assertTrue(stages?.contains("Germination") == true)
        assertTrue(stages?.contains("Maturity") == true)
    }

    @Test
    fun testGetFieldDescription() {
        val description = StaticDataProvider.getFieldDescription("crop")
        assertNotNull(description)
        assertTrue(description.isNotEmpty())
        assertTrue(description.contains("crop") || description.contains("Crop"))
    }

    @Test
    fun testBuildPromptTemplate() {
        val inputs = mapOf(
            "crop" to "Wheat",
            "waterAmount" to "100mm",
            "soilType" to "Clay",
            "growthStage" to "Flowering",
            "weather" to "Sunny"
        )
        val prompt = StaticDataProvider.buildPromptTemplate("IRRIGATION", inputs)
        
        assertTrue(prompt.contains("Wheat"))
        assertTrue(prompt.contains("100mm"))
        assertTrue(prompt.contains("Clay"))
        assertTrue(prompt.contains("irrigation") || prompt.contains("Irrigation"))
    }

    @Test
    fun testBuildMarketPrompt() {
        val inputs = mapOf(
            "country" to "Egypt",
            "crop" to "Cotton",
            "currentPrice" to "50",
            "productionCost" to "30",
            "demand" to "High",
            "competition" to "Medium"
        )
        val prompt = StaticDataProvider.buildPromptTemplate("MARKET", inputs)
        
        assertTrue(prompt.contains("Egypt"))
        assertTrue(prompt.contains("Cotton"))
        assertTrue(prompt.contains("market") || prompt.contains("Market"))
    }

    // ========== DataUtils Tests ==========

    @Test
    fun testMapToJson() {
        val inputMap = mapOf(
            "crop" to "Rice",
            "waterAmount" to "50L"
        )
        val json = DataUtils.mapToJson(inputMap)
        
        assertNotNull(json)
        assertTrue(json.contains("Rice"))
        assertTrue(json.contains("50L"))
    }

    @Test
    fun testJsonToMap() {
        val inputMap = mapOf("crop" to "Corn", "water" to "100")
        val json = DataUtils.mapToJson(inputMap)
        val resultMap = DataUtils.jsonToMap(json)
        
        assertNotNull(resultMap)
        assertEquals(inputMap, resultMap)
    }

    @Test
    fun testValidateInputFieldsSuccess() {
        val inputs = mapOf(
            "crop" to "Wheat",
            "waterAmount" to "100",
            "soilType" to "Clay",
            "growthStage" to "Flowering",
            "weather" to "Sunny"
        )
        val (isValid, missing) = DataUtils.validateInputFields("IRRIGATION", inputs)
        
        assertTrue(isValid)
        assertTrue(missing.isEmpty())
    }

    @Test
    fun testValidateInputFieldsFailure() {
        val inputs = mapOf(
            "crop" to "Wheat"
            // Missing other required fields
        )
        val (isValid, missing) = DataUtils.validateInputFields("IRRIGATION", inputs)
        
        assertFalse(isValid)
        assertTrue(missing.isNotEmpty())
        assertTrue(missing.contains("waterAmount") || missing.size > 3)
    }

    @Test
    fun testGetFieldDisplayName() {
        val displayName = DataUtils.getFieldDisplayName("soilType")
        assertTrue(displayName.contains("Soil") && displayName.contains("Type"))
    }

    @Test
    fun testFormatTimestamp() {
        val currentTime = System.currentTimeMillis()
        val formatted = DataUtils.formatTimestamp(currentTime)
        
        assertNotNull(formatted)
        assertTrue(formatted.isNotEmpty())
        // Should contain date and time
        assertTrue(formatted.length > 10)
    }

    @Test
    fun testTruncateText() {
        val longText = "This is a very long text that should be truncated"
        val truncated = DataUtils.truncateText(longText, 20)
        
        assertEquals(20 + 3, truncated.length) // 20 chars + "..."
        assertTrue(truncated.endsWith("..."))
    }

    @Test
    fun testExtractKeyInsights() {
        val response = "First sentence. Second sentence. Third sentence."
        val insights = DataUtils.extractKeyInsights(response)
        
        assertTrue(insights.contains("First sentence"))
        assertTrue(insights.contains("Second sentence"))
        assertFalse(insights.contains("Third sentence"))
    }

    // ========== Domain Model Tests ==========

    @Test
    fun testAnalysisResultCreation() {
        val result = AnalysisResult(
            id = 1,
            analysisType = AnalysisType.IRRIGATION,
            inputData = "{\"crop\": \"Wheat\"}",
            aiResponse = "Test response"
        )
        
        assertEquals(1, result.id)
        assertEquals(AnalysisType.IRRIGATION, result.analysisType)
        assertEquals("Test response", result.aiResponse)
        assertTrue(result.timestamp > 0)
    }

    @Test
    fun testAnalysisTypeEnum() {
        val types = listOf(
            AnalysisType.IRRIGATION,
            AnalysisType.DISEASE,
            AnalysisType.FERTILIZER,
            AnalysisType.CLIMATE,
            AnalysisType.MARKET
        )
        
        assertEquals(5, types.size)
    }

    @Test
    fun testAnalysisTypeConversion() {
        val type = AnalysisType.valueOf("IRRIGATION")
        assertEquals(AnalysisType.IRRIGATION, type)
    }

    // ========== Integration-style Tests ==========

    @Test
    fun testCompleteAnalysisFlow() {
        // 1. Prepare input
        val analysisType = "IRRIGATION"
        val inputs = mapOf(
            "crop" to "Rice",
            "waterAmount" to "100mm",
            "soilType" to "Clay",
            "growthStage" to "Vegetative",
            "weather" to "Rainy"
        )

        // 2. Validate
        val (isValid, _) = DataUtils.validateInputFields(analysisType, inputs)
        assertTrue(isValid)

        // 3. Build prompt
        val prompt = DataUtils.buildAnalysisPrompt(analysisType, inputs)
        assertNotNull(prompt)
        assertTrue(prompt.contains("Rice"))

        // 4. Simulate AI response
        val mockResponse = "Increase water by 20% due to recent rainfall"

        // 5. Create result
        val result = AnalysisResult(
            analysisType = AnalysisType.IRRIGATION,
            inputData = DataUtils.mapToJson(inputs),
            aiResponse = mockResponse
        )

        // 6. Verify
        assertEquals(AnalysisType.IRRIGATION, result.analysisType)
        assertEquals(mockResponse, result.aiResponse)
        assertNotNull(result.inputData)
    }

    @Test
    fun testMarketAnalysisFlow() {
        val inputs = mapOf(
            "country" to "Egypt",
            "crop" to "Wheat",
            "currentPrice" to "60",
            "productionCost" to "35",
            "demand" to "High",
            "competition" to "Medium"
        )

        val (isValid, missing) = DataUtils.validateInputFields("MARKET", inputs)
        assertTrue(isValid)
        assertTrue(missing.isEmpty())

        val prompt = StaticDataProvider.buildPromptTemplate("MARKET", inputs)
        assertTrue(prompt.contains("Egypt"))
        assertTrue(prompt.contains("Wheat"))
        assertTrue(prompt.contains("market") || prompt.contains("Market"))
    }
}

