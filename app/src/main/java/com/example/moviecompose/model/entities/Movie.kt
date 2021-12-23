package com.example.moviecompose.model.entities

import androidx.room.Entity
import com.example.moviecompose.model.network.Cast
import com.example.moviecompose.model.network.Video

@Entity(primaryKeys = [("id")])
data class Movie(
    val id: Int,
    val title: String,
    val original_title: String,
    val overview: String,
    val backdrop_path: String?,
    val poster_path: String?,
    val genre_ids: List<Int>,
    val vote_average: String,
    var trailers: List<Video> = emptyList(),
    val cast: List<Cast> = emptyList(),
    val recommendation: List<MoviePoster> = emptyList()
)

// Use in screen where we just need Poster Path and id
data class MoviePoster(
    val id: Int,
    val poster_path: String?
)
