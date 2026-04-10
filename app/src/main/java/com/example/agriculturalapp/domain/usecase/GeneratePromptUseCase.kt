package com.example.agriculturalapp.domain.usecase

import com.example.agriculturalapp.domain.entity.AnalysisInput
import com.example.agriculturalapp.domain.entity.AnalysisType
import javax.inject.Inject

class GeneratePromptUseCase @Inject constructor() {
    operator fun invoke(input: AnalysisInput): String {
        val basePrompt = when (input.analysisType) {
            AnalysisType.IRRIGATION -> "Provide irrigation advice for the following conditions:"
            AnalysisType.DISEASE -> "Identify the plant disease and suggest treatments based on these symptoms:"
            AnalysisType.FERTILIZER -> "Recommend fertilizer application based on these soil and crop details:"
            AnalysisType.CLIMATE -> "Analyze the impact of these climate conditions on crop yield:"
            AnalysisType.MARKET -> "Provide market price trends and advice for the following crops and regions:"
        }
        
        val details = input.formData.entries.joinToString("\n") { "- ${it.key}: ${it.value}" }
        
        return "$basePrompt\n$details\n\nPlease provide a detailed analysis and recommendations."
    }
}
