package com.example.moviecompose.ui.person.personDetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.model.entities.MoviePoster
import com.example.moviecompose.model.entities.SeriesPoster
import com.example.moviecompose.model.network.PersonDetail
import com.example.moviecompose.network.Resource
import com.example.moviecompose.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val personRepository: PersonRepository
) : ViewModel() {

    val isLoading = mutableStateOf(false)
    val loadingError = mutableStateOf("")

    private val personDetail = mutableStateOf<PersonDetail?>(null)
    fun getPersonDetails(personId: Int): MutableState<PersonDetail?> {
        viewModelScope.launch {
            isLoading.value = true
            val result = personRepository.getPersonDetail(personId = personId)
            when (result) {
                is Resource.Success -> {
                    personDetail.value = result.data!!
                    isLoading.value = false
                    loadingError.value = ""
                }
                is Resource.Loading -> {
                    isLoading.value = true
                }
                is Resource.Error -> {
                    isLoading.value = false
                    loadingError.value = result.message!!
                }
            }
        }
        return personDetail
    }

    private val personMovieCredit = mutableStateOf<List<MoviePoster>>(listOf())
    fun getPersonMovieCredit(personId: Int): MutableState<List<MoviePoster>> {
        viewModelScope.launch {
            val result = personRepository.getPersonMovieCredit(personId = personId)
            when (result) {
                is Resource.Success -> {
                    personMovieCredit.value = result.data!!.cast.filter {
                        it.poster_path != null
                    }
                    loadingError.value = ""
                }
                is Resource.Loading -> {}
                is Resource.Error -> {
                    loadingError.value = result.message!!
                }
            }
        }
        return personMovieCredit
    }

    private val personSeriesCredit = mutableStateOf<List<SeriesPoster>>(listOf())
    fun getPersonSeriesCredit(personId: Int): MutableState<List<SeriesPoster>> {
        viewModelScope.launch {
            val result = personRepository.getPersonSeriesCredit(personId = personId)
            when (result) {
                is Resource.Success -> {
                    personSeriesCredit.value = result.data!!.cast.filter {
                        it.poster_path != null
                    }
                    loadingError.value = ""
                }
                is Resource.Loading -> {}
                is Resource.Error -> {
                    loadingError.value = result.message!!
                }
            }
        }
        return personSeriesCredit
    }

}