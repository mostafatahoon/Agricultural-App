package com.example.agriculturalapp.data.repository

import com.example.agriculturalapp.data.local.ResponseDao
import com.example.agriculturalapp.data.mapper.GeminiMapper
import com.example.agriculturalapp.data.mapper.ResultEntityMapper
import com.example.agriculturalapp.data.remote.GeminiApi
import com.example.agriculturalapp.data.dtomodel.GeminiRequestDto
import com.example.agriculturalapp.data.dtomodel.ContentRequest
import com.example.agriculturalapp.data.dtomodel.PartRequest
import com.example.agriculturalapp.domain.entity.AIResponse
import com.example.agriculturalapp.domain.repository.AIRepository
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AIRepositoryImpl @Inject constructor(
    private val generativeModel: GenerativeModel,
    private val responseDao: ResponseDao,
    private val resultEntityMapper: ResultEntityMapper,
    private val geminiMapper: GeminiMapper,
    private val geminiApi: GeminiApi,
    @Named("geminiApiKey") private val apiKey: String
) : AIRepository {

    override suspend fun sendAIRequest(prompt: String): AIResponse {
        return try {

            val request = GeminiRequestDto(
                contents = listOf(
                    ContentRequest(
                        parts = listOf(PartRequest(text = prompt))
                    )
                )
            )
            val responseDto = geminiApi.generateContent(apiKey, request)
            geminiMapper.toDomain(prompt, responseDto)
        } catch (e: Exception) {

            val response = generativeModel.generateContent(prompt)
            AIResponse(
                prompt = prompt,
                response = response.text?.trim() ?: "No response generated.",
                timestamp = System.currentTimeMillis()
            )
        }
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
