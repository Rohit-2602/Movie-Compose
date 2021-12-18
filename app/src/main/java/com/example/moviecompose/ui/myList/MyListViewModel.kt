package com.example.moviecompose.ui.myList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.model.entities.Movie
import com.example.moviecompose.model.entities.Series
import com.example.moviecompose.repository.MovieRepository
import com.example.moviecompose.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository
): ViewModel() {

    fun getFavouriteMovieList(): MutableState<List<Movie>> {
        val movieList = mutableStateOf<List<Movie>>(listOf())
        viewModelScope.launch {
            movieList.value = movieRepository.getFavouriteMovieList()
        }
        return movieList
    }

    fun getFavouriteSeriesList(): MutableState<List<Series>> {
        val seriesList = mutableStateOf<List<Series>>(listOf())
        viewModelScope.launch {
            seriesList.value = seriesRepository.getFavouriteSeriesList()
        }
        return seriesList
    }

}