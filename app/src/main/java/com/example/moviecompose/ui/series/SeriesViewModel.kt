package com.example.moviecompose.ui.series

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.model.Series
import com.example.moviecompose.model.SeriesDetailResponse
import com.example.moviecompose.network.Resource
import com.example.moviecompose.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(private val seriesRepository: SeriesRepository) :
    ViewModel() {

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    val endReached = mutableStateOf(false)
    private var currentPage = 1

    fun getTrendingSeries(): MutableState<List<Series>> {
        isLoading.value = true
        val trendingSeries = mutableStateOf<List<Series>>(listOf())
        viewModelScope.launch {
            when (val result = seriesRepository.getTrendingSeries()) {
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
            when (val result = seriesRepository.getSeriesBasedOnGenre(genre = genre)) {
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

    fun getSeries(genreId: Int): MutableState<List<Series>> {
        val seriesList = mutableStateOf<List<Series>>(listOf())
        viewModelScope.launch {
            isLoading.value = true
            val result = if (genreId == 0) {
                seriesRepository.getTrendingSeries(page = currentPage)
            } else {
                seriesRepository.getSeriesBasedOnGenre(genreId, page = currentPage)
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

    fun getSeriesDetails(seriesId: Int): MutableState<SeriesDetailResponse?> {
        isLoading.value = true
        val seriesDetail = mutableStateOf<SeriesDetailResponse?>(null)
        viewModelScope.launch {
            when(val result = seriesRepository.getSeriesDetails(seriesId = seriesId)) {
                is Resource.Success -> {
                    isLoading.value = false
                    loadError.value = ""
                    seriesDetail.value = result.data
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
        return seriesDetail
    }

}