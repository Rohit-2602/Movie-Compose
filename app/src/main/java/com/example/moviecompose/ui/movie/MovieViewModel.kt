package com.example.moviecompose.ui.movie

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.model.entities.Movie
import com.example.moviecompose.model.network.Cast
import com.example.moviecompose.model.network.MovieDetailResponse
import com.example.moviecompose.model.network.Video
import com.example.moviecompose.network.Resource
import com.example.moviecompose.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    val endReached = mutableStateOf(false)
    private var currentPage = 1

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

    fun getFavouriteMovie(movieId: Int): MutableState<Movie?> {
        val movie = mutableStateOf<Movie?>(null)
        viewModelScope.launch {
            movie.value = movieRepository.getFavouriteMovie(movieId)
        }
        return movie
    }

    fun isFavourite(movieId: Int): MutableState<Boolean> {
        val fav = mutableStateOf(false)
        viewModelScope.launch {
            fav.value = movieRepository.getFavouriteMovieList().any { movie ->  movie.id == movieId }
        }
        return fav
    }

    fun getTrendingMovies(): MutableState<List<Movie>> {
        val trendingMovies = mutableStateOf<List<Movie>>(listOf())
        isLoading.value = true
        viewModelScope.launch {
            when (val result = movieRepository.getTrendingMovies()) {
                is Resource.Success -> {
                    val movies = result.data!!.results
                        .filter { movie ->
                            movie.poster_path != null
                        }
                    loadError.value = ""
                    isLoading.value = false
                    trendingMovies.value += movies
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
        return trendingMovies
    }

    fun getMoviesByGenre(genre: Int): MutableState<List<Movie>> {
        val genreMovie = mutableStateOf<List<Movie>>(listOf())
        viewModelScope.launch {
            when (val result = movieRepository.getMoviesBasedOnGenre(genre = genre)) {
                is Resource.Success -> {
                    val movies = result.data!!.results
                        .filter { movie ->
                            movie.poster_path != null
                        }
                    loadError.value = ""
                    genreMovie.value += movies
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                }
                is Resource.Loading -> {}
            }
        }
        return genreMovie
    }

    private val movieList = mutableStateOf<List<Movie>>(listOf())
    fun getPaginatedMovies(genreId: Int): MutableState<List<Movie>> {
        viewModelScope.launch {
            isLoading.value = true
            val result = if (genreId == 0) {
                movieRepository.getTrendingMovies(page = currentPage)
            } else {
                movieRepository.getMoviesBasedOnGenre(genreId, page = currentPage)
            }
            when (result) {
                is Resource.Success -> {
                    endReached.value = currentPage >= result.data!!.total_pages
                    val movies = result.data.results
                        .filter { movie ->
                            movie.poster_path != null
                        }
                    currentPage++
                    loadError.value = ""
                    isLoading.value = false
                    movieList.value += movies
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
        return movieList
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