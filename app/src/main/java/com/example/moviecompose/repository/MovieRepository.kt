package com.example.moviecompose.repository

import com.example.moviecompose.model.entities.Movie
import com.example.moviecompose.model.network.*
import com.example.moviecompose.network.Resource
import com.example.moviecompose.network.service.MovieService
import com.example.moviecompose.persistence.MovieDao
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieService: MovieService,
    private val movieDao: MovieDao
) {

    suspend fun addMovieToFavourite(movie: Movie) = movieDao.insertMovie(movie = movie)
    suspend fun removeMovieFromFavourite(movie: Movie) = movieDao.removeMovie(movie = movie)
    suspend fun getFavouriteMovie(movieId: Int) = movieDao.getMovie(id = movieId)
    suspend fun getFavouriteMovieList() = movieDao.getMovieList()

    suspend fun getTrendingMovies(page: Int = 1): Resource<MovieResponse> {
        val result = try {
            movieService.getTrendingMovies(page = page)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getTrendingMoviesPoster(page: Int = 1): Resource<PosterMovieResponse> {
        val result = try {
            movieService.getTrendingMoviesPoster(page = page)
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

    suspend fun getMoviesPosterBasedOnGenre(genre: Int, page: Int = 1): Resource<PosterMovieResponse> {
        val result = try {
            movieService.getMoviesPosterBasedOnGenre(genre = genre, page = page)
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

    suspend fun getMovieCast(movieId: Int): Resource<CastResponse> {
        val result = try {
            movieService.getMovieCast(movieId = movieId)
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

    suspend fun getMoviePosterRecommendation(movieId: Int): Resource<PosterMovieResponse> {
        val result = try {
            movieService.getMoviePosterRecommendations(movieId = movieId)
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