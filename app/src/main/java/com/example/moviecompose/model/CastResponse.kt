package com.example.moviecompose.model

data class CastResponse(
    val id: Int,
    val cast: List<Cast>
)

data class Cast(
    val id: Int,
    val name: String,
    val original_name: String,
    val profile_path : String?,
    val character: String
)
