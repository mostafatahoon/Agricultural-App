package com.example.agriculturalapp.presentation.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    val title = "AgrInsight AI"
    val description = "Harness high-resolution satellite data and machine learning to optimize crop yields."
}
