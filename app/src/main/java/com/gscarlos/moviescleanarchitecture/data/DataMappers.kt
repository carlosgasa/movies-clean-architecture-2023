package com.gscarlos.moviescleanarchitecture.data

import com.gscarlos.moviescleanarchitecture.data.local.model.MovieEntity
import com.gscarlos.moviescleanarchitecture.data.local.model.MovieType
import com.gscarlos.moviescleanarchitecture.data.remote.firebase.model.FileDto
import com.gscarlos.moviescleanarchitecture.data.remote.firebase.model.LocationDto
import com.gscarlos.moviescleanarchitecture.data.remote.network.responses.MovieDto
import com.gscarlos.moviescleanarchitecture.domain.model.FileToShow
import com.gscarlos.moviescleanarchitecture.domain.model.LocationToShow
import com.gscarlos.moviescleanarchitecture.domain.model.MovieToShow
import java.util.Date


fun MovieDto.toRoomMovie(type:Int) = MovieEntity(
    id,
    0,
    title,
    overview,
    releaseDate ?: "Sin fecha aun",
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

fun LocationDto.toShow(): LocationToShow = LocationToShow(
    latitud ?: 0.0,
    longitud ?: 0.0,
    fecha?.toDate() ?: Date()
)

fun FileDto.toShow(): FileToShow = FileToShow(
    url ?: "",
    fecha?.toDate() ?: Date()
)

