package com.gscarlos.moviescleanarchitecture.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gscarlos.moviescleanarchitecture.data.datasource.LocationRepository
import com.gscarlos.moviescleanarchitecture.data.remote.firebase.FirebaseProvider
import com.gscarlos.moviescleanarchitecture.data.remote.firebase.model.LocationDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel  @Inject constructor(
    private val locationRepository: LocationRepository
): ViewModel() {

    private val _locationsState = MutableStateFlow<LocationsViewState>(LocationsViewState.Start)
    val locationsState = _locationsState.asStateFlow()

    fun loadLocationsFromFirebase() {
        viewModelScope.launch {
            _locationsState.value = LocationsViewState.Loading
            locationRepository.getLocations().collectLatest { result ->
                _locationsState.value = LocationsViewState.Success(result)
            }
        }
    }
}