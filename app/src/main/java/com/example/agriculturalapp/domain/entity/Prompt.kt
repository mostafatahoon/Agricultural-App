package com.example.agriculturalapp.domain.entity

data class Prompt(
    val topicName: String,
    val inputs: List<String>,
    val fullPrompt: String
)