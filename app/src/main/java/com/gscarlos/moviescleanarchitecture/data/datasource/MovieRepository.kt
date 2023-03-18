package com.gscarlos.moviescleanarchitecture.data.datasource

import com.gscarlos.moviescleanarchitecture.BuildConfig
import com.gscarlos.moviescleanarchitecture.data.local.database.AppDatabase
import com.gscarlos.moviescleanarchitecture.data.remote.MoviesApiService
import com.gscarlos.moviescleanarchitecture.data.toRoomMovie
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

sealed class DataResult {
    object Loading : DataResult()
    object Error : DataResult()
    object Success : DataResult()
}

class MovieRepository @Inject constructor(
    private val db: AppDatabase,
    private val apiService: MoviesApiService
) {
    suspend fun getMovies() = flow {
        emit(DataResult.Loading)
        if (db.movieDao().getMoviesCount() == 0) {
            val result = apiService.getMovies(BuildConfig.MOVIE_API_KEY)
            if (result.isSuccessful && result.body() != null) {
                result.body()?.results?.map { it.toRoomMovie() }?.let {
                    db.movieDao().insertBulk(it)
                    emit(DataResult.Success)
                }
            } else {
                emit(DataResult.Error)
            }
        } else {
            emit(DataResult.Success)
        }
    }
}