package com.example.agriculturalapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "results")
data class ResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val analysisType: String,

    val inputData: String, // JSON string of user inputs

    val aiResponse: String,

    val timestamp: Long = System.currentTimeMillis(),

    val title: String = "" // Auto-generated title from analysis type
)
