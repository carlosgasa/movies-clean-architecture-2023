package com.gscarlos.moviescleanarchitecture.ui.compose.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val BackgroundLight = Color(0xFFFFFFFF)
val BorderCardLight = Color(0x80DFDFDF)
val GrayDark = Color(0xFF363636)
val GreenLight = Color(0xFF19bc66)

val DarkColorPalette = darkColors(
    primary = Color.White,
    primaryVariant = GrayDark,
    secondary = Color.Blue
)

val LightColorPalette = lightColors(
    primary = GrayDark,
    primaryVariant = GrayDark,
    secondary = GreenLight,
    background = BackgroundLight,

    /* Other default colors to override
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)