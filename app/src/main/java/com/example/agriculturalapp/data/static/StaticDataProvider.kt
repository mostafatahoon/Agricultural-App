package com.example.agriculturalapp.data.provider

import com.example.agriculturalapp.data.local.AnalysisType

object StaticDataProvider {

    fun getPromptTemplate(analysisType: AnalysisType): String {
        return when (analysisType) {
            AnalysisType.IRRIGATION ->
                "As an agricultural expert, analyze the irrigation setup using the following farm data:\n%FARM_DATA%\nProvide practical recommendations."
            AnalysisType.PLANTING_ADVICE ->
                "As an agricultural expert, provide planting advice based on this farm data:\n%FARM_DATA%\nInclude timing, spacing, and crop-specific guidance."
            AnalysisType.PEST_CONTROL ->
                "As an agricultural expert, suggest pest control actions using this farm data:\n%FARM_DATA%\nPrioritize safe and effective methods."
            AnalysisType.SOIL_HEALTH ->
                "As an agricultural expert, assess soil health from this farm data:\n%FARM_DATA%\nRecommend improvement steps."
        }
    }
}

