package com.example.agriculturalapp.presentation.userInput

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agriculturalapp.data.static.StaticDataProvider
import com.example.agriculturalapp.domain.entity.AnalysisInput
import com.example.agriculturalapp.domain.entity.AnalysisType
import com.example.agriculturalapp.domain.usecase.GeneratePromptUseCase
import com.example.agriculturalapp.domain.usecase.SendPromptUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    private val generatePrompt: GeneratePromptUseCase,
    private val sendPrompt: SendPromptUseCase
) : ViewModel() {

    private val _formData = MutableStateFlow<Map<String, String>>(emptyMap())
    val formData: StateFlow<Map<String, String>> = _formData

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _response = MutableStateFlow<String>("")
    val response: StateFlow<String> = _response

    fun updateField(fieldId: String, value: String) {
        _formData.value = _formData.value.toMutableMap().apply {
            put(fieldId, value)
        }
    }

    fun submitForm(analysisType: AnalysisType) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val input = AnalysisInput(analysisType, _formData.value)
                val prompt = generatePrompt(input)
                val result = sendPrompt(prompt)
                _response.value = result.response
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
                _response.value = ""
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getFieldsForAnalysis(analysisType: AnalysisType) =
        StaticDataProvider.getFieldsForAnalysis(analysisType)
}
