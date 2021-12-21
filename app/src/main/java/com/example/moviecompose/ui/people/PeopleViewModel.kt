package com.example.moviecompose.ui.people

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.model.network.People
import com.example.moviecompose.network.Resource
import com.example.moviecompose.repository.PeopleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val peopleRepository: PeopleRepository
) : ViewModel() {

    var currentPage = 1
    var loadError = mutableStateOf("")
    val searchQuery = mutableStateOf("")

    var isLoading = mutableStateOf(false)

    private var peopleList = mutableStateOf<List<People>>(listOf())
    fun getPeople(): MutableState<List<People>> {
        viewModelScope.launch {
            when (val result = peopleRepository.getPersonList(page = 1)) {
                is Resource.Success -> {
                    val response = result.data!!.results.filter {
                        it.known_for_department == "Acting"
                    }
                    loadError.value = ""
                    peopleList.value = response
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                }
                is Resource.Loading -> {
                    isLoading.value = true
                }
            }
        }
        return peopleList
    }

    fun loadMorePeople(): MutableState<List<People>> {
        viewModelScope.launch {
            when (val result = peopleRepository.getPersonList(page = currentPage+1)) {
                is Resource.Success -> {
                    val response = result.data!!.results.filter {
                        it.known_for_department == "Acting"
                    }
                    loadError.value = ""
                    currentPage++
                    peopleList.value += response
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                }
                is Resource.Loading -> {
                    isLoading.value = true
                }
            }
        }
        return peopleList
    }

    fun searchPerson(query: String): MutableState<List<People>> {
        if (query != "") {
            viewModelScope.launch {
                when (val result = peopleRepository.searchPerson(query = query, page = 1)) {
                    is Resource.Success -> {
                        val response = result.data!!.results.filter {
                            it.known_for_department == "Acting"
                        }
                        loadError.value = ""
                        peopleList.value = response
                        isLoading.value = false
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
        } else {
            peopleList = getPeople()
        }
        return peopleList
    }

    fun loadMoreSearchResult(query: String) {
        viewModelScope.launch {
            when (val result = peopleRepository.searchPerson(query = query, page = currentPage+1)) {
                is Resource.Success -> {
                    val searchResult = result.data!!.results.filter {
                        it.known_for_department == "Acting"
                    }
                    currentPage++
                    loadError.value = ""
                    peopleList.value += searchResult
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                }
                is Resource.Loading -> {}
            }
        }
    }

}