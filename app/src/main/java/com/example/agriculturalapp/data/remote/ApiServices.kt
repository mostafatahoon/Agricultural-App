package com.example.agriculturalapp.data.remote

import com.example.agriculturalapp.data.dtomodel.GeminiRequest
import com.example.agriculturalapp.data.dtomodel.GeminiResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServices {
    @POST("v1/models/gemini-pro:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}
