package com.example.moviecompose.ui.detailScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.api.MovieDBRepository
import com.example.moviecompose.api.Resource
import com.example.moviecompose.model.MovieDetailResponse
import com.example.moviecompose.model.SeriesDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(private val movieDBRepository: MovieDBRepository) :
    ViewModel() {

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf("")

    fun getMovieDetails(movieId: Int): MutableState<MovieDetailResponse?> {
        isLoading.value = true
        val movieDetail = mutableStateOf<MovieDetailResponse?>(null)
        viewModelScope.launch {
            val result = movieDBRepository.getMovieDetails(movieId = movieId)
            when(result) {
                is Resource.Success -> {
                    isLoading.value = false
                    errorMessage.value = ""
                    movieDetail.value = result.data
                }
                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading -> {
                    isLoading.value = true
                }
            }
        }
        return movieDetail
    }

    fun getSeriesDetails(seriesId: Int): MutableState<SeriesDetailResponse?> {
        isLoading.value = true
        val seriesDetail = mutableStateOf<SeriesDetailResponse?>(null)
        viewModelScope.launch {
            val result = movieDBRepository.getSeriesDetails(seriesId = seriesId)
            when(result) {
                is Resource.Success -> {
                    isLoading.value = false
                    errorMessage.value = ""
                    seriesDetail.value = result.data
                }
                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading -> {
                    isLoading.value = true
                }
            }
        }
        return seriesDetail
    }

}
