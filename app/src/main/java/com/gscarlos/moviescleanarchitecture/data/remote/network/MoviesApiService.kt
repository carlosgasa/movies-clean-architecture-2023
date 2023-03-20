package com.gscarlos.moviescleanarchitecture.data.remote.network

import com.gscarlos.moviescleanarchitecture.data.remote.network.responses.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface MoviesApiService {

    @GET("discover/movie")
    suspend fun getRecommendedMovies(
        @Query("api_key") apiKey: String
    ): Response<MoviesResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): Response<MoviesResponse>

    @GET("movie/top_rated")
    suspend fun getMostRatedMovies(
        @Query("api_key") apiKey: String
    ): Response<MoviesResponse>
}