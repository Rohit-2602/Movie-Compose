package com.example.moviecompose.ui.series.seriesDetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.model.entities.Series
import com.example.moviecompose.model.entities.SeriesPoster
import com.example.moviecompose.model.network.Cast
import com.example.moviecompose.model.network.SeriesDetailResponse
import com.example.moviecompose.model.network.Video
import com.example.moviecompose.network.Resource
import com.example.moviecompose.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesDetailViewModel @Inject constructor(private val seriesRepository: SeriesRepository) :
    ViewModel() {

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    fun addSeriesToFavourite(series: Series) {
        viewModelScope.launch {
            seriesRepository.addSeriesToFavourite(series = series)
        }
    }

    fun removeSeriesFromFavourite(series: Series) {
        viewModelScope.launch {
            seriesRepository.removeSeriesFromFavourite(series = series)
        }
    }

    fun isFavourite(seriesId: Int): MutableState<Boolean> {
        val fav = mutableStateOf(false)
        viewModelScope.launch {
            fav.value = seriesRepository.getFavouriteSeriesList().any { series ->  series.id == seriesId }
        }
        return fav
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

    fun getSeriesCast(seriesId: Int): MutableState<List<Cast>> {
        isLoading.value = true
        val seriesCast = mutableStateOf<List<Cast>>(listOf())
        viewModelScope.launch {
            when(val result = seriesRepository.getSeriesCast(seriesId = seriesId)) {
                is Resource.Success -> {
                    isLoading.value = false
                    loadError.value = ""
                    seriesCast.value = result.data!!.cast.filter { cast ->
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
        return seriesCast
    }

    fun getSeriesRecommendation(seriesId: Int): MutableState<List<SeriesPoster>> {
        isLoading.value = true
        val recommendation = mutableStateOf<List<SeriesPoster>>(listOf())
        viewModelScope.launch {
            when(val result = seriesRepository.getSeriesPosterRecommendation(seriesId = seriesId)) {
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

    fun getSeriesTrailers(seriesId: Int): MutableState<List<Video>> {
        isLoading.value = true
        val trailers = mutableStateOf<List<Video>>(listOf())
        viewModelScope.launch {
            when(val result = seriesRepository.getSeriesVideos(seriesId = seriesId)) {
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