package com.example.agriculturalapp.data.remote

import androidx.navigation.compose.rememberNavController
import com.example.agriculturalapp.data.dtomodel.GeminiRequestDto
import com.example.agriculturalapp.data.dtomodel.GeminiResponseDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiApi {

    @POST("v1/models/gemini-pro:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequestDto
    ): GeminiResponseDto
}
