package com.gscarlos.moviescleanarchitecture.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gscarlos.moviescleanarchitecture.data.datasource.MovieRepository
import com.gscarlos.moviescleanarchitecture.data.datasource.impl.DataResult
import com.gscarlos.moviescleanarchitecture.domain.model.MovieToShow
import com.gscarlos.moviescleanarchitecture.domain.usecases.UpdateFavoriteUseCase
import com.gscarlos.moviescleanarchitecture.domain.usecases.UpdateRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
    private val updateRateUseCase: UpdateRateUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MoviesViewState>(MoviesViewState.Start)
    val uiState = _uiState.asStateFlow()

    private val _popularMoviesState = MutableStateFlow<List<MovieToShow>>(emptyList())
    val popularMoviesState = _popularMoviesState.asStateFlow()

    private val _mostRatedMoviesState = MutableStateFlow<List<MovieToShow>>(emptyList())
    val mostRatedMoviesState = _mostRatedMoviesState.asStateFlow()

    private val _recommendedMoviesState = MutableStateFlow<List<MovieToShow>>(emptyList())
    val recommendedMoviesState = _recommendedMoviesState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getPopularMovies().collectLatest {
                _popularMoviesState.value = it
            }
        }

        viewModelScope.launch {
            repository.getMostRatedMovies().collectLatest { list ->
                _mostRatedMoviesState.value = list.sortedByDescending { it.voteAverage }
            }
        }

        viewModelScope.launch {
            repository.getRecommendedMovies().collectLatest {
                _recommendedMoviesState.value = it
            }
        }
    }

    fun loadMovies() {
        viewModelScope.launch {
            repository.loadMovies().collectLatest {
                when (it) {
                    DataResult.Error -> _uiState.value = MoviesViewState.Error
                    DataResult.Loading -> _uiState.value = MoviesViewState.Loading
                    DataResult.Success -> _uiState.value = MoviesViewState.Success
                }
            }
        }
    }

    fun onFavorite(movie: MovieToShow) {
        updateFavoriteUseCase.updateFavorite(movie)
    }

    fun setRate(id: Int, newRate: Float) {
        updateRateUseCase.setRate(id, newRate)
    }

}