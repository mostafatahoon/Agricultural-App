package com.example.agriculturalapp.domain.usecase

import com.example.agriculturalapp.domain.entity.AIResponse
import com.example.agriculturalapp.domain.repository.AIRepository
import javax.inject.Inject

class SendPromptUseCase @Inject constructor(
    private val repository: AIRepository
) {
    suspend operator fun invoke(prompt: String): AIResponse {
        return repository.sendAIRequest(prompt)
    }
}

