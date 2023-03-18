package com.gscarlos.moviescleanarchitecture.ui.movies

import com.gscarlos.moviescleanarchitecture.data.local.model.Movie

sealed class MoviesViewState {
    object Start: MoviesViewState()
    object Loading : MoviesViewState()
    data class Content(val movies: List<Movie>) : MoviesViewState()
    object Error : MoviesViewState()
}