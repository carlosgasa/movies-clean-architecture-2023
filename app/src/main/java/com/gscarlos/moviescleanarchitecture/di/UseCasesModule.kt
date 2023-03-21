package com.gscarlos.moviescleanarchitecture.di

import com.gscarlos.moviescleanarchitecture.data.local.database.AppDatabase
import com.gscarlos.moviescleanarchitecture.domain.usecases.UpdateFavoriteUseCase
import com.gscarlos.moviescleanarchitecture.domain.usecases.UpdateRateUseCase
import com.gscarlos.moviescleanarchitecture.domain.usecases.impl.UpdateFavoriteUseCaseImpl
import com.gscarlos.moviescleanarchitecture.domain.usecases.impl.UpdateRateUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Singleton
    @Provides
    fun providesUpdateFavoriteUseCase(db: AppDatabase): UpdateFavoriteUseCase {
        return UpdateFavoriteUseCaseImpl(db)
    }

    @Singleton
    @Provides
    fun providesUpdateRateUseCase(db: AppDatabase): UpdateRateUseCase {
        return UpdateRateUseCaseImpl(db)
    }
}