package com.example.agriculturalapp.data.repositoryimpl

import com.example.agriculturalapp.data.local.LocalDataSource
import com.example.agriculturalapp.data.remote.RemoteDataSource
import com.example.agriculturalapp.domain.entity.AnalysisResult
import com.example.agriculturalapp.domain.repository.AnalysisRepository

class AnalysisRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : AnalysisRepository {

    override suspend fun generateAIResponse(prompt: String): String {
        return remoteDataSource.generateAIResponse(prompt)
    }

    override suspend fun saveResult(result: AnalysisResult): Long {
        return localDataSource.insertResult(result)
    }

    override suspend fun getSavedResults(): List<AnalysisResult> {
        return localDataSource.getAllResults()
    }

    override suspend fun deleteResult(id: Long): Int {
        return localDataSource.deleteResult(id)
    }

    override suspend fun getResultById(id: Long): AnalysisResult? {
        return localDataSource.getResultById(id)
    }
}

