package com.gscarlos.moviescleanarchitecture.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String,
    val backdropPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val popularity: Double,
    val voteAverage: Double,
    val type: Int,
    val favorite: Boolean
)

sealed class MovieType {
    class Popular(val value: Int = 1) : MovieType()
    class Recommended(val value: Int = 2) : MovieType()
    class MostRated(val value: Int = 3) : MovieType()
}