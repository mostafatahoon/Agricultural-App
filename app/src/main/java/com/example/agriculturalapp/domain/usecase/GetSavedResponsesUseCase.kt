package com.example.agriculturalapp.domain.usecase

import com.example.agriculturalapp.domain.entity.AIResponse
import com.example.agriculturalapp.domain.repository.AIRepository
import kotlinx.coroutines.flow.Flow

class GetSavedResponsesUseCase (
    private val repository: AIRepository
) {
    operator fun invoke(): Flow<List<AIResponse>> {
        return repository.getSavedResponses()
    }

}

