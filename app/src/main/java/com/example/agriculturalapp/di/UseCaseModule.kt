package com.example.agriculturalapp.di

import com.example.agriculturalapp.domain.repository.AIRepository
import com.example.agriculturalapp.domain.usecase.GeneratePromptUseCase
import com.example.agriculturalapp.domain.usecase.GetSavedResponsesUseCase
import com.example.agriculturalapp.domain.usecase.SaveResultUseCase
import com.example.agriculturalapp.domain.usecase.SendPromptUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGeneratePromptUseCase(): GeneratePromptUseCase {
        return GeneratePromptUseCase()
    }

    @Provides
    @Singleton
    fun provideSendPromptUseCase(repository: AIRepository): SendPromptUseCase {
        return SendPromptUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveResultUseCase(repository: AIRepository): SaveResultUseCase {
        return SaveResultUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetSavedResponsesUseCase(repository: AIRepository): GetSavedResponsesUseCase {
        return GetSavedResponsesUseCase(repository)
    }
}
