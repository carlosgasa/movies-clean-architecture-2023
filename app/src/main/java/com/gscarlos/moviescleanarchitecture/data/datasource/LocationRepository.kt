package com.gscarlos.moviescleanarchitecture.data.datasource

import com.gscarlos.moviescleanarchitecture.domain.model.LocationToShow
import kotlinx.coroutines.flow.Flow


interface LocationRepository {
    suspend fun getLocations() : Flow<List<LocationToShow>>
}