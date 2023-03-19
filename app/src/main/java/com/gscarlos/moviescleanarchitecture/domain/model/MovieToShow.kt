package com.gscarlos.moviescleanarchitecture.domain.model

data class MovieToShow(
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val releaseDate: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val popularity: Double = 0.0,
    val voteAverage: Double = 0.0,
    val isFavorite: Boolean = false
)