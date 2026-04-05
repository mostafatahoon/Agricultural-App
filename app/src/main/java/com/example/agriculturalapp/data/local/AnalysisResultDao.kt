package com.example.agriculturalapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AnalysisResultDao {
    @Insert
    suspend fun insert(result: AnalysisResultEntity): Long

    @Query("SELECT * FROM analysis_results ORDER BY timestamp DESC")
    suspend fun getAllResults(): List<AnalysisResultEntity>

    @Query("SELECT * FROM analysis_results WHERE id = :id")
    suspend fun getResultById(id: Long): AnalysisResultEntity?

    @Query("DELETE FROM analysis_results WHERE id = :id")
    suspend fun deleteById(id: Long): Int

    @Delete
    suspend fun delete(result: AnalysisResultEntity): Int

    @Query("SELECT * FROM analysis_results WHERE analysisType = :type ORDER BY timestamp DESC")
    suspend fun getResultsByType(type: String): List<AnalysisResultEntity>
}

