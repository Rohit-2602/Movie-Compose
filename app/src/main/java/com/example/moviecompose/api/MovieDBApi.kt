package com.example.moviecompose.api

import com.example.moviecompose.BuildConfig
import com.example.moviecompose.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDBApi {

    companion object {
        const val API_KEY = BuildConfig.API_KEY
        const val BASE_URL = "https://api.themoviedb.org/"
        private const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
        private const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"
        const val PAGING_SIZE = 20
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

}