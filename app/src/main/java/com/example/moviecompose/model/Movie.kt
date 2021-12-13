package com.example.moviecompose.model

data class Movie(
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val id: Long,
    val original_language: String,
    val original_title: String,
    val overview: String,
    var poster_path: String?,
    val release_date: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Int,
    val popularity: Float,
)