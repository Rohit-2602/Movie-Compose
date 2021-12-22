package com.example.moviecompose.ui.person

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.model.network.Person
import com.example.moviecompose.network.Resource
import com.example.moviecompose.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val personRepository: PersonRepository
) : ViewModel() {

    var currentPage = 1
    var loadError = mutableStateOf("")
    val searchQuery = mutableStateOf("")

    var isLoading = mutableStateOf(false)

    private var personList = mutableStateOf<List<Person>>(listOf())
    fun getPerson(): MutableState<List<Person>> {
        viewModelScope.launch {
            when (val result = personRepository.getPersonList(page = 1)) {
                is Resource.Success -> {
                    val response = result.data!!.results
//                        .filter {
//                            it.known_for_department == "Acting"
//                        }
                    loadError.value = ""
                    personList.value = response
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                }
                is Resource.Loading -> {
                    isLoading.value = true
                }
            }
        }
        return personList
    }

    fun loadMorePerson(): MutableState<List<Person>> {
        viewModelScope.launch {
            when (val result = personRepository.getPersonList(page = currentPage + 1)) {
                is Resource.Success -> {
                    val response = result.data!!.results
//                        .filter {
//                            it.known_for_department == "Acting"
//                        }
                    loadError.value = ""
                    currentPage++
                    personList.value += response
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                }
                is Resource.Loading -> {
                    isLoading.value = true
                }
            }
        }
        return personList
    }

    fun searchPerson(query: String): MutableState<List<Person>> {
        if (query != "") {
            viewModelScope.launch {
                when (val result = personRepository.searchPerson(query = query, page = 1)) {
                    is Resource.Success -> {
                        val response = result.data!!.results.filter {
                            it.known_for_department == "Acting"
                        }
                        loadError.value = ""
                        personList.value = response
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
            personList = getPerson()
        }
        return personList
    }

    fun loadMoreSearchResult(query: String) {
        viewModelScope.launch {
            when (val result =
                personRepository.searchPerson(query = query, page = currentPage + 1)) {
                is Resource.Success -> {
                    val searchResult = result.data!!.results.filter {
                        it.known_for_department == "Acting"
                    }
                    currentPage++
                    loadError.value = ""
                    personList.value += searchResult
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                }
                is Resource.Loading -> {}
            }
        }
    }

}