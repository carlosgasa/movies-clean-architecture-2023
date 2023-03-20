package com.gscarlos.moviescleanarchitecture.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gscarlos.moviescleanarchitecture.data.datasource.MovieRepository
import com.gscarlos.moviescleanarchitecture.data.datasource.UserRepository
import com.gscarlos.moviescleanarchitecture.domain.model.MovieToShow
import com.gscarlos.moviescleanarchitecture.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel  @Inject constructor(
    private val movieRepository: MovieRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val _favoritesMoviesState = MutableStateFlow<List<MovieToShow>>(emptyList())
    val favoritesMoviesState = _favoritesMoviesState.asStateFlow()

    private val _ratedMoviesState = MutableStateFlow<List<MovieToShow>>(emptyList())
    val ratedMoviesState = _ratedMoviesState.asStateFlow()

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    init {
        viewModelScope.launch {
            movieRepository.getFavoriteMovies().collectLatest {
                _favoritesMoviesState.value = it
            }
        }

        viewModelScope.launch {
            movieRepository.getMyRateMovies().collectLatest { list ->
                _ratedMoviesState.value = list.sortedByDescending { it.voteAverage }
            }
        }

        viewModelScope.launch {
            movieRepository.getMyRateMovies().collectLatest { list ->
                _ratedMoviesState.value = list.sortedByDescending { it.voteAverage }
            }
        }

        viewModelScope.launch {
            userRepository.getLocalUser().collectLatest { user ->
                _user.value = user
            }
        }
    }

    fun updateFavorite(movie: MovieToShow) {
        movieRepository.updateFavorite(movie)
    }

    fun setRate(id: Int, newRate: Float) {
        movieRepository.setRate(id, newRate)
    }
}