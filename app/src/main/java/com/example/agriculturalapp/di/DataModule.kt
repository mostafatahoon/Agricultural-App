package com.example.agriculturalapp.di

import android.content.Context
import com.example.agriculturalapp.data.local.AgriInsightDatabase
import com.example.agriculturalapp.data.local.AnalysisResultDao
import com.example.agriculturalapp.data.local.LocalDataSource
import com.example.agriculturalapp.data.local.LocalDataSourceImpl
import com.example.agriculturalapp.data.remote.GeminiApi
import com.example.agriculturalapp.data.remote.GeminiRemoteDataSource
import com.example.agriculturalapp.data.remote.RemoteDataSource
import com.example.agriculturalapp.data.repositoryimpl.AnalysisRepositoryImpl
import com.example.agriculturalapp.domain.repository.AnalysisRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private const val GEMINI_BASE_URL = "https://generativelanguage.googleapis.com/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GEMINI_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGeminiApi(retrofit: Retrofit): GeminiApi {
        return retrofit.create(GeminiApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(geminiApi: GeminiApi): RemoteDataSource {
        return GeminiRemoteDataSource(geminiApi, "AIzaSyAhT9eDlwKoyYVcAQpMifVBvB216B9_0Y")
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AgriInsightDatabase {
        return AgriInsightDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideAnalysisResultDao(database: AgriInsightDatabase) = database.analysisResultDao()

    @Provides
    @Singleton
    fun provideLocalDataSource(dao: AnalysisResultDao): LocalDataSource {
        return LocalDataSourceImpl(dao)
    }

    @Provides
    @Singleton
    fun provideAnalysisRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): AnalysisRepository {
        return AnalysisRepositoryImpl(remoteDataSource, localDataSource)
    }
}

