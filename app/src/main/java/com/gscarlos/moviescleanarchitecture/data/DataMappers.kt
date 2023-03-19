package com.gscarlos.moviescleanarchitecture.data

import com.gscarlos.moviescleanarchitecture.data.local.model.MovieEntity
import com.gscarlos.moviescleanarchitecture.data.remote.responses.MovieDto
import com.gscarlos.moviescleanarchitecture.domain.model.MovieToShow


fun MovieDto.toRoomMovie(type:Int) = MovieEntity(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath ?: posterPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    type,
    false
)


fun MovieEntity.toShow(): MovieToShow = MovieToShow(
    id,
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

