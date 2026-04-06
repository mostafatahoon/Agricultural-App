package com.example.agriculturalapp.presentation.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agriculturalapp.domain.entity.AIResponse
import com.example.agriculturalapp.domain.usecase.GetSavedResponsesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getSavedResponsesUseCase: GetSavedResponsesUseCase
) : ViewModel() {

    var results by mutableStateOf<List<AIResponse>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            getSavedResponsesUseCase().collectLatest { saved ->
                results = saved
            }
        }
    }
}