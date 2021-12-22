package com.example.moviecompose.ui.series.seasonDetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.model.network.SeasonResponse
import com.example.moviecompose.network.Resource
import com.example.moviecompose.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeasonDetailViewModel @Inject constructor(private val seriesRepository: SeriesRepository) :
    ViewModel() {

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

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

}