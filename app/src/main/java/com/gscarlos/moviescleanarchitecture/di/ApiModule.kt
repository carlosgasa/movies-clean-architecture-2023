package com.gscarlos.moviescleanarchitecture.di

import com.gscarlos.moviescleanarchitecture.common.Constants.BASE_MOVIE_URL
import com.gscarlos.moviescleanarchitecture.data.remote.network.MoviesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideApiService(): MoviesApiService {
        return Retrofit
            .Builder()
            .baseUrl(BASE_MOVIE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApiService::class.java)
    }
}