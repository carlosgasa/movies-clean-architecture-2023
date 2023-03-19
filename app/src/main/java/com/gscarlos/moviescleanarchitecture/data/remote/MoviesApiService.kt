package com.gscarlos.moviescleanarchitecture.data.remote

import com.gscarlos.moviescleanarchitecture.data.remote.responses.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface MoviesApiService {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String
    ): Response<MoviesResponse>
}