package com.example.agriculturalapp.presentation.analyze

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SelectAnalyzeTypeViewModel : ViewModel() {

    private val _isAnalyzing = mutableStateOf(false)
    val isAnalyzing: State<Boolean> = _isAnalyzing

    private val _analysisResult = mutableStateOf<String?>(null)
    val analysisResult: State<String?> = _analysisResult

    fun startAnalysis() {
        viewModelScope.launch {
            _isAnalyzing.value = true
            _analysisResult.value = null

            delay(3000)

            _isAnalyzing.value = false
            _analysisResult.value = "Analysis Complete: Soil is Healthy (98%)"
        }
    }
}