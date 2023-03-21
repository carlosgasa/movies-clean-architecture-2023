package com.gscarlos.moviescleanarchitecture.ui.profile

import android.view.LayoutInflater
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.gscarlos.moviescleanarchitecture.R
import com.gscarlos.moviescleanarchitecture.domain.model.MovieToShow
import com.gscarlos.moviescleanarchitecture.domain.model.User
import com.gscarlos.moviescleanarchitecture.ui.compose.composables.MovieItem
import com.gscarlos.moviescleanarchitecture.ui.movies.adapter.MoviesItemEvent
import com.gscarlos.moviescleanarchitecture.ui.movies.rate.DialogRate

class ProfileScreenContentProperties(
    val favorites: List<MovieToShow>,
    val rated: List<MovieToShow>,
    val user: User
) {
    fun isEmpty(): Boolean {
        return favorites.isEmpty() && rated.isEmpty()
    }
}

@Composable
fun ProfileScreen(layoutInflater: LayoutInflater, viewModel: ProfileViewModel = viewModel()) {
    val favoritesState = viewModel.favoritesMoviesState.collectAsState()
    val ratedState = viewModel.ratedMoviesState.collectAsState()
    val userState = viewModel.user.collectAsState()
    val context = LocalContext.current

    ProfileScreenContent(
        properties = ProfileScreenContentProperties(favoritesState.value, ratedState.value, userState.value)
    ) { event ->
        when (event) {
            is MoviesItemEvent.OnFavorite -> viewModel.updateFavorite(event.movie)
            is MoviesItemEvent.OnItem -> {
                DialogRate.Builder(context)
                    .setLayout(layoutInflater)
                    .setMovie(event.movie)
                    .build()
                    .show {
                        viewModel.setRate(event.movie.id, it)
                    }
            }
        }
    }
}

@Composable
fun ProfileScreenContent(
    properties: ProfileScreenContentProperties,
    onEvent: (MoviesItemEvent) -> Unit
) {
    Column(modifier = Modifier
        .padding(8.dp)
        .verticalScroll(rememberScrollState())) {
        ProfileHeader(
            user = properties.user
        )
        if (properties.isEmpty()) {
            Text(
                modifier = Modifier.padding(100.dp),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.txt_movies_empty),
                color = MaterialTheme.colors.surface
            )
        } else {
            ContentProfile(properties = properties, onEvent = onEvent)
        }
    }
}


@Composable
fun ProfileHeader(user: User) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            modifier = Modifier.size(80.dp),
            painter = rememberImagePainter(
                data = user.urlAvatar,
                builder = {
                    transformations(
                        CircleCropTransformation()
                    )
                }
            ),
            contentScale = ContentScale.Crop,
            contentDescription = "Recipe item favorite"
        )
        Text(text = user.birthDate, color = MaterialTheme.colors.surface)
        Text(
            text = user.description,
            color = MaterialTheme.colors.surface,
            textAlign = TextAlign.Justify,
        )
    }
}

@Composable
fun ContentProfile(properties: ProfileScreenContentProperties, onEvent: (MoviesItemEvent) -> Unit) {
    Column() {
        if (properties.rated.isNotEmpty()) {
            Text(text = stringResource(R.string.txt_rated), fontStyle = FontStyle.Italic, fontSize = 18.sp, color = MaterialTheme.colors.surface)
            LazyRow {
                items(properties.rated) { movie ->
                    MovieItem(movie = movie) {
                        onEvent(it)
                    }
                }
            }
        }

        if (properties.favorites.isNotEmpty()) {
            Text(text = stringResource(R.string.txt_favorites), fontStyle = FontStyle.Italic, fontSize = 18.sp, color = MaterialTheme.colors.surface)
            LazyRow {
                items(properties.favorites) { movie ->
                    MovieItem(movie = movie) {
                        onEvent(it)
                    }
                }
            }
        }
    }
}
