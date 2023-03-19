package com.gscarlos.moviescleanarchitecture.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.gscarlos.moviescleanarchitecture.data.local.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao : BaseDao<MovieEntity> {

    @Query("SELECT COUNT(id) FROM movies")
    fun getMoviesCount(): Int

    @Query("SELECT * FROM movies")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id=:id")
    fun findMovieById(id: Int): Flow<MovieEntity>

    @Query("UPDATE movies SET favorite=:isFavorite WHERE id=:id")
    fun toggleFavorite(id: Int, isFavorite: Boolean)
}