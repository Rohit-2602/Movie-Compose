package com.example.moviecompose.model.entities

data class Multi(
    val id: Int,
    val name: String?,
    val original_name: String?,
    val title: String?,
    val original_title: String?,
    val overview: String,
    val backdrop_path: String?,
    val poster_path: String?,
    val genre_ids: List<Int>?,
    val media_type: String,
    val vote_average: String
)
