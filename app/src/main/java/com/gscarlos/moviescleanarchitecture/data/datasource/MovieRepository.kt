package com.gscarlos.moviescleanarchitecture.data.datasource

import com.gscarlos.moviescleanarchitecture.data.datasource.impl.DataResult
import com.gscarlos.moviescleanarchitecture.domain.model.MovieToShow
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun loadMovies(): Flow<DataResult>
    suspend fun getMovies() : Flow<List<MovieToShow>>
    fun updateFavorite(movie: MovieToShow)
}