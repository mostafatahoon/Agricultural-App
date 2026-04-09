package com.example.agriculturalapp.domain.usecase

import javax.inject.Inject

class GeneratePromptUseCase @Inject constructor() {

    operator fun invoke(input: com.example.agriculturalapp.domain.entity.AnalysisInput): String {
        val template = com.example.agriculturalapp.data.provider.StaticDataProvider
            .getPromptTemplate(input.analysisType)

        // Format farm data from input
        val farmDataText = input.formData.entries.joinToString("\n") { entry ->
            "- ${entry.key}: ${entry.value}"
        }

        return template.replace("%FARM_DATA%", farmDataText)
    }
}
