package com.example.agriculturalapp.data.utils

import com.example.agriculturalapp.data.local.StaticDataProvider
import com.google.gson.Gson

/**
 * Utility class for common data operations
 */
object DataUtils {

    private val gson = Gson()

    /**
     * Convert a map to JSON string for storing as input data
     */
    fun mapToJson(map: Map<String, String>): String {
        return gson.toJson(map)
    }

    /**
     * Convert JSON string back to map
     */
    fun jsonToMap(json: String): Map<String, String>? {
        val type = object : com.google.gson.reflect.TypeToken<Map<String, String>>() {}.type
        return try {
            gson.fromJson(json, type)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Build a complete analysis prompt from user input
     */
    fun buildAnalysisPrompt(
        analysisType: String,
        inputData: Map<String, String>
    ): String {
        return StaticDataProvider.buildPromptTemplate(analysisType, inputData)
    }

    /**
     * Validate input fields for an analysis type
     */
    fun validateInputFields(
        analysisType: String,
        inputData: Map<String, String>
    ): Pair<Boolean, List<String>> {
        val requiredFields = StaticDataProvider.getFieldsForAnalysisType(analysisType)
        val missingFields = requiredFields.filter { field ->
            inputData[field].isNullOrBlank()
        }

        return if (missingFields.isEmpty()) {
            Pair(true, emptyList())
        } else {
            Pair(false, missingFields)
        }
    }

    /**
     * Get user-friendly field names for UI display
     */
    fun getFieldDisplayName(fieldName: String): String {
        return fieldName
            .replace(Regex("([A-Z])"), " $1")
            .trim()
            .replaceFirstChar { it.uppercase() }
    }

    /**
     * Format timestamp to readable date string
     */
    fun formatTimestamp(timestamp: Long): String {
        val sdf = java.text.SimpleDateFormat("MMM dd, yyyy HH:mm", java.util.Locale.getDefault())
        return sdf.format(java.util.Date(timestamp))
    }

    /**
     * Truncate long text for display
     */
    fun truncateText(text: String, maxLength: Int = 100): String {
        return if (text.length > maxLength) {
            text.substring(0, maxLength) + "..."
        } else {
            text
        }
    }

    /**
     * Extract key insights from AI response (first 2 sentences)
     */
    fun extractKeyInsights(aiResponse: String): String {
        val sentences = aiResponse.split(Regex("[.!?]"))
        return sentences.take(2).joinToString(". ") + "."
    }
}

