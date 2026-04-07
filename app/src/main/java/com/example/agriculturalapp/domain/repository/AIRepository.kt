package com.example.agriculturalapp.domain.repository

import com.example.agriculturalapp.domain.entity.AIResponse
import kotlinx.coroutines.flow.Flow

interface AIRepository {

    suspend fun sendAIRequest(prompt: String): AIResponse

    suspend fun getAIResponse(prompt: String): AIResponse

    suspend fun saveResponse(response: AIResponse)

    suspend fun saveAnalysisResult(
        analysisType: String,
        inputData: String,
        aiResponse: String,
        title: String
    )

    fun getSavedResponses(): Flow<List<AIResponse>>

}