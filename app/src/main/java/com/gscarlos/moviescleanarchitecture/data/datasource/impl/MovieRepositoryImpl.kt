package com.gscarlos.moviescleanarchitecture.data.datasource.impl

import com.gscarlos.moviescleanarchitecture.BuildConfig
import com.gscarlos.moviescleanarchitecture.data.datasource.MovieRepository
import com.gscarlos.moviescleanarchitecture.data.local.database.AppDatabase
import com.gscarlos.moviescleanarchitecture.data.remote.MoviesApiService
import com.gscarlos.moviescleanarchitecture.data.toRoomMovie
import com.gscarlos.moviescleanarchitecture.data.toShow
import com.gscarlos.moviescleanarchitecture.domain.model.MovieToShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

sealed class DataResult {
    object Loading : DataResult()
    object Error : DataResult()
    object Success : DataResult()
}

class MovieRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val apiService: MoviesApiService
) : MovieRepository {

    override suspend fun loadMovies(): Flow<DataResult> = flow {
        if (db.movieDao().getMoviesCount() == 0) {
            emit(DataResult.Loading)
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

    override suspend fun getMovies(): Flow<List<MovieToShow>> = channelFlow {
        db.movieDao().getMovies().collectLatest { listEntities ->
            listEntities.map { entity ->
                entity.toShow()
            }.let { listToShow ->
                send(listToShow)
            }
        }
    }
}