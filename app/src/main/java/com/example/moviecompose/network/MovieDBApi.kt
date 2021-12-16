package com.example.moviecompose.network

import com.example.moviecompose.BuildConfig

object MovieDBApi {

    const val API_KEY = BuildConfig.API_KEY
    const val BASE_URL = "https://api.themoviedb.org/"
//    const val PAGING_SIZE = 20

    private const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
    private const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"
    fun getYoutubeThumbnail(key: String): String = "$YOUTUBE_THUMBNAIL_URL$key/default.jpg"
    fun getYoutubeUrl(key: String): String = "$YOUTUBE_VIDEO_URL$key"

    private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
    private const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"
    fun getPosterPath(posterPath: String?): String = BASE_POSTER_PATH + posterPath
    fun getBackDropPath(backdropPath: String?): String = BASE_BACKDROP_PATH + backdropPath
}