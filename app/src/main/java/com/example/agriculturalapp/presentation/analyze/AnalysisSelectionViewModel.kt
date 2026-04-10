package com.example.agriculturalapp.presentation.analyze

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.agriculturalapp.domain.entity.AnalysisType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnalysisSelectionViewModel @Inject constructor() : ViewModel() {
    private val _selectedType = mutableStateOf<AnalysisType?>(AnalysisType.IRRIGATION)
    val selectedType: State<AnalysisType?> = _selectedType

    fun selectType(type: AnalysisType) {
        _selectedType.value = type
    }
}
