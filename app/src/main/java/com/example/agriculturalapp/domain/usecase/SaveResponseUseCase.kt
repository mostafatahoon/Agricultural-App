package com.example.agriculturalapp.domain.usecase

import com.example.agriculturalapp.domain.entity.AIResponse
import com.example.agriculturalapp.domain.repository.AIRepository
import javax.inject.Inject

class SaveResponseUseCase @Inject constructor(
    private val repository: AIRepository
) {
    suspend operator fun invoke(response: AIResponse) {
        repository.saveResponse(response)
    }
}
