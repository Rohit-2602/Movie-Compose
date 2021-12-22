package com.example.moviecompose.ui.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.model.entities.Multi
import com.example.moviecompose.network.Resource
import com.example.moviecompose.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    val searchedList = mutableStateOf<List<Multi>>(listOf())
    val searchQuery = mutableStateOf("")

    val loadError = mutableStateOf("")
    val isLoading = mutableStateOf(false)
    val endReached = mutableStateOf(false)

    // 2 bcoz in search we already load page 1
    private var currentPage = 2

    fun search(query: String) {
        if (query != "") {
            viewModelScope.launch {
                isLoading.value = true
                when (val result = searchRepository.search(query)) {
                    is Resource.Success -> {
                        val searchResult = result.data!!.results.filter { multi ->
                            if(multi.media_type == "tv") {
                                multi.name != null && multi.poster_path != null
                            } else {
                                multi.title != null && multi.poster_path != null
                            }
                        }
                        loadError.value = ""
                        isLoading.value = false
                        searchedList.value = searchResult
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
        }
    }

    fun loadMoreResult(query: String) {
        viewModelScope.launch {
            when (val result = searchRepository.search(query, currentPage)) {
                is Resource.Success -> {
                    endReached.value = currentPage >= result.data!!.total_pages
                    val searchResult = result.data.results.filter { multi ->
                        if(multi.media_type == "tv") {
                            multi.name != null && multi.poster_path != null
                        } else {
                            multi.title != null && multi.poster_path != null
                        }
                    }
                    currentPage++
                    loadError.value = ""
                    searchedList.value += searchResult
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                }
                is Resource.Loading -> {}
            }
        }
    }

}