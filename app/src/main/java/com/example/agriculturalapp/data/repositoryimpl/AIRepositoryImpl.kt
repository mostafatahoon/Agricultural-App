package com.example.agriculturalapp.data.repositoryimpl

import com.example.agriculturalapp.data.local.ResponseDao
import com.example.agriculturalapp.data.local.ResultEntity
import com.example.agriculturalapp.data.mapper.toDomain
import com.example.agriculturalapp.domain.entity.AIResponse
import com.example.agriculturalapp.domain.repository.AIRepository
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AIRepositoryImpl @Inject constructor(
    private val generativeModel: GenerativeModel,
    private val dao: ResponseDao
) : AIRepository {

    override suspend fun getAIResponse(prompt: String): AIResponse {
        val response = sendAIRequest(prompt)
        saveResponse(response)
        return response
    }

    override suspend fun saveResponse(response: AIResponse) {
        // Simple mapping
        val entity = ResultEntity(
            analysisType = response.prompt,
            inputData = "",
            aiResponse = response.response,
            timestamp = response.timestamp,
            title = "Analysis"
        )
        dao.insertResult(entity)
    }

    override suspend fun saveAnalysisResult(
        analysisType: String,
        inputData: String,
        aiResponse: String,
        title: String
    ) {
        val entity = ResultEntity(
            analysisType = analysisType,
            inputData = inputData,
            aiResponse = aiResponse,
            title = title,
            timestamp = System.currentTimeMillis()
        )
        dao.insertResult(entity)
    }

    override fun getSavedResponses(): Flow<List<AIResponse>> {
        return dao.getAllResults().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun sendAIRequest(prompt: String): AIResponse {
        val response = generativeModel.generateContent(prompt)
        val text = response.text ?: "No response from AI"

        return AIResponse(
            prompt = prompt,
            response = text,
            timestamp = System.currentTimeMillis()
        )
    }
}
