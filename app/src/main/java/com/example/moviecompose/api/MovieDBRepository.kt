package com.example.moviecompose.api

import com.example.moviecompose.model.MovieResponse
import com.example.moviecompose.model.SeriesResponse
import com.example.moviecompose.util.Constant
import javax.inject.Inject

class MovieDBRepository @Inject constructor(private val movieDBApi: MovieDBApi) {

    fun getPosterPath(posterPath: String?): String = Constant.getPosterPath(posterPath)
    fun getBackDropPath(backdropPath: String?): String = Constant.getBackDropPath(backdropPath)

    suspend fun getTrendingMovies(): Resource<MovieResponse> {
        val result = try {
            movieDBApi.getTrendingMovies()
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getMoviesBasedOnGenre(genre: Int): Resource<MovieResponse> {
        val result = try {
            movieDBApi.getMoviesBasedOnGenre(genre = genre)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getTrendingSeries(): Resource<SeriesResponse> {
        val result = try {
            movieDBApi.getTrendingSeries()
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getSeriesBasedOnGenre(genre: Int): Resource<SeriesResponse> {
        val result = try {
            movieDBApi.getSeriesBasedOnGenre(genre = genre)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

}