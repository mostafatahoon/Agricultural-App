package com.example.agriculturalapp.data.repository

import com.example.agriculturalapp.data.dtomodel.Content
import com.example.agriculturalapp.data.dtomodel.GeminiRequestDto
import com.example.agriculturalapp.data.dtomodel.Part
import com.example.agriculturalapp.data.local.ResponseDao
import com.example.agriculturalapp.data.local.ResultEntity
import com.example.agriculturalapp.data.mapper.extractText
import com.example.agriculturalapp.data.mapper.toDomain
import com.example.agriculturalapp.data.mapper.toEntity
import com.example.agriculturalapp.data.remote.GeminiApi
import com.example.agriculturalapp.domain.entity.AIResponse
import com.example.agriculturalapp.domain.repository.AIRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Legacy repository kept only so the project compiles if older code paths remain referenced.
 * Clean Architecture note: prefer `data.repositoryimpl.AIRepositoryImpl`.
 */
class AIRepositoryImpl @Inject constructor(
    private val api: GeminiApi,
    private val responseDao: ResponseDao,
    private val apiKey: String
) : AIRepository {

    override suspend fun sendAIRequest(prompt: String): AIResponse {
        val request = GeminiRequestDto(contents = listOf(Content(parts = listOf(Part(text = prompt)))))
        val apiResponse = api.generateContent(apiKey, request)
        return AIResponse(prompt = prompt, response = apiResponse.extractText(), timestamp = System.currentTimeMillis())
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
        return responseDao.getAllResults().map { results -> results.map { it.toDomain() } }
    }
}
