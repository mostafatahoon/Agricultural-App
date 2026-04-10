package com.example.agriculturalapp.domain.entity

import com.example.agriculturalapp.data.local.AnalysisType

data class AnalysisInput(
    val analysisType: AnalysisType,
    val formData: Map<String, String>
)

