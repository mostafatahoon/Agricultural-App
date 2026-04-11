package com.example.agriculturalapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AIResponse(
    val id: Int = 0,
    val prompt: String,
    val response: String,
    val timestamp: Long
) : Parcelable