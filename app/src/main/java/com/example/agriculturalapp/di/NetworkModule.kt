package com.example.agriculturalapp.di

import com.example.agriculturalapp.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("geminiApiKey")
    fun provideGeminiApiKey(): String = BuildConfig.GEMINI_API_KEY

    @Provides
    @Singleton
    fun provideGenerativeModel(@Named("geminiApiKey") apiKey: String): GenerativeModel {
        return GenerativeModel(
            modelName = "gemini-2.5-flash",
            apiKey = apiKey,
            requestOptions = RequestOptions(apiVersion = "v1beta")
        )
    }
}
