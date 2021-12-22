package com.example.moviecompose.ui.series.genreSeriesList

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
class GenreSeriesDetailViewModel @Inject constructor(private val seriesRepository: SeriesRepository) :
    ViewModel() {

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    val endReached = mutableStateOf(false)
    private var currentPage = 1

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

}