package com.example.moviecompose.ui.movie

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.model.entities.MoviePoster
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

    private val trendingMoviesList = mutableStateOf<List<MoviePoster>>(listOf())
    fun getTrendingMovies(): MutableState<List<MoviePoster>> {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = movieRepository.getTrendingMoviesPoster()) {
                is Resource.Success -> {
                    val movies = result.data!!.results
                        .filter { movie ->
                            movie.poster_path != null
                        }
                    loadError.value = ""
                    isLoading.value = false
                    trendingMoviesList.value += movies
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
        return trendingMoviesList
    }

    fun getMoviesByGenre(genre: Int): MutableState<List<MoviePoster>> {
        val genreMovie = mutableStateOf<List<MoviePoster>>(listOf())
        viewModelScope.launch {
            when (val result = movieRepository.getMoviesPosterBasedOnGenre(genre = genre)) {
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

}