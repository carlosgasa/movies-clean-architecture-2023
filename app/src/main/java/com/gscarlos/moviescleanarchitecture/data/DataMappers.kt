package com.gscarlos.moviescleanarchitecture.data

import com.gscarlos.moviescleanarchitecture.data.local.model.MovieEntity
import com.gscarlos.moviescleanarchitecture.data.local.model.MovieType
import com.gscarlos.moviescleanarchitecture.data.remote.network.responses.MovieDto
import com.gscarlos.moviescleanarchitecture.domain.model.MovieToShow


fun MovieDto.toRoomMovie(type:Int) = MovieEntity(
    id,
    0,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath ?: posterPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    popular = type == MovieType.Popular().value,
    mostRated = type == MovieType.MostRated().value,
    recommended = type == MovieType.Recommended().value,
    favorite = false
)


fun MovieEntity.toShow(): MovieToShow = MovieToShow(
    id,
    myRate,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    favorite
)

