package com.example.agriculturalapp.domain.entity

data class AnalysisInput(
    val analysisType: AnalysisType,
    val formData: Map<String, String>
)
