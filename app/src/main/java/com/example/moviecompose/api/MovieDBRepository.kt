package com.example.moviecompose.api

import com.example.moviecompose.model.MovieDetailResponse
import com.example.moviecompose.model.MovieResponse
import com.example.moviecompose.model.SeriesDetailResponse
import com.example.moviecompose.model.SeriesResponse
import javax.inject.Inject

class MovieDBRepository @Inject constructor(private val movieDBApi: MovieDBApi) {

    suspend fun getTrendingMovies(page: Int = 1): Resource<MovieResponse> {
        val result = try {
            movieDBApi.getTrendingMovies(page = page)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getMoviesBasedOnGenre(genre: Int, page: Int = 1): Resource<MovieResponse> {
        val result = try {
            movieDBApi.getMoviesBasedOnGenre(genre = genre, page = page)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetailResponse> {
        val result = try {
            movieDBApi.getMovieDetails(movieId = movieId)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getTrendingSeries(page: Int = 1): Resource<SeriesResponse> {
        val result = try {
            movieDBApi.getTrendingSeries(page = page)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getSeriesBasedOnGenre(genre: Int, page: Int = 1): Resource<SeriesResponse> {
        val result = try {
            movieDBApi.getSeriesBasedOnGenre(genre = genre, page = page)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getSeriesDetails(seriesId: Int): Resource<SeriesDetailResponse> {
        val result = try {
            movieDBApi.getSeriesDetails(seriesId = seriesId)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

}