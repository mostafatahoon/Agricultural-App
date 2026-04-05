package com.example.agriculturalapp.data.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

interface ResponseDao {
    @Insert
    suspend fun insertResult(result: ResultEntity)

    @Query("SELECT * FROM results ORDER BY timestamp DESC")
    suspend fun getAllResults(): List<ResultEntity>

    @Delete
    suspend fun deleteResult(result: ResultEntity)


}