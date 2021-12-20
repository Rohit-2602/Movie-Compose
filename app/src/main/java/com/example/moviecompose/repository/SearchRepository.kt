package com.example.moviecompose.repository

import com.example.moviecompose.model.network.SearchResponse
import com.example.moviecompose.network.Resource
import com.example.moviecompose.network.service.SearchService
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val searchService: SearchService
) {

    suspend fun search(query: String, page: Int = 1): Resource<SearchResponse> {
        val result = try {
            searchService.search(query = query, page = page)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

}