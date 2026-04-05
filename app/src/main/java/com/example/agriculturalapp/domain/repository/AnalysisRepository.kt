package com.example.agriculturalapp.domain.repository

import com.example.agriculturalapp.domain.entity.AnalysisResult

interface AnalysisRepository {
    /**
     * Generate AI response from Gemini API
     */
    suspend fun generateAIResponse(prompt: String): String

    /**
     * Save analysis result to local database
     */
    suspend fun saveResult(result: AnalysisResult): Long

    /**
     * Get all saved analysis results
     */
    suspend fun getSavedResults(): List<AnalysisResult>

    /**
     * Delete a result by ID
     */
    suspend fun deleteResult(id: Long): Int

    /**
     * Get a specific result by ID
     */
    suspend fun getResultById(id: Long): AnalysisResult?
}

