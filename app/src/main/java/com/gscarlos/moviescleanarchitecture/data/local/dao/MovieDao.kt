package com.gscarlos.moviescleanarchitecture.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.gscarlos.moviescleanarchitecture.data.local.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao : BaseDao<Movie> {

    @Query("SELECT COUNT(id) FROM Movie")
    fun getMoviesCount(): Int

    @Query("SELECT * FROM Movie")
    suspend fun getMovies(): List<Movie>

    @Query("SELECT * FROM Movie WHERE id=:id")
    fun findMovieById(id: Int): Flow<Movie>

    @Query("UPDATE Movie SET favorite=:isFavorite WHERE id=:id")
    fun toggleFavorite(id: Int, isFavorite: Boolean)
}