package com.gscarlos.moviescleanarchitecture.ui.compose.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.gscarlos.moviescleanarchitecture.common.Constants
import com.gscarlos.moviescleanarchitecture.domain.model.MovieToShow
import com.gscarlos.moviescleanarchitecture.ui.compose.theme.MoviesTheme
import com.gscarlos.moviescleanarchitecture.ui.compose.theme.Typography
import com.gscarlos.moviescleanarchitecture.ui.movies.adapter.MoviesItemEvent

@Composable
fun MovieItem(movie: MovieToShow, onEvent: (MoviesItemEvent) -> Unit) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onEvent(MoviesItemEvent.OnItem(movie))
                    },
                painter = rememberImagePainter(
                    data = "${Constants.BASE_MOVIE_IMAGE_URL}w185/${movie.posterPath}",
                    builder = {
                        transformations(
                            RoundedCornersTransformation(
                                4f,
                                4f,
                                4f,
                                4f
                            )
                        )
                    }
                ),
                contentScale = ContentScale.Crop,
                contentDescription = "Movie item showed"
            )

            IconButton(
                onClick = {
                    onEvent(MoviesItemEvent.OnFavorite(movie))
                }
            ) {
                Card(backgroundColor = Color.White.copy(alpha = 0.8f), elevation = 0.dp) {
                    Icon(
                        modifier = Modifier.padding(2.dp),
                        imageVector = if (movie.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        tint = Color.Gray,
                        contentDescription = "Favorite button"
                    )
                }
            }
        }
        Text(
            text = movie.title,
            textAlign = TextAlign.Center,
            style = Typography.h6,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colors.surface,
            modifier = Modifier.size(width = 150.dp, height = 18.dp)
        )
    }
}

@Preview
@Composable
fun MovieItemPreview() {
    MoviesTheme() {
        MovieItem(MovieToShow()) {}
    }
}