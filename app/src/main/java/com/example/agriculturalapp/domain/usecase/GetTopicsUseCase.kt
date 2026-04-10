package com.example.agriculturalapp.domain.usecase

import com.example.agriculturalapp.domain.entity.Topic
import com.example.agriculturalapp.domain.entity.Word
import javax.inject.Inject

class GetTopicsUseCase @Inject constructor() {
    private val topics = listOf(
        Topic(
            id = 1,
            name = "Planting Advice",
            description = "Get expert advice on when and how to plant various crops.",
            words = listOf(
                Word(1, "Wheat"),
                Word(2, "Corn"),
                Word(3, "Rice")
            ),
            promptTemplate = "As an agricultural expert, provide detailed planting advice for the following: %WORDS%."
        ),
        Topic(
            id = 2,
            name = "Pest Control",
            description = "Learn how to manage and eliminate common agricultural pests.",
            words = listOf(
                Word(4, "Aphids"),
                Word(5, "Locusts"),
                Word(6, "Spider Mites")
            ),
            promptTemplate = "As an agricultural expert, suggest effective pest control methods for: %WORDS%."
        ),
        Topic(
            id = 3,
            name = "Soil Health",
            description = "Understand soil composition and how to improve its fertility.",
            words = listOf(
                Word(7, "Nitrogen"),
                Word(8, "Phosphorus"),
                Word(9, "Potassium")
            ),
            promptTemplate = "As an agricultural expert, provide tips on improving soil health for: %WORDS%."
        )
    )

    operator fun invoke(): List<Topic> = topics
}
