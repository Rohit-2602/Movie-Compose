package com.example.moviecompose.ui.series

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.model.entities.Series
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

    private val trendingSeriesList = mutableStateOf<List<Series>>(listOf())
    fun getTrendingSeries(): MutableState<List<Series>> {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = seriesRepository.getTrendingSeries()) {
                is Resource.Success -> {
                    val series = result.data!!.results
                        .filter { series ->
                            series.poster_path != null
                        }
                    loadError.value = ""
                    isLoading.value = false
                    trendingSeriesList.value += series
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
        return trendingSeriesList
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

}