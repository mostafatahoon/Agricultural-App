package com.example.agriculturalapp.domain.usecase

import com.example.agriculturalapp.domain.entity.AnalysisResult
import com.example.agriculturalapp.domain.repository.AnalysisRepository

class GenerateAIResponseUseCase(
    private val repository: AnalysisRepository
) {
    suspend operator fun invoke(prompt: String): String {
        return repository.generateAIResponse(prompt)
    }
}

class SaveAnalysisResultUseCase(
    private val repository: AnalysisRepository
) {
    suspend operator fun invoke(result: AnalysisResult): Long {
        return repository.saveResult(result)
    }
}

class GetSavedResultsUseCase(
    private val repository: AnalysisRepository
) {
    suspend operator fun invoke(): List<AnalysisResult> {
        return repository.getSavedResults()
    }
}

class DeleteAnalysisResultUseCase(
    private val repository: AnalysisRepository
) {
    suspend operator fun invoke(id: Long): Int {
        return repository.deleteResult(id)
    }
}

class GetResultByIdUseCase(
    private val repository: AnalysisRepository
) {
    suspend operator fun invoke(id: Long): AnalysisResult? {
        return repository.getResultById(id)
    }
}

