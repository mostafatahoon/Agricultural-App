package com.example.agriculturalapp.domain.entity

data class Prompt(
    val topicName: String,
    val selectedWords: List<String>,
    val fullPrompt: String
)