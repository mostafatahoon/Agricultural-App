package com.example.agriculturalapp.domain.usecase

import com.example.agriculturalapp.domain.entity.Prompt
import com.example.agriculturalapp.domain.entity.Topic
import com.example.agriculturalapp.domain.entity.Word

class GeneratePromptUseCase {

    operator fun invoke(topic: Topic, selectedWords: List<Word>): Prompt {
        val wordsText = selectedWords.joinToString(", ") { it.text }
        val finalPrompt = topic.promptTemplate.replace("%WORDS%", wordsText)

        return Prompt(
            topicName = topic.name,
            selectedWords = selectedWords.map { it.text },
            fullPrompt = finalPrompt
        )
    }

}

