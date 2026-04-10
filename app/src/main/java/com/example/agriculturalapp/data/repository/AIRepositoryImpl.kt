package com.example.agriculturalapp.data.repository

import com.example.agriculturalapp.data.local.ResponseDao
import com.example.agriculturalapp.data.mapper.ResultEntityMapper
import com.example.agriculturalapp.domain.entity.AIResponse
import com.example.agriculturalapp.domain.repository.AIRepository
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AIRepositoryImpl @Inject constructor(
    private val generativeModel: GenerativeModel,
    private val responseDao: ResponseDao,
    private val resultEntityMapper: ResultEntityMapper
) : AIRepository {

    override suspend fun sendAIRequest(prompt: String): AIResponse {
        val response = generativeModel.generateContent(prompt)
        val text = response.text?.trim() ?: "No response generated."

        return AIResponse(
            prompt = prompt,
            response = text,
            timestamp = System.currentTimeMillis()
        )
    }

    override suspend fun getAIResponse(prompt: String): AIResponse {
        val entity = responseDao.getResultByAnalysisType(prompt)
        return if (entity != null) {
            resultEntityMapper.toDomain(entity)
        } else {
            val response = sendAIRequest(prompt)
            saveResponse(response)
            response
        }
    }

    override suspend fun saveResponse(response: AIResponse) {
        responseDao.insertResult(resultEntityMapper.toEntity(response))
    }

    override suspend fun saveAnalysisResult(
        analysisType: String,
        inputData: String,
        aiResponse: String,
        title: String
    ) {
        // We use the existing mapping logic to maintain consistency
        val response = AIResponse(
            prompt = analysisType,
            response = aiResponse,
            timestamp = System.currentTimeMillis()
        )
        saveResponse(response)
    }

    override fun getSavedResponses(): Flow<List<AIResponse>> {
        return responseDao.getAllResults().map { entities ->
            entities.map { resultEntityMapper.toDomain(it) }
        }
    }
}
