package com.example.agriculturalapp.data.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface ResponseDao {
    @Insert
    suspend fun insertResult(result: ResultEntity)

    @Query("SELECT * FROM results ORDER BY timestamp DESC")
     fun getAllResults(): Flow<List<ResultEntity>>

    @Delete
    suspend fun deleteResult(result: ResultEntity)

    @Query("SELECT * FROM results WHERE prompt = :prompt LIMIT 1")
    suspend fun getResultByPrompt(prompt: String): ResultEntity?


}