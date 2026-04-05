package com.example.agriculturalapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.agriculturalapp.data.dtomodel.Candidate

@Entity(tableName = "results")
data class ResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val analysisType: String,

    val aiResponse: String,

    val timestamp: Long = System.currentTimeMillis()
)
