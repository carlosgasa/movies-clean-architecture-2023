package com.gscarlos.moviescleanarchitecture.domain.usecases.impl

import com.gscarlos.moviescleanarchitecture.data.local.database.AppDatabase
import com.gscarlos.moviescleanarchitecture.domain.model.MovieToShow
import com.gscarlos.moviescleanarchitecture.domain.usecases.UpdateFavoriteUseCase
import javax.inject.Inject

class UpdateFavoriteUseCaseImpl @Inject constructor(
    private val db: AppDatabase,
) : UpdateFavoriteUseCase {

    override fun updateFavorite(movie: MovieToShow) {
        db.movieDao().checkFavoriteStatus(movie.id).let {
            db.movieDao().updateFavorite(movie.id, !it)
        }
    }

}