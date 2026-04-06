package com.example.agriculturalapp.domain.usecase

import com.example.agriculturalapp.domain.entity.AIResponse
import com.example.agriculturalapp.domain.repository.AIRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedResponsesUseCase @Inject constructor(
    private val repository: AIRepository
) {
    operator fun invoke(): Flow<List<AIResponse>> = repository.getSavedResponses()
}
