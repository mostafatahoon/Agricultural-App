package com.example.agriculturalapp.presentation.userInput

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.agriculturalapp.data.local.AnalysisType
import com.example.agriculturalapp.domain.entity.AnalysisInput
import com.example.agriculturalapp.domain.entity.Topic
import com.example.agriculturalapp.domain.entity.Word
import com.example.agriculturalapp.domain.usecase.GeneratePromptUseCase
import com.example.agriculturalapp.domain.usecase.GetTopicsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserInputScreenViewModel @Inject constructor(
    getTopicsUseCase: GetTopicsUseCase,
    private val generatePromptUseCase: GeneratePromptUseCase
) : ViewModel() {

    var topics by mutableStateOf<List<Topic>>(getTopicsUseCase())
        private set

    var selectedTopic by mutableStateOf<Topic?>(null)
        private set

    var selectedWords by mutableStateOf<List<Word>>(emptyList())
        private set

    fun selectTopic(topic: Topic) {
        selectedTopic = topic
        selectedWords = emptyList()
    }

    fun toggleWordSelection(word: Word) {
        selectedWords = if (selectedWords.contains(word)) selectedWords - word else selectedWords + word
    }

    fun generatePrompt(): String? {
        val topic = selectedTopic ?: return null
        val mappedType = AnalysisType.fromId(topic.name.lowercase()) ?: AnalysisType.IRRIGATION
        val formData = selectedWords.associate { it.text to it.text }
        return generatePromptUseCase(AnalysisInput(mappedType, formData))
    }
}
