package com.example.moviecompose.model.entities

import androidx.room.Entity
import com.example.moviecompose.model.network.Cast
import com.example.moviecompose.model.network.Video

@Entity(primaryKeys = [("id")])
data class Series(
    val id: Int,
    val name: String,
    val original_name: String,
    val overview: String,
    val backdrop_path: String?,
    val poster_path: String?,
    val genre_ids: List<Int>,
    val vote_average: String,
    var trailers: List<Video>? = emptyList(),
    val cast: List<Cast> = emptyList(),
    val recommendation: List<SeriesPoster> = emptyList()
)

// Use in screen where we just need Poster Path and id
data class SeriesPoster(
    val id: Int,
    val poster_path: String?
)
