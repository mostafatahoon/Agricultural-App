package com.example.agriculturalapp.domain.usecase

import com.example.agriculturalapp.domain.entity.Topic
import com.example.agriculturalapp.domain.entity.Word


class GetTopicsUseCase(private val topics: List<Topic>) {
    operator fun invoke(): List<Topic> = topics
}


