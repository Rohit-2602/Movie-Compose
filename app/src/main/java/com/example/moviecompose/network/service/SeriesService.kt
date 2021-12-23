package com.example.moviecompose.network.service

import com.example.moviecompose.model.network.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesService {

    @GET("3/trending/tv/week")
    suspend fun getTrendingSeries(@Query("page") page: Int = 1): SeriesResponse

    @GET("3/trending/tv/week")
    suspend fun getTrendingSeriesPoster(@Query("page") page: Int = 1): PosterSeriesResponse

    @GET("3/discover/tv")
    suspend fun getSeriesBasedOnGenre(
        @Query("page") page: Int = 1,
        @Query("with_genres") genre: Int
    ): SeriesResponse

    @GET("3/discover/tv")
    suspend fun getSeriesPosterBasedOnGenre(
        @Query("page") page: Int = 1,
        @Query("with_genres") genre: Int
    ): PosterSeriesResponse

    @GET("3/tv/{seriesId}")
    suspend fun getSeriesDetails(@Path("seriesId") seriesId: Int): SeriesDetailResponse

    @GET("3/tv/{seriesId}/credits")
    suspend fun getSeriesCast(@Path("seriesId") seriesId: Int): CastResponse

    @GET("3/tv/{seriesId}/recommendations")
    suspend fun getSeriesRecommendations(@Path("seriesId") seriesId: Int): SeriesResponse

    @GET("3/tv/{seriesId}/recommendations")
    suspend fun getSeriesPosterRecommendations(@Path("seriesId") seriesId: Int): PosterSeriesResponse

    @GET("3/tv/{seriesId}/videos")
    suspend fun getSeriesVideos(@Path("seriesId") seriesId: Int): VideoResponse

    @GET("3/tv/{seriesId}/season/{season_number}")
    suspend fun getSeasonDetails(
        @Path("seriesId") seriesId: Int,
        @Path("season_number") seasonNumber: Int
    ): SeasonResponse

}