package com.gscarlos.moviescleanarchitecture.domain.usecases

import com.gscarlos.moviescleanarchitecture.domain.model.MovieToShow

interface UpdateFavoriteUseCase {
    fun updateFavorite(movie: MovieToShow)
}