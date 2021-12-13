package com.example.moviecompose.util

object Constant {

    private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
    private const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"

    /**
     * Action - 28
     * Adventure - 12
     * Animation - 16
     * Comedy - 35
     * Family - 10751
     * Fantasy - 14
     * Mystery - 9648
     * Sci Fic - 878
     * Thriller - 53
     */
    val GENRES_LIST = hashMapOf<String, Int>(
        "Action" to 28,
        "Adventure" to 12,
        "Animation" to 16,
        "Comedy" to 35,
        "Family" to 10751,
        "Fantasy" to 14,
        "Mystery" to 9648,
        "Sci-Fic" to 878,
        "Thriller" to 53,
    )

    fun getPosterPath(posterPath: String?): String = BASE_POSTER_PATH + posterPath
    fun getBackDropPath(backdropPath: String?): String = BASE_BACKDROP_PATH + backdropPath

}