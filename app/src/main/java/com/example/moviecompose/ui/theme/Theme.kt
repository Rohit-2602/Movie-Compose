package com.example.moviecompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = blue,
    primaryVariant = secondaryBackground,
    background = secondaryBackground,
    surface = background,
    onSurface = background
)

private val LightColorPalette = lightColors(
    primary = Color.Blue,
    background = background,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black
)

@Composable
fun ComposeMovieTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    // Default Dark Theme
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
//        LightColorPalette
        DarkColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}