package com.example.agriculturalapp.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

/**
 * In-Memory implementation of ResponseDao for testing purposes.
 * This implementation stores all data in memory without persistence.
 *
 * Clean Architecture: Part of the Data Layer (Local Data Source)
 * Follows: Single Responsibility Principle
 */
class InMemoryResponseDao : ResponseDao {
    
    private val items = MutableStateFlow<List<ResultEntity>>(emptyList())

    /**
     * Inserts a result entity into the in-memory store.
     * Replaces existing result if same ID exists.
     *
     * @param result The ResultEntity to insert
     */
    override suspend fun insertResult(result: ResultEntity) {
        items.value = listOf(result) + items.value.filterNot { it.id == result.id }
    }

    /**
     * Retrieves all results ordered by timestamp (newest first).
     *
     * @return Flow of ResultEntity list
     */
    override fun getAllResults(): Flow<List<ResultEntity>> {
        return items.map { list -> 
            list.sortedByDescending { it.timestamp } 
        }
    }

    /**
     * Deletes a result entity from the in-memory store.
     *
     * @param result The ResultEntity to delete
     */
    override suspend fun deleteResult(result: ResultEntity) {
        items.value = items.value.filterNot { 
            it.id == result.id && it.timestamp == result.timestamp 
        }
    }

    /**
     * Retrieves a single result by analysis type.
     *
     * @param analysisType The analysis type to search for
     * @return The first ResultEntity matching the analysis type, or null if not found
     */
    override suspend fun getResultByAnalysisType(analysisType: String): ResultEntity? {
        return items.value.firstOrNull { it.analysisType == analysisType }
    }
}



