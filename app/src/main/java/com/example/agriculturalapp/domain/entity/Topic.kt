package com.example.agriculturalapp.domain.entity

data class Topic(
    val id: Int,
    val name: String,
    val description: String,
    val words: List<Word>,
    val promptTemplate: String
)
