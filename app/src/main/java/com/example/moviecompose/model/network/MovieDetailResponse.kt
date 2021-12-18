package com.example.moviecompose.model.network

data class MovieDetailResponse(
    val id: Int,
    val title: String,
    val original_title: String,
    val overview: String,
    val backdrop_path: String,
    val poster_path: String,
    val genres: List<Genre>,
    val runtime: Int,
    val vote_average: String,
)