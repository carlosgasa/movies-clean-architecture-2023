package com.gscarlos.moviescleanarchitecture.ui.locations

import com.gscarlos.moviescleanarchitecture.domain.model.LocationToShow


sealed class LocationsViewState {
    object Start: LocationsViewState()
    object Loading : LocationsViewState()
    object Error : LocationsViewState()
    class Success(var locations: List<LocationToShow>) : LocationsViewState()
}