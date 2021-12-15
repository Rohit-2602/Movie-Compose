package com.example.moviecompose.api

import com.example.moviecompose.BuildConfig
import com.example.moviecompose.model.MovieDetailResponse
import com.example.moviecompose.model.MovieResponse
import com.example.moviecompose.model.SeriesDetailResponse
import com.example.moviecompose.model.SeriesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBApi {

    companion object {
        const val API_KEY = BuildConfig.API_KEY
        const val BASE_URL = "https://api.themoviedb.org/"
        const val PAGING_SIZE = 20
        private const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
        private const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"

        private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
        private const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"
        fun getPosterPath(posterPath: String?): String = BASE_POSTER_PATH + posterPath
        fun getBackDropPath(backdropPath: String?): String = BASE_BACKDROP_PATH + backdropPath

    }

    @GET("3/trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("3/discover/movie")
    suspend fun getMoviesBasedOnGenre(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1,
        @Query("with_genres") genre: Int
    ): MovieResponse

    @GET("3/movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): MovieDetailResponse

    @GET("3/trending/tv/week")
    suspend fun getTrendingSeries(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1
    ): SeriesResponse

    @GET("3/discover/tv")
    suspend fun getSeriesBasedOnGenre(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1,
        @Query("with_genres") genre: Int
    ): SeriesResponse

    @GET("3/tv/{seriesId}")
    suspend fun getSeriesDetails(
        @Path("seriesId") seriesId: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): SeriesDetailResponse

}