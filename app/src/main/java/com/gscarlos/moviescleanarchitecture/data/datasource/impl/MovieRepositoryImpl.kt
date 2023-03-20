package com.gscarlos.moviescleanarchitecture.data.datasource.impl

import com.gscarlos.moviescleanarchitecture.BuildConfig
import com.gscarlos.moviescleanarchitecture.data.datasource.MovieRepository
import com.gscarlos.moviescleanarchitecture.data.local.database.AppDatabase
import com.gscarlos.moviescleanarchitecture.data.local.model.MovieType
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
            try {
                emit(DataResult.Loading)
                val popular = apiService.getPopularMovies(BuildConfig.MOVIE_API_KEY)
                val mostRated = apiService.getMostRatedMovies(BuildConfig.MOVIE_API_KEY)
                val recommended = apiService.getRecommendedMovies(BuildConfig.MOVIE_API_KEY)

                if (popular.isSuccessful && mostRated.isSuccessful && recommended.isSuccessful) {
                    popular.body()?.results?.map { it.toRoomMovie(MovieType.Popular().value) }
                        ?.let { entityList ->
                            entityList.forEach {
                                if(db.movieDao().movieExistsById(it.id)) {
                                    db.movieDao().setPopular(it.id)
                                } else {
                                    db.movieDao().insert(it)
                                }
                            }
                        }
                    mostRated.body()?.results?.map { it.toRoomMovie(MovieType.MostRated().value) }
                        ?.let { entityList ->
                            entityList.forEach {
                                if(db.movieDao().movieExistsById(it.id)) {
                                    db.movieDao().setMostRated(it.id)
                                } else {
                                    db.movieDao().insert(it)
                                }
                            }
                        }
                    recommended.body()?.results?.map { it.toRoomMovie(MovieType.Recommended().value) }
                        ?.let { entityList ->
                            entityList.forEach {
                                if(db.movieDao().movieExistsById(it.id)) {
                                    db.movieDao().setRecommended(it.id)
                                } else {
                                    db.movieDao().insert(it)
                                }
                            }
                        }
                    emit(DataResult.Success)
                } else {
                    emit(DataResult.Error)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(DataResult.Error)
            }
        } else {
            emit(DataResult.Success)
        }
    }

    override suspend fun getPopularMovies(): Flow<List<MovieToShow>> = channelFlow {
        db.movieDao().getPopularMovies().collectLatest {
                listEntities ->
            listEntities.map { entity ->
                entity.toShow()
            }.let { listToShow ->
                send(listToShow)
            }
        }
    }

    override suspend fun getMostRatedMovies(): Flow<List<MovieToShow>> = channelFlow {
        db.movieDao().getPopularMovies().collectLatest {
                listEntities ->
            listEntities.map { entity ->
                entity.toShow()
            }.let { listToShow ->
                send(listToShow)
            }
        }
    }

    override suspend fun getRecommendedMovies(): Flow<List<MovieToShow>> = channelFlow {
        db.movieDao().getRecommendedMovies().collectLatest {
                listEntities ->
            listEntities.map { entity ->
                entity.toShow()
            }.let { listToShow ->
                send(listToShow)
            }
        }
    }

    override fun updateFavorite(movie: MovieToShow) {
        db.movieDao().checkFavoriteStatus(movie.id).let {
            db.movieDao().updateFavorite(movie.id, !it)
        }
    }

    override fun setRate(id: Int, newRate: Float) {
        db.movieDao().setRate(id, newRate*2)
    }

    override suspend fun getFavoriteMovies(): Flow<List<MovieToShow>> = channelFlow {
        db.movieDao().getFavoriteMovies().collectLatest {
                listEntities ->
            listEntities.map { entity ->
                entity.toShow()
            }.let { listToShow ->
                send(listToShow)
            }
        }
    }

    override suspend fun getMyRateMovies(): Flow<List<MovieToShow>> = channelFlow {
        db.movieDao().getMyRateMovies().collectLatest {
                listEntities ->
            listEntities.map { entity ->
                entity.toShow()
            }.let { listToShow ->
                send(listToShow)
            }
        }
    }
}