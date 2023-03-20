package com.gscarlos.moviescleanarchitecture.ui.movies.adapter

import com.gscarlos.moviescleanarchitecture.domain.model.MovieToShow


sealed class MoviesItemEvent {
    class OnItem(val movie: MovieToShow) : MoviesItemEvent()
    class OnFavorite(val movie: MovieToShow) : MoviesItemEvent()
}
