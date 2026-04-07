package com.example.agriculturalapp.di

import com.example.agriculturalapp.data.local.ResponseDao
import com.example.agriculturalapp.data.repositoryimpl.AIRepositoryImpl
import com.example.agriculturalapp.domain.repository.AIRepository
import com.google.ai.client.generativeai.GenerativeModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAIRepository(
        generativeModel: GenerativeModel,
        dao: ResponseDao
    ): AIRepository {
        return AIRepositoryImpl(generativeModel, dao)
    }
}
