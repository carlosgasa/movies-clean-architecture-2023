package com.gscarlos.moviescleanarchitecture.ui.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun MoviesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    MaterialTheme(
        typography = Typography,
        colors = if(darkTheme) DarkColorPalette else LightColorPalette,
        shapes = Shapes,
        content = content
    )
}