package com.example.moviecompose.repository

import com.example.moviecompose.model.MovieDetailResponse
import com.example.moviecompose.model.MovieResponse
import com.example.moviecompose.model.VideoResponse
import com.example.moviecompose.network.Resource
import com.example.moviecompose.network.service.MovieService
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieService: MovieService) {

    suspend fun getTrendingMovies(page: Int = 1): Resource<MovieResponse> {
        val result = try {
            movieService.getTrendingMovies(page = page)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getMoviesBasedOnGenre(genre: Int, page: Int = 1): Resource<MovieResponse> {
        val result = try {
            movieService.getMoviesBasedOnGenre(genre = genre, page = page)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetailResponse> {
        val result = try {
            movieService.getMovieDetails(movieId = movieId)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getMovieRecommendation(movieId: Int): Resource<MovieResponse> {
        val result = try {
            movieService.getMovieRecommendations(movieId = movieId)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getMovieVideos(movieId: Int): Resource<VideoResponse> {
        val result = try {
            movieService.getMovieVideos(movieId = movieId)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

}