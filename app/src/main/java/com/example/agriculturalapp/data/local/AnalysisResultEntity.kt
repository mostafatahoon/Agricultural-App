package com.example.agriculturalapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "analysis_results")
data class AnalysisResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val analysisType: String,
    val inputData: String?,
    val aiResponse: String,
    val timestamp: Long
)

