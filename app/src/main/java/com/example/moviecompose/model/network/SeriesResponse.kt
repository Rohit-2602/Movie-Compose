package com.example.moviecompose.model.network

import com.example.moviecompose.model.entities.Series
import com.example.moviecompose.model.entities.SeriesPoster

data class SeriesResponse(
    val page: Int,
    val results: List<Series>,
    val total_pages: Int,
    val total_results: Int
)

data class PosterSeriesResponse(
    val page: Int,
    val results: List<SeriesPoster>,
    val total_pages: Int,
    val total_results: Int
)