package com.gscarlos.moviescleanarchitecture.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.gscarlos.moviescleanarchitecture.data.local.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao : BaseDao<MovieEntity> {

    @Query("SELECT COUNT(id) FROM movies")
    fun getMoviesCount(): Int

    @Query("SELECT * FROM movies WHERE popular=1")
    fun getPopularMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE mostRated=1")
    fun getMostRatedMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE recommended=1")
    fun getRecommendedMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id=:id")
    fun findMovieById(id: Int): Flow<MovieEntity>

    @Query("UPDATE movies SET favorite=:isFavorite WHERE id=:id")
    fun updateFavorite(id: Int, isFavorite: Boolean)

    @Query("SELECT favorite FROM movies WHERE id=:id")
    fun checkFavoriteStatus(id: Int): Boolean

    @Query("SELECT EXISTS (SELECT id FROM movies WHERE id=:id)")
    fun movieExistsById(id: Int): Boolean

    @Query("UPDATE movies SET popular=1 WHERE id=:id")
    fun setPopular(id: Int)

    @Query("UPDATE movies SET mostRated=1 WHERE id=:id")
    fun setMostRated(id: Int)

    @Query("UPDATE movies SET recommended=1 WHERE id=:id")
    fun setRecommended(id: Int)
}