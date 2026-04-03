package com.example.agriculturalapp.presentation.history

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel


class HistoryViewModel : ViewModel() {
    val results = mutableStateListOf("Field A - Optimal", "Field B - Low Nitrogen")
}