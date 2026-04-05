package com.example.agriculturalapp.di

import com.example.agriculturalapp.domain.repository.AnalysisRepository
import com.example.agriculturalapp.domain.usecase.DeleteAnalysisResultUseCase
import com.example.agriculturalapp.domain.usecase.GenerateAIResponseUseCase
import com.example.agriculturalapp.domain.usecase.GetResultByIdUseCase
import com.example.agriculturalapp.domain.usecase.GetSavedResultsUseCase
import com.example.agriculturalapp.domain.usecase.SaveAnalysisResultUseCase
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
    fun provideGenerateAIResponseUseCase(repository: AnalysisRepository): GenerateAIResponseUseCase {
        return GenerateAIResponseUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveAnalysisResultUseCase(repository: AnalysisRepository): SaveAnalysisResultUseCase {
        return SaveAnalysisResultUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetSavedResultsUseCase(repository: AnalysisRepository): GetSavedResultsUseCase {
        return GetSavedResultsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteAnalysisResultUseCase(repository: AnalysisRepository): DeleteAnalysisResultUseCase {
        return DeleteAnalysisResultUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetResultByIdUseCase(repository: AnalysisRepository): GetResultByIdUseCase {
        return GetResultByIdUseCase(repository)
    }
}

