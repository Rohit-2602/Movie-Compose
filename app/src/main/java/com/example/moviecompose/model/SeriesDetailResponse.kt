package com.example.moviecompose.model

data class SeriesDetailResponse(
    val backdrop_path: String,
    val episode_run_time: List<Int>,
    val first_air_date: String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val in_production: Boolean,
    val languages: List<String>,
    val last_air_date: String,
    val last_episode_to_air: LastEpisodeToAir,
    val name: String,
    val next_episode_to_air: Any,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val seasons: List<Season>,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val type: String,
    val vote_average: Double,
    val vote_count: Int
)

data class LastEpisodeToAir(
    val air_date: String,
    val episode_number: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val production_code: String,
    val season_number: Int,
    val still_path: String,
    val vote_average: Double,
    val vote_count: Int
)

data class SpokenLanguage(
    val english_name: String,
    val iso_639_1: String,
    val name: String
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