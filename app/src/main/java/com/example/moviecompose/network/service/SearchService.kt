package com.example.moviecompose.network.service

import com.example.moviecompose.model.network.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("3/search/multi")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int
    ): SearchResponse

}