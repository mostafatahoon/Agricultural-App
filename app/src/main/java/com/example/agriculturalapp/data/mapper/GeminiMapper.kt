package com.example.agriculturalapp.data.mapper

import com.example.agriculturalapp.data.dtomodel.GeminiResponse
import com.example.agriculturalapp.domain.entity.AIResponse
import javax.inject.Inject

class GeminiMapper @Inject constructor() {
    fun toDomain(prompt: String, response: GeminiResponse): AIResponse {
        val text = response.candidates
            .firstOrNull()
            ?.content
            ?.parts
            ?.firstOrNull()
            ?.text
            ?.trim()
            .orEmpty()

        return AIResponse(
            prompt = prompt,
            response = text.ifBlank { "No response generated." },
            timestamp = System.currentTimeMillis()
        )
    }
}

