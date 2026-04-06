package com.example.agriculturalapp.presentation.userInput

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.agriculturalapp.domain.entity.Prompt
import com.example.agriculturalapp.domain.entity.Topic
import com.example.agriculturalapp.domain.entity.Word
import com.example.agriculturalapp.domain.usecase.GeneratePromptUseCase
import com.example.agriculturalapp.domain.usecase.GetTopicsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserInputScreenViewModel @Inject constructor(
    private val getTopicsUseCase: GetTopicsUseCase,
    private val generatePromptUseCase: GeneratePromptUseCase
) : ViewModel() {

    var topics by mutableStateOf<List<Topic>>(emptyList())
        private set

    var selectedTopic by mutableStateOf<Topic?>(null)
        private set

    var selectedWords by mutableStateOf<List<Word>>(emptyList())
        private set

    init {
        topics = getTopicsUseCase()
    }

    fun selectTopic(topic: Topic) {
        selectedTopic = topic
        selectedWords = emptyList() // Reset words when topic changes
    }

    fun toggleWordSelection(word: Word) {
        selectedWords = if (selectedWords.contains(word)) {
            selectedWords - word
        } else {
            selectedWords + word
        }
    }

    fun generatePrompt(): Prompt? {
        val topic = selectedTopic ?: return null
        return generatePromptUseCase(topic, selectedWords)
    }
}
