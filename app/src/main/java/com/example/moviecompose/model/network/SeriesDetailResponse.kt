package com.example.moviecompose.model.network

data class SeriesDetailResponse(
    val id: Int,
    val name: String,
    val original_name: String,
    val overview: String,
    val poster_path: String,
    val backdrop_path: String,
    val genres: List<Genre>,
    val seasons: List<Season>,
    val vote_average: String,
)

data class Season(
    val air_date: String,
    val episode_count: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String,
    val season_number: Int
)