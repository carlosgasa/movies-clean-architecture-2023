package com.gscarlos.moviescleanarchitecture.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gscarlos.moviescleanarchitecture.data.datasource.MovieRepository
import com.gscarlos.moviescleanarchitecture.data.datasource.impl.DataResult
import com.gscarlos.moviescleanarchitecture.domain.model.MovieToShow
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

    private val _moviesState = MutableStateFlow<List<MovieToShow>>(emptyList())
    val moviesState = _moviesState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getMovies().collectLatest {
                _moviesState.value = it
            }
        }
    }

    fun loadMovies() {
        viewModelScope.launch {
            repository.loadMovies().collectLatest {
                when (it) {
                    DataResult.Error -> _uiState.value = MoviesViewState.Error
                    DataResult.Loading -> MoviesViewState.Loading
                    DataResult.Success -> MoviesViewState.Success
                }
            }
        }
    }

}