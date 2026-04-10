package com.example.agriculturalapp.domain.usecase

import com.example.agriculturalapp.domain.entity.Prompt
import com.example.agriculturalapp.domain.entity.Topic

class GeneratePromptUseCase {

    operator fun invoke(
        topic: Topic,
        inputs: List<String>
    ): Prompt {

        val finalPrompt = String.format(
            topic.promptTemplate,
            *inputs.toTypedArray()
        )

        return Prompt(
            topicName = topic.name,
            inputs = inputs,
            fullPrompt = finalPrompt
        )
    }
}

