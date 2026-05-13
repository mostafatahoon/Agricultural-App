package com.example.agriculturalapp.data.dtomodel

data class GeminiRequestDto(
    val contents: List<ContentRequest>
)

data class ContentRequest(
    val role: String = "user",
    val parts: List<PartRequest>
)

data class PartRequest(
    val text: String
)
