package com.example.moviecompose.ui.genreDetailScreen

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
class GenreDetailViewModel @Inject constructor(private val movieDBRepository: MovieDBRepository) :
    ViewModel() {

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var currentPage = 1
    val endReached = mutableStateOf(false)

    private val movieList = mutableStateOf<List<Movie>>(listOf())
    fun getMovies(genreId: Int): MutableState<List<Movie>> {
        viewModelScope.launch {
            isLoading.value = true
            val result = if (genreId == 0) {
                movieDBRepository.getTrendingMovies(page = currentPage)
            } else {
                movieDBRepository.getMoviesBasedOnGenre(genreId, page = currentPage)
            }
            when(result) {
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

    private val seriesList = mutableStateOf<List<Series>>(listOf())
    fun getSeries(genreId: Int): MutableState<List<Series>> {
        viewModelScope.launch {
            isLoading.value = true
            val result = if (genreId == 0) {
                movieDBRepository.getTrendingSeries(page = currentPage)
            } else {
                movieDBRepository.getSeriesBasedOnGenre(genreId, page = currentPage)
            }
            when(result) {
                is Resource.Success -> {
                    endReached.value = currentPage >= result.data!!.total_pages
                    val series = result.data.results
                        .filter { movie ->
                            movie.poster_path != null
                        }
                    currentPage++
                    loadError.value = ""
                    isLoading.value = false
                    seriesList.value += series
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
        return seriesList
    }
}