package com.example.agriculturalapp.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashViewModel : ViewModel() {
    fun startTimer(onFinished: () -> Unit) {
        viewModelScope.launch {
            delay(2000)
            onFinished()
        }
    }
}