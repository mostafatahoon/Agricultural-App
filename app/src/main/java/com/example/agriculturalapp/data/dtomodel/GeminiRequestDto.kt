package com.example.agriculturalapp.data.dtomodel

data class GeminiRequestDto(
    val contents: List<Content>
)

data class Content(
    val role: String = "user",
    val parts: List<Part>
)

data class Part(
    val text: String
)
