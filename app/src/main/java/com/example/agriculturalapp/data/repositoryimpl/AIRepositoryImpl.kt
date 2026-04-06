package com.example.agriculturalapp.data.repositoryimpl

import com.example.agriculturalapp.data.dtomodel.Content
import com.example.agriculturalapp.data.dtomodel.GeminiRequestDto
import com.example.agriculturalapp.data.dtomodel.Part
import com.example.agriculturalapp.data.local.ResponseDao
import com.example.agriculturalapp.data.mapper.extractText
import com.example.agriculturalapp.data.mapper.toDomain
import com.example.agriculturalapp.data.mapper.toEntity
import com.example.agriculturalapp.data.remote.GeminiApi
import com.example.agriculturalapp.domain.entity.AIResponse
import com.example.agriculturalapp.domain.repository.AIRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AIRepositoryImpl(
    private val api: GeminiApi,
    private val dao: ResponseDao,
    private val apiKey: String
) : AIRepository {

    override suspend fun getAIResponse(prompt: String): AIResponse {


        val response = sendAIRequest(prompt)

        saveResponse(response)

        return response
    }

    override suspend fun saveResponse(response: AIResponse) {
        dao.insertResult(response.toEntity())
    }

    override fun getSavedResponses(): Flow<List<AIResponse>> {
        return dao.getAllResults().map { list ->
            list.map { it.toDomain() }
        }
    }


    override suspend fun sendAIRequest(prompt: String): AIResponse {

        val request = GeminiRequestDto(
            contents = listOf(
                Content(
                    parts = listOf(
                        Part(text = prompt)
                    )
                )
            )
        )

        val apiResponse = api.generateContent(apiKey, request)

        val text = apiResponse.extractText()

        return AIResponse(
            prompt = prompt,
            response = text,
            timestamp = System.currentTimeMillis()
        )
    }
}