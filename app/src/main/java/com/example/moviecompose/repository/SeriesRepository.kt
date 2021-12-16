package com.example.moviecompose.repository

import com.example.moviecompose.model.SeriesDetailResponse
import com.example.moviecompose.model.SeriesResponse
import com.example.moviecompose.network.Resource
import com.example.moviecompose.network.service.SeriesService
import javax.inject.Inject

class SeriesRepository @Inject constructor(private val seriesService: SeriesService) {

    suspend fun getTrendingSeries(page: Int = 1): Resource<SeriesResponse> {
        val result = try {
            seriesService.getTrendingSeries(page = page)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getSeriesBasedOnGenre(genre: Int, page: Int = 1): Resource<SeriesResponse> {
        val result = try {
            seriesService.getSeriesBasedOnGenre(genre = genre, page = page)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getSeriesDetails(seriesId: Int): Resource<SeriesDetailResponse> {
        val result = try {
            seriesService.getSeriesDetails(seriesId = seriesId)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

}