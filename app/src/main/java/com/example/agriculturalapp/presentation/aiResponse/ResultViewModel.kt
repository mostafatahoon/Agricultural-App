package com.example.agriculturalapp.presentation.aiResponse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agriculturalapp.domain.entity.AnalysisInput
import com.example.agriculturalapp.domain.usecase.SaveResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val saveResult: SaveResultUseCase
) : ViewModel() {

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving

    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess: StateFlow<Boolean> = _saveSuccess

    fun saveAnalysis(input: AnalysisInput, response: String) {
        viewModelScope.launch {
            _isSaving.value = true
            try {
                saveResult(input, response)
                _saveSuccess.value = true
            } catch (e: Exception) {
                _saveSuccess.value = false
            } finally {
                _isSaving.value = false
            }
        }
    }
}

