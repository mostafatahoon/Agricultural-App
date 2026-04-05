package com.example.agriculturalapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AnalysisResultEntity::class], version = 1, exportSchema = false)
abstract class AgriInsightDatabase : RoomDatabase() {

    abstract fun analysisResultDao(): AnalysisResultDao

    companion object {
        @Volatile
        private var instance: AgriInsightDatabase? = null

        fun getInstance(context: Context): AgriInsightDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AgriInsightDatabase::class.java,
                    "agriinsight_database"
                ).build().also { instance = it }
            }
        }
    }
}

