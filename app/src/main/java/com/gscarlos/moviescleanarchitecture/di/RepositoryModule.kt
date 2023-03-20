package com.gscarlos.moviescleanarchitecture.di

import com.gscarlos.moviescleanarchitecture.data.datasource.MovieRepository
import com.gscarlos.moviescleanarchitecture.data.datasource.UserRepository
import com.gscarlos.moviescleanarchitecture.data.datasource.impl.MovieRepositoryImpl
import com.gscarlos.moviescleanarchitecture.data.datasource.impl.UserRepositoryImpl
import com.gscarlos.moviescleanarchitecture.data.local.database.AppDatabase
import com.gscarlos.moviescleanarchitecture.data.remote.MoviesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesMovieRepository(db: AppDatabase, apiService: MoviesApiService): MovieRepository {
        return MovieRepositoryImpl(db, apiService)
    }

    @Singleton
    @Provides
    fun providesUserRepository(): UserRepository {
        return UserRepositoryImpl()
    }
}