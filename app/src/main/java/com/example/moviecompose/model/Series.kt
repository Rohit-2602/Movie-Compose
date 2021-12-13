package com.example.moviecompose.model

data class Series(
    val backdrop_path: String?,
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Long,
    val name: String,
    val original_language: String,
    val original_name: String,
    val overview: String,
    var poster_path: String?,
    val vote_average: Float,
    val vote_count: Int,
    val popularity: Float,
)