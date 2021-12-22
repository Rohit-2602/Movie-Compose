package com.example.moviecompose.ui.movie.genreMovieList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.model.entities.Movie
import com.example.moviecompose.network.Resource
import com.example.moviecompose.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreMovieDetailViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    val endReached = mutableStateOf(false)
    private var currentPage = 1

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

}