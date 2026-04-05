package com.example.agriculturalapp.data.local

import com.example.agriculturalapp.domain.entity.AnalysisResult
import com.example.agriculturalapp.domain.entity.AnalysisType

interface LocalDataSource {
    suspend fun insertResult(result: AnalysisResult): Long
    suspend fun getAllResults(): List<AnalysisResult>
    suspend fun getResultById(id: Long): AnalysisResult?
    suspend fun deleteResult(id: Long): Int
    suspend fun getResultsByType(type: AnalysisType): List<AnalysisResult>
}

class LocalDataSourceImpl(
    private val dao: AnalysisResultDao
) : LocalDataSource {

    override suspend fun insertResult(result: AnalysisResult): Long {
        val entity = AnalysisResultEntity(
            id = result.id,
            analysisType = result.analysisType.name,
            inputData = result.inputData,
            aiResponse = result.aiResponse,
            timestamp = result.timestamp
        )
        return dao.insert(entity)
    }

    override suspend fun getAllResults(): List<AnalysisResult> {
        return dao.getAllResults().map { entity ->
            AnalysisResult(
                id = entity.id,
                analysisType = AnalysisType.valueOf(entity.analysisType),
                inputData = entity.inputData,
                aiResponse = entity.aiResponse,
                timestamp = entity.timestamp
            )
        }
    }

    override suspend fun getResultById(id: Long): AnalysisResult? {
        return dao.getResultById(id)?.let { entity ->
            AnalysisResult(
                id = entity.id,
                analysisType = AnalysisType.valueOf(entity.analysisType),
                inputData = entity.inputData,
                aiResponse = entity.aiResponse,
                timestamp = entity.timestamp
            )
        }
    }

    override suspend fun deleteResult(id: Long): Int {
        return dao.deleteById(id)
    }

    override suspend fun getResultsByType(type: AnalysisType): List<AnalysisResult> {
        return dao.getResultsByType(type.name).map { entity ->
            AnalysisResult(
                id = entity.id,
                analysisType = AnalysisType.valueOf(entity.analysisType),
                inputData = entity.inputData,
                aiResponse = entity.aiResponse,
                timestamp = entity.timestamp
            )
        }
    }
}

