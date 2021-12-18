package com.example.moviecompose.network.service

import com.example.moviecompose.model.network.CastResponse
import com.example.moviecompose.model.network.MovieDetailResponse
import com.example.moviecompose.model.network.MovieResponse
import com.example.moviecompose.model.network.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("3/trending/movie/week")
    suspend fun getTrendingMovies(@Query("page") page: Int = 1): MovieResponse

    @GET("3/discover/movie")
    suspend fun getMoviesBasedOnGenre(
        @Query("page") page: Int = 1,
        @Query("with_genres") genre: Int
    ): MovieResponse

    @GET("3/movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: Int): MovieDetailResponse

    @GET("3/movie/{movieId}/credits")
    suspend fun getMovieCast(@Path("movieId") movieId: Int): CastResponse

    @GET("3/movie/{movieId}/recommendations")
    suspend fun getMovieRecommendations(@Path("movieId") movieId: Int): MovieResponse

    @GET("3/movie/{movieId}/videos")
    suspend fun getMovieVideos(@Path("movieId") movieId: Int): VideoResponse

}