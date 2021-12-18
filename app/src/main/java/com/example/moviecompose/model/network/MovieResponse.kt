package com.example.moviecompose.model.network

import com.example.moviecompose.model.entities.Movie

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)