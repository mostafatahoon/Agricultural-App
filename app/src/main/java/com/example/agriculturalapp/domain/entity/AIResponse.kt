package com.example.agriculturalapp.domain.entity

data class AIResponse(
    val id: Int = 0,
    val prompt: String,
    val response: String,
    val timestamp: Long
)