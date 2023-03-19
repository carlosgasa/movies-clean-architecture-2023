package com.gscarlos.moviescleanarchitecture.ui.movies


sealed class MoviesViewState {
    object Start: MoviesViewState()
    object Loading : MoviesViewState()
    object Error : MoviesViewState()
    object Success : MoviesViewState()
}