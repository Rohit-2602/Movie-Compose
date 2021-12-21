package com.example.moviecompose.model.network

import com.example.moviecompose.model.entities.Multi

data class PeopleResponse(
    val page: Int,
    val results: List<People>,
    val total_pages: Int,
    val total_results: Int
)

data class People(
    val id: Long,
    val name: String?,
    val profile_path: String?,
    val known_for: List<Multi>,
    val known_for_department: String?
)


