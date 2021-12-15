package com.example.moviecompose.ui.homeScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.api.MovieDBRepository
import com.example.moviecompose.api.Resource
import com.example.moviecompose.model.Movie
import com.example.moviecompose.model.Series
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val homeRepository: MovieDBRepository) :
    ViewModel() {

    val selectedTab = mutableStateOf(0)

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    fun getTrendingMovies(): MutableState<List<Movie>> {
        val trendingMovies = mutableStateOf<List<Movie>>(listOf())
        isLoading.value = true
        viewModelScope.launch {
            when (val result = homeRepository.getTrendingMovies()) {
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
            when (val result = homeRepository.getMoviesBasedOnGenre(genre = genre)) {
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

    fun getTrendingSeries(): MutableState<List<Series>> {
        isLoading.value = true
        val trendingSeries = mutableStateOf<List<Series>>(listOf())
        viewModelScope.launch {
            when (val result = homeRepository.getTrendingSeries()) {
                is Resource.Success -> {
                    val series = result.data!!.results
                        .filter { series ->
                            series.poster_path != null
                        }
                    loadError.value = ""
                    isLoading.value = false
                    trendingSeries.value += series
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
        return trendingSeries
    }

    fun getSeriesByGenre(genre: Int): MutableState<List<Series>> {
        val genreSeries = mutableStateOf<List<Series>>(listOf())
        viewModelScope.launch {
            when (val result = homeRepository.getSeriesBasedOnGenre(genre = genre)) {
                is Resource.Success -> {
                    val series = result.data!!.results
                        .filter { series ->
                            series.poster_path != null
                        }
                    loadError.value = ""
                    genreSeries.value += series
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                }
                is Resource.Loading -> {}
            }
        }
        return genreSeries
    }

}