package com.example.agriculturalapp.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Simple in-memory implementation of [ResponseDao] for tests and previews.
 *
 * It mirrors the Room DAO contract without persistence.
 */
class InMemoryResponseDao : ResponseDao {

    private val items = MutableStateFlow<List<ResultEntity>>(emptyList())
    private var nextId = 1

    override suspend fun insertResult(result: ResultEntity) {
        val entityToStore = if (result.id == 0) {
            result.copy(id = nextId++)
        } else {
            nextId = maxOf(nextId, result.id + 1)
            result
        }

        items.update { current ->
            current.filterNot { it.id == entityToStore.id } + entityToStore
        }
    }

    override fun getAllResults(): Flow<List<ResultEntity>> {
        return items.asStateFlow()
    }

    override suspend fun deleteResult(result: ResultEntity) {
        items.update { current ->
            current.filterNot { it.id == result.id }
        }
    }

    override suspend fun getResultByAnalysisType(analysisType: String): ResultEntity? {
        return items.value.firstOrNull { it.analysisType == analysisType }
    }
}
