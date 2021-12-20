package com.example.moviecompose.model.network

import com.example.moviecompose.model.entities.Multi

data class SearchResponse (
    val page: Int,
    val results: List<Multi>,
    val total_pages: Int,
    val total_results: Int
)