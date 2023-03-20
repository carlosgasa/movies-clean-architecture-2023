package com.gscarlos.moviescleanarchitecture.data.datasource.impl

import com.gscarlos.moviescleanarchitecture.data.datasource.LocationRepository
import com.gscarlos.moviescleanarchitecture.data.remote.firebase.FirebaseProvider
import com.gscarlos.moviescleanarchitecture.data.remote.firebase.model.LocationDto
import com.gscarlos.moviescleanarchitecture.data.toShow
import com.gscarlos.moviescleanarchitecture.domain.model.LocationToShow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val firebaseProvider: FirebaseProvider
) : LocationRepository {
    override suspend fun getLocations() = flow {
        val list  = mutableListOf<LocationToShow>()
        firebaseProvider.getGPSHistory().await().let {
            for (document in it.documents) {
                try {
                    document.toObject(LocationDto::class.java)?.let { doc ->
                        list.add(doc.toShow())
                    }
                } catch (e : Exception) {
                    e.printStackTrace()
                }
            }
        }
        emit(list)
    }
}