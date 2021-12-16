package com.example.moviecompose.network.service

import com.example.moviecompose.model.SeriesDetailResponse
import com.example.moviecompose.model.SeriesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesService {

    @GET("3/trending/tv/week")
    suspend fun getTrendingSeries(@Query("page") page: Int = 1): SeriesResponse

    @GET("3/discover/tv")
    suspend fun getSeriesBasedOnGenre(
        @Query("page") page: Int = 1,
        @Query("with_genres") genre: Int
    ): SeriesResponse

    @GET("3/tv/{seriesId}")
    suspend fun getSeriesDetails(@Path("seriesId") seriesId: Int): SeriesDetailResponse

}