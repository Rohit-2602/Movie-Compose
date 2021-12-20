package com.example.moviecompose.ui.series

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.model.entities.Series
import com.example.moviecompose.model.network.Cast
import com.example.moviecompose.model.network.SeasonResponse
import com.example.moviecompose.model.network.SeriesDetailResponse
import com.example.moviecompose.model.network.Video
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

    fun getFavouriteSeries(seriesId: Int): MutableState<Series?> {
        val series = mutableStateOf<Series?>(null)
        viewModelScope.launch {
            series.value = seriesRepository.getFavouriteSeries(seriesId = seriesId)
        }
        return series
    }

    fun isFavourite(seriesId: Int): MutableState<Boolean> {
        val fav = mutableStateOf(false)
        viewModelScope.launch {
            fav.value = seriesRepository.getFavouriteSeriesList().any { series ->  series.id == seriesId }
        }
        return fav
    }

    fun getTrendingSeries(): MutableState<List<Series>> {
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

    private val seriesList = mutableStateOf<List<Series>>(listOf())
    fun getPaginatedSeries(genreId: Int): MutableState<List<Series>> {
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
                        .filter { series ->
                            series.poster_path != null
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

    fun getSeriesRecommendation(seriesId: Int): MutableState<List<Series>> {
        isLoading.value = true
        val recommendation = mutableStateOf<List<Series>>(listOf())
        viewModelScope.launch {
            when(val result = seriesRepository.getSeriesRecommendation(seriesId = seriesId)) {
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

    fun getSeasonDetails(seriesId: Int, seasonNumber: Int): MutableState<SeasonResponse?> {
        isLoading.value = true
        val seasonDetail = mutableStateOf<SeasonResponse?>(null)
        viewModelScope.launch {
            when(val result = seriesRepository.getSeasonDetail(seriesId = seriesId, seasonNumber = seasonNumber)) {
                is Resource.Success -> {
                    isLoading.value = false
                    loadError.value = ""
                    seasonDetail.value = result.data!!
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
        return seasonDetail
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