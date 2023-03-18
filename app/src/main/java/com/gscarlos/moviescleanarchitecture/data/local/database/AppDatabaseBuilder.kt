package com.gscarlos.moviescleanarchitecture.data.local.database

import android.content.Context
import androidx.room.Room

object AppDatabaseBuilder {

    private const val DATABASE_NAME = "database-movies"

    fun getInstance(context: Context) : AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, DATABASE_NAME
        ).allowMainThreadQueries().build()
    }
}