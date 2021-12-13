package com.example.moviecompose.ui.homeScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.api.MovieDBRepository
import com.example.moviecompose.api.Resource
import com.example.moviecompose.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val homeRepository: MovieDBRepository) :
    ViewModel() {

    val trendingMovies = mutableStateOf<List<Movie>>(listOf())

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    init {
        getTrendingMovies()
    }

    private fun getPosterPath(posterPath: String?): String = homeRepository.getPosterPath(posterPath)

    fun getTrendingMovies() {
        isLoading.value = true
        viewModelScope.launch {
            val result = homeRepository.getTrendingMovies()
            when(result) {
                is Resource.Success -> {
                    val movies = result.data!!.results.mapIndexed { _, movie ->
                        movie.poster_path = getPosterPath(movie.poster_path)
                        movie
                    }
                    loadError.value = ""
                    isLoading.value = false
                    trendingMovies.value += movies
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                    Log.i("Rohit", "Trending Error")
                }
                is Resource.Loading -> {
                    isLoading.value = true
                }
            }
        }
    }

    fun getMoviesByGenre(genre: Int): MutableState<List<Movie>> {
        isLoading.value = true
        val genreMovie = mutableStateOf<List<Movie>>(listOf())
        viewModelScope.launch {
            val result = homeRepository.getMoviesBasedOnGenre(genre = genre)
            when(result) {
                is Resource.Success -> {
                    val movies = result.data!!.results.mapIndexed { _, movie ->
                        movie.poster_path = getPosterPath(movie.poster_path)
                        movie
                    }
                    loadError.value = ""
                    isLoading.value = false
                    genreMovie.value += movies
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                    Log.i("Rohit", "Genre Error")
                }
                is Resource.Loading -> {
                    isLoading.value = true
                }
            }
        }
        return genreMovie
    }

}