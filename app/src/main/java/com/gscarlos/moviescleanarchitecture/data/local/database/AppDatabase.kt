package com.gscarlos.moviescleanarchitecture.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gscarlos.moviescleanarchitecture.data.local.dao.MovieDao
import com.gscarlos.moviescleanarchitecture.data.local.model.Movie

@Database(
    entities = [Movie::class],
    version = 1
)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
