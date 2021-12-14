package com.example.moviecompose.model

data class MovieResponse(
    val dates: Dates = Dates(),
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)

// Only in Upcoming Movies
data class Dates(
    val maxiMum: String = "",
    val minimum: String = "",
)