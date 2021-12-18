package com.example.moviecompose.model.network

import com.example.moviecompose.model.entities.Series

data class SeriesResponse(
    val page: Int,
    val results: List<Series>,
    val total_pages: Int,
    val total_results: Int
)