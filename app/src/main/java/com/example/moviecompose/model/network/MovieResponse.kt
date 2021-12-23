package com.example.moviecompose.model.network

import com.example.moviecompose.model.entities.Movie
import com.example.moviecompose.model.entities.MoviePoster

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)

data class PosterMovieResponse(
    val page: Int,
    val results: List<MoviePoster>,
    val total_pages: Int,
    val total_results: Int
)