package com.example.moviecompose.ui.movie.movieDetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.model.entities.Movie
import com.example.moviecompose.model.entities.MoviePoster
import com.example.moviecompose.model.network.Cast
import com.example.moviecompose.model.network.MovieDetailResponse
import com.example.moviecompose.model.network.Video
import com.example.moviecompose.network.Resource
import com.example.moviecompose.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    fun addMovieToFavourite(movie: Movie) {
        viewModelScope.launch {
            movieRepository.addMovieToFavourite(movie)
        }
    }

    fun removeMovieFromFavourite(movie: Movie) {
        viewModelScope.launch {
            movieRepository.removeMovieFromFavourite(movie)
        }
    }

    fun isFavourite(movieId: Int): MutableState<Boolean> {
        val fav = mutableStateOf(false)
        viewModelScope.launch {
            fav.value = movieRepository.getFavouriteMovieList().any { movie ->  movie.id == movieId }
        }
        return fav
    }

    fun getMovieDetails(movieId: Int): MutableState<MovieDetailResponse?> {
        isLoading.value = true
        val movieDetail = mutableStateOf<MovieDetailResponse?>(null)
        viewModelScope.launch {
            when (val result = movieRepository.getMovieDetails(movieId = movieId)) {
                is Resource.Success -> {
                    isLoading.value = false
                    loadError.value = ""
                    movieDetail.value = result.data
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading -> {
                    isLoading.value = true
                }
            }
        }
        return movieDetail
    }

    fun getMovieCast(movieId: Int): MutableState<List<Cast>> {
        isLoading.value = true
        val movieCast = mutableStateOf<List<Cast>>(listOf())
        viewModelScope.launch {
            when (val result = movieRepository.getMovieCast(movieId = movieId)) {
                is Resource.Success -> {
                    isLoading.value = false
                    loadError.value = ""
                    movieCast.value = result.data!!.cast.filter { cast ->
                        cast.profile_path != null
                    }
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading -> {
                    isLoading.value = true
                }
            }
        }
        return movieCast
    }

    fun getMovieRecommendation(movieId: Int): MutableState<List<Movie>> {
        isLoading.value = true
        val recommendation = mutableStateOf<List<Movie>>(listOf())
        viewModelScope.launch {
            when (val result = movieRepository.getMovieRecommendation(movieId = movieId)) {
                is Resource.Success -> {
                    isLoading.value = false
                    loadError.value = ""
                    recommendation.value = result.data!!.results
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading -> {
                    isLoading.value = true
                }
            }
        }
        return recommendation
    }

    fun getMoviePosterRecommendation(movieId: Int): MutableState<List<MoviePoster>> {
        isLoading.value = true
        val recommendation = mutableStateOf<List<MoviePoster>>(listOf())
        viewModelScope.launch {
            when (val result = movieRepository.getMoviePosterRecommendation(movieId = movieId)) {
                is Resource.Success -> {
                    isLoading.value = false
                    loadError.value = ""
                    recommendation.value = result.data!!.results
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading -> {
                    isLoading.value = true
                }
            }
        }
        return recommendation
    }

    fun getMovieTrailers(movieId: Int): MutableState<List<Video>> {
        isLoading.value = true
        val trailers = mutableStateOf<List<Video>>(listOf())
        viewModelScope.launch {
            when (val result = movieRepository.getMovieVideos(movieId = movieId)) {
                is Resource.Success -> {
                    trailers.value = result.data!!.results
                        .filter { video ->
                            video.type == "Trailer"
                        }
                    isLoading.value = false
                    loadError.value = ""
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading -> {
                    isLoading.value = true
                }
            }
        }
        return trailers
    }
}