package com.example.agriculturalapp.di

import android.content.Context
import androidx.room.Room
import com.example.agriculturalapp.data.local.AppDatabase
import com.example.agriculturalapp.data.local.ResponseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "agricultural_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideResponseDao(database: AppDatabase): ResponseDao {
        return database.responseDao()
    }
}
