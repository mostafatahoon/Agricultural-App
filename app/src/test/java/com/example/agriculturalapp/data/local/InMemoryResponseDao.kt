package com.example.agriculturalapp.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

/**
 * In-memory fake of ResponseDao for unit tests only.
 */
class InMemoryResponseDao : ResponseDao {

    private val items = MutableStateFlow<List<ResultEntity>>(emptyList())

    override suspend fun insertResult(result: ResultEntity) {
        items.value = listOf(result) + items.value.filterNot { it.id == result.id }
    }

    override fun getAllResults(): Flow<List<ResultEntity>> {
        return items.map { list ->
            list.sortedByDescending { it.timestamp }
        }
    }

    override suspend fun deleteResult(result: ResultEntity) {
        items.value = items.value.filterNot {
            it.id == result.id && it.timestamp == result.timestamp
        }
    }

    override suspend fun getResultByAnalysisType(analysisType: String): ResultEntity? {
        return items.value.firstOrNull { it.analysisType == analysisType }
    }
}

