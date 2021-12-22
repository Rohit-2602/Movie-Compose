package com.example.moviecompose.ui.navigation

sealed class NavScreen(val route: String) {
    object MovieDetail: NavScreen("MovieDetails") {
        const val routeWithArgument: String = "MovieDetails/{movieId}"
        const val argument0: String = "movieId"
    }
    object GenreMovieDetail: NavScreen("GenreMovieDetails") {
        const val routeWithArgument: String = "GenreMovieDetails/{genreId}/{genreTitle}"
        const val argument0: String = "genreId"
        const val argument1: String = "genreTitle"
    }
    object SeriesDetail: NavScreen("SeriesDetails") {
        const val routeWithArgument: String = "SeriesDetails/{seriesId}"
        const val argument0: String = "seriesId"
    }
    object GenreSeriesDetail: NavScreen("GenreSeriesDetails") {
        const val routeWithArgument: String = "GenreSeriesDetails/{genreId}/{genreTitle}"
        const val argument0: String = "genreId"
        const val argument1: String = "genreTitle"
    }
    object SeasonDetail: NavScreen("SeasonDetails") {
        const val routeWithArgument: String = "SeasonDetails/{seriesId}/{seasonNumber}/{seriesName}"
        const val argument0: String = "seriesId"
        const val argument1: String = "seasonNumber"
        const val argument2: String = "seriesName"
    }
    object PersonDetail: NavScreen("PersonDetails") {
        const val routeWithArgument: String = "PersonDetails/{personId}/{personKnownFor}"
        const val argument0: String = "personId"
        const val argument1: String = "personKnownFor"
    }
}