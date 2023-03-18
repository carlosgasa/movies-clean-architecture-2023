package com.gscarlos.moviescleanarchitecture.ui.movies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gscarlos.moviescleanarchitecture.data.datasource.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MoviesViewState>(MoviesViewState.Start)
    val uiState = _uiState.asStateFlow()

    fun getMovies() {
        viewModelScope.launch {
            repository.getMovies().collectLatest {
                Log.i("MoviesViewModel", "$it")
            }
        }
    }

}