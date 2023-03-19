package com.gscarlos.moviescleanarchitecture.ui.movies.adapter

import com.gscarlos.moviescleanarchitecture.domain.model.MovieToShow


sealed class MoviesAdapterEvent {
    class OnItem(val movie: MovieToShow) : MoviesAdapterEvent()
    class OnFavorite(val movie: MovieToShow) : MoviesAdapterEvent()
}
