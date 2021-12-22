package com.example.moviecompose.model.network

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
    val known_for: List<KnownFor>,
    val known_for_department: String?
)

// To get the list of movies from Person
data class KnownFor(
    val id: Int,
    val media_type: String,
    var poster_path: String
)

data class PersonDetail(
    val id: Long,
    val name: String?,
    val biography: String,
    val birthday: String,
    val deathday: String?,
    val profile_path: String?,
    val known_for_department: String?,
    val place_of_birth: String
)
