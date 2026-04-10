package com.example.agriculturalapp.domain.usecase

import com.example.agriculturalapp.domain.entity.AnalysisInput
import com.example.agriculturalapp.domain.repository.AIRepository
import com.google.gson.Gson
import javax.inject.Inject

class SaveResultUseCase @Inject constructor(
    private val repository: AIRepository
) {
    suspend operator fun invoke(
        input: AnalysisInput,
        response: String
    ) {
        val inputJson = Gson().toJson(input.formData)
        // analysisType.id is a stable string identifier
        val title = "${input.analysisType.id.replaceFirstChar { it.uppercase() }} Analysis"
        
        repository.saveAnalysisResult(
            analysisType = input.analysisType.id,
            inputData = inputJson,
            aiResponse = response,
            title = title
        )
    }
}
