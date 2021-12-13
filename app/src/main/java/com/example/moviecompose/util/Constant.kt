package com.example.moviecompose.util

object Constant {

    private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
    private const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"

    val MOVIES_GENRE_LIST = hashMapOf(
        "Action" to 28,
        "Adventure" to 12,
        "Animation" to 16,
        "Comedy" to 35,
        "Drama" to 18,
        "Family" to 10751,
        "Fantasy" to 14,
        "Mystery" to 9648,
        "Sci-Fic" to 878,
        "Thriller" to 53,
    )

    val SERIES_GENRE_LIST = hashMapOf(
        "Action & Adventure" to 10759,
        "Animation" to 16,
        "Comedy" to 35,
        "Crime" to 80,
        "Documentary" to 99,
        "Drama" to 18,
        "Family" to 10751,
        "Mystery" to 9648,
        "Reality" to 10764,
        "Sci-Fic & Fantasy" to 10765,
    )

    fun getPosterPath(posterPath: String?): String = BASE_POSTER_PATH + posterPath
    fun getBackDropPath(backdropPath: String?): String = BASE_BACKDROP_PATH + backdropPath

}