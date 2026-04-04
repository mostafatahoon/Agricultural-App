package com.example.agriculturalapp.domain.usecase

import com.example.agriculturalapp.domain.entity.AIResponse
import com.example.agriculturalapp.domain.entity.Prompt
import com.example.agriculturalapp.domain.repository.AIRepository

class GetAIResponseUseCase(
    private val repository: AIRepository
) {

    suspend operator fun invoke(prompt: String): AIResponse {
        val response = repository.getAIResponse(prompt)
        return response
    }


}

