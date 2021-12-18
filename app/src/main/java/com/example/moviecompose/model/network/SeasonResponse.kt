package com.example.moviecompose.model.network

data class SeasonResponse(
    val id: Int,
    val _id: String,
    val name: String,
    val overview: String,
    val poster_path: String,
    val season_number: Int,
    val episodes: List<Episode>
)

data class Episode(
    val air_date: String,
    val episode_number: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val season_number: Int,
    val still_path: String,
    val vote_average: Double,
    val vote_count: Int
)
