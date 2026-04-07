package com.example.agriculturalapp.domain.usecase

import com.example.agriculturalapp.data.local.AnalysisType
import com.example.agriculturalapp.data.static.StaticDataProvider
import com.example.agriculturalapp.domain.entity.AnalysisInput
import javax.inject.Inject

class GeneratePromptUseCase @Inject constructor() {

    operator fun invoke(input: AnalysisInput): String {
        val analysisType = input.analysisType
        val template = StaticDataProvider.getPromptTemplate(analysisType)
        
        // Format farm data from input
        val farmDataText = input.formData.entries.joinToString("\n") { (key, value) ->
            "- $key: $value"
        }
        
        return template.replace("%FARM_DATA%", farmDataText)
    }
}
