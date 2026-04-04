package com.example.agriculturalapp.domain.usecase

import com.example.agriculturalapp.domain.entity.AIResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal object ResponseMemoryStore {
    private val _responses = MutableStateFlow<List<AIResponse>>(emptyList())

    val responses: Flow<List<AIResponse>> = _responses.asStateFlow()

    fun add(response: AIResponse) {
        _responses.value += response
    }

    fun clear() {
        _responses.value = emptyList()
    }

}

