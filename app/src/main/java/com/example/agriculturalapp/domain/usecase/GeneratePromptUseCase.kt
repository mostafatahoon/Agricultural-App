package com.example.agriculturalapp.domain.usecase

import com.example.agriculturalapp.domain.entity.AnalysisInput
import com.example.agriculturalapp.domain.entity.AnalysisType
import java.util.Locale
import javax.inject.Inject

class GeneratePromptUseCase @Inject constructor() {
    operator fun invoke(input: AnalysisInput): String {
        val currentLanguage = Locale.getDefault().language
        val isArabic = currentLanguage == "ar"
        
        val languageInstruction = if (isArabic) {
            "IMPORTANT: Your entire response MUST be in Arabic language. Use a professional and helpful tone suitable for a farmer."
        } else {
            "Respond in English."
        }

        val basePrompt = when (input.analysisType) {
            AnalysisType.IRRIGATION -> "Provide irrigation advice for the following conditions:"
            AnalysisType.DISEASE -> "Identify the plant disease and suggest treatments based on these symptoms:"
            AnalysisType.FERTILIZER -> "Recommend fertilizer application based on these soil and crop details:"
            AnalysisType.CLIMATE -> "Analyze the impact of these climate conditions on crop yield:"
            AnalysisType.MARKET -> "Provide market price trends and advice for the following crops and regions:"
        }
        
        val details = input.formData.entries.joinToString("\n") { "- ${it.key}: ${it.value}" }
        
        val formatInstruction = """
            Please provide a detailed analysis and recommendations.
            Format your response using the following structure:
            ## [Section Title]
            * Bullet points for key recommendations
            Use **bold text** for emphasis on important values or chemicals.
            Do not use more than 3 levels of headers.
        """.trimIndent()
        
        return "$languageInstruction\n\n$basePrompt\n$details\n\n$formatInstruction"
    }
}
