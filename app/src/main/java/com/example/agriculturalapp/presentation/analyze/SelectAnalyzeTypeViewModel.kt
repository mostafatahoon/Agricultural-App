package com.example.agriculturalapp.presentation.analyze

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agriculturalapp.domain.usecase.GetAIResponseUseCase
import com.example.agriculturalapp.domain.usecase.SaveResponseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SelectAnalyzeTypeViewModel @Inject constructor(
    private val getAIResponseUseCase: GetAIResponseUseCase,
    private val saveResponseUseCase: SaveResponseUseCase
) : ViewModel() {

    private val _isAnalyzing = mutableStateOf(false)
    val isAnalyzing: State<Boolean> = _isAnalyzing

    private val _analysisResult = mutableStateOf<String?>(null)
    val analysisResult: State<String?> = _analysisResult

    fun startAnalysis(prompt: String = "Analyze my field health and suggest improvements") {
        viewModelScope.launch {
            _isAnalyzing.value = true
            _analysisResult.value = null

            runCatching {
                getAIResponseUseCase(prompt)
            }.onSuccess { response ->
                saveResponseUseCase(response)
                _analysisResult.value = response.response
            }.onFailure { throwable ->
                _analysisResult.value = "Analysis failed: ${throwable.message ?: "Unknown error"}"
            }

            _isAnalyzing.value = false
        }
    }
}