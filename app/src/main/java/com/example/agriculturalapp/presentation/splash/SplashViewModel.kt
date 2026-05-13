package com.example.agriculturalapp.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {
    fun startTimer(onFinished: () -> Unit) {
        viewModelScope.launch {
            delay(2000)
            onFinished()
        }
    }
}
