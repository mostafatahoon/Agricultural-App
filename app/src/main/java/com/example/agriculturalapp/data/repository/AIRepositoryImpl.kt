package com.example.agriculturalapp.data.repository

import com.example.agriculturalapp.data.local.ResponseDao
import com.example.agriculturalapp.data.local.ResultEntity
import com.example.agriculturalapp.data.mapper.toDomain
import com.example.agriculturalapp.data.mapper.toEntity
import com.example.agriculturalapp.domain.entity.AIResponse
import com.example.agriculturalapp.domain.repository.AIRepository
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AIRepositoryImpl @Inject constructor(
    private val generativeModel: GenerativeModel,
    private val responseDao: ResponseDao
) : AIRepository {

    override suspend fun sendAIRequest(prompt: String): AIResponse {
        val response = generativeModel.generateContent(prompt)
        val text = response.text ?: "No response from AI"

        return AIResponse(
            prompt = prompt,
            response = text,
            timestamp = System.currentTimeMillis()
        )
    }

    override suspend fun getAIResponse(prompt: String): AIResponse {
        return sendAIRequest(prompt).also { saveResponse(it) }
    }

    override suspend fun saveResponse(response: AIResponse) {
        responseDao.insertResult(response.toEntity())
    }

    override suspend fun saveAnalysisResult(
        analysisType: String,
        inputData: String,
        aiResponse: String,
        title: String
    ) {
        responseDao.insertResult(
            ResultEntity(
                analysisType = analysisType,
                inputData = inputData,
                aiResponse = aiResponse,
                title = title,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    override fun getSavedResponses(): Flow<List<AIResponse>> {
        return responseDao.getAllResults().map { list ->
            list.map { it.toDomain() }
        }
    }
}
