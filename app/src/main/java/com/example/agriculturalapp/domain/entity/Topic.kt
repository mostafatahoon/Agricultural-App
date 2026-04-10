package com.example.agriculturalapp.domain.entity

data class Topic(
    val id: Int,
    val name: String,
    val promptTemplate: String,
    val inputFields: List<String>
)
