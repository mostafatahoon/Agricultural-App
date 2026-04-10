package com.example.agriculturalapp.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agriculturalapp.domain.entity.AIResponse
import com.example.agriculturalapp.domain.usecase.GetSavedResponsesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    getSavedResponsesUseCase: GetSavedResponsesUseCase
) : ViewModel() {

    val results: StateFlow<List<AIResponse>> = getSavedResponsesUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}