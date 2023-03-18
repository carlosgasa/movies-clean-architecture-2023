package com.gscarlos.moviescleanarchitecture.data

import com.gscarlos.moviescleanarchitecture.data.remote.responses.Movie as ResponseMovie
import com.gscarlos.moviescleanarchitecture.data.local.model.Movie as RoomMovie

fun ResponseMovie.toRoomMovie(): RoomMovie = RoomMovie(
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
    false
)

