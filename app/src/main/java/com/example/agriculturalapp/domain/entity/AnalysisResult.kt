package com.example.agriculturalapp.domain.entity

import java.time.LocalDateTime

enum class AnalysisType {
    IRRIGATION,
    DISEASE,
    FERTILIZER,
    CLIMATE,
    MARKET
}

data class AnalysisResult(
    val id: Long = 0,
    val analysisType: AnalysisType,
    val inputData: String?, // Can be JSON or plain string
    val aiResponse: String,
    val timestamp: Long = System.currentTimeMillis()
)

