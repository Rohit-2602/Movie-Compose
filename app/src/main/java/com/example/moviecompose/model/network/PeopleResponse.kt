package com.example.moviecompose.model.network

import com.example.moviecompose.model.entities.MoviePoster
import com.example.moviecompose.model.entities.SeriesPoster

data class PersonResponse(
    val page: Int,
    val results: List<Person>,
    val total_pages: Int,
    val total_results: Int
)

data class Person(
    val id: Long,
    val name: String?,
    val profile_path: String?,
    val known_for_department: String?
)

data class PersonMovieResponse(
    val cast: List<MoviePoster>
)

data class PersonSeriesResponse(
    val cast: List<SeriesPoster>
)

data class PersonDetail(
    val id: Long,
    val name: String,
    val biography: String,
    val birthday: String,
    val deathday: String?,
    val profile_path: String?,
    val known_for_department: String?,
    val place_of_birth: String
)
