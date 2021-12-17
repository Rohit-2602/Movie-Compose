package com.example.moviecompose.model

data class SeasonResponse(
    val _id: String,
    val air_date: String,
    val episodes: List<Episode>,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String,
    val season_number: Int
)

data class Episode(
    val air_date: String,
    val episode_number: Int,
    val guest_stars: List<GuestStar>,
    val id: Int,
    val name: String,
    val overview: String,
    val season_number: Int,
    val still_path: String,
    val vote_average: Double,
    val vote_count: Int
)

data class GuestStar(
    val character: String,
    val id: Int,
    val name: String,
    val original_name: String,
    val profile_path: String
)