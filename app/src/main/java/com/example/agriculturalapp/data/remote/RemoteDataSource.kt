package com.example.agriculturalapp.data.remote

import com.example.agriculturalapp.data.dtomodel.Content
import com.example.agriculturalapp.data.dtomodel.GeminiRequest
import com.example.agriculturalapp.data.dtomodel.Part

interface RemoteDataSource {
    suspend fun generateAIResponse(prompt: String): String
}

class GeminiRemoteDataSource(
    private val geminiApi: GeminiApi,
    private val apiKey: String
) : RemoteDataSource {

    override suspend fun generateAIResponse(prompt: String): String {
        try {
            // Build the request
            val request = GeminiRequest(
                contents = listOf(
                    Content(
                        parts = listOf(
                            Part(text = prompt)
                        )
                    )
                )
            )

            // Call API
            val response = geminiApi.generateContent(apiKey, request)

            // Extract text from response
            return response.candidates.firstOrNull()
                ?.content
                ?.parts
                ?.firstOrNull()
                ?.text
                ?: throw IllegalStateException("No response from Gemini API")

        } catch (e: Exception) {
            throw IllegalStateException("Failed to generate AI response: ${e.message}", e)
        }
    }
}

