package com.example.agriculturalapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ResultEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun responseDao(): ResponseDao
}
