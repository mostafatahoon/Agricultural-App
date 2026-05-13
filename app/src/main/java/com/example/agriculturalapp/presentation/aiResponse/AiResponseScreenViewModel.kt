package com.example.agriculturalapp.presentation.aiResponse

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agriculturalapp.domain.entity.AIResponse
import com.example.agriculturalapp.domain.usecase.GetAIResponseUseCase
import com.example.agriculturalapp.domain.usecase.SaveResponseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AiResponseScreenViewModel @Inject constructor(
    private val getAIResponseUseCase: GetAIResponseUseCase,
    private val saveResponseUseCase: SaveResponseUseCase
) : ViewModel() {

    var uiState by mutableStateOf<AiResponseUiState>(AiResponseUiState.Initial)
        private set

    fun getAiResponse(prompt: String) {
        viewModelScope.launch {
            uiState = AiResponseUiState.Loading
            try {
                val response = getAIResponseUseCase(prompt)
                uiState = AiResponseUiState.Success(response)
            } catch (e: Exception) {
                uiState = AiResponseUiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun saveResponse(response: AIResponse) {
        viewModelScope.launch {
            saveResponseUseCase(response)
        }
    }
}

    sealed interface AiResponseUiState {
        object Initial : AiResponseUiState
        object Loading : AiResponseUiState
        data class Success(val response: AIResponse) : AiResponseUiState
        data class Error(val message: String) : AiResponseUiState
    }
