package com.gscarlos.moviescleanarchitecture.di

import com.gscarlos.moviescleanarchitecture.data.remote.firebase.FirebaseProvider
import com.gscarlos.moviescleanarchitecture.data.remote.firebase.impl.FirebaseProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun provideFirestore(): FirebaseProvider {
        return FirebaseProviderImpl()
    }
}