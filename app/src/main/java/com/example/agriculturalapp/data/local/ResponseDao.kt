package com.example.agriculturalapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ResponseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: ResultEntity)

    @Query("SELECT * FROM results ORDER BY timestamp DESC")
    fun getAllResults(): Flow<List<ResultEntity>>

    @Delete
    suspend fun deleteResult(result: ResultEntity)

    @Query("SELECT * FROM results WHERE analysisType = :analysisType LIMIT 1")
    suspend fun getResultByAnalysisType(analysisType: String): ResultEntity?
}