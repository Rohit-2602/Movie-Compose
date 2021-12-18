package com.example.moviecompose.repository

import com.example.moviecompose.model.entities.Series
import com.example.moviecompose.model.network.*
import com.example.moviecompose.network.Resource
import com.example.moviecompose.network.service.SeriesService
import com.example.moviecompose.persistance.SeriesDao
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val seriesService: SeriesService,
    private val seriesDao: SeriesDao
) {

    suspend fun addSeriesToFavourite(series: Series) = seriesDao.insertSeries(series = series)
    suspend fun removeSeriesFromFavourite(series: Series) = seriesDao.removeSeries(series = series)
    suspend fun getFavouriteSeries(seriesId: Int) = seriesDao.getSeries(id = seriesId)
    suspend fun getFavouriteSeriesList() = seriesDao.getSeriesList()

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

    suspend fun getSeriesCast(seriesId: Int): Resource<CastResponse> {
        val result = try {
            seriesService.getSeriesCast(seriesId = seriesId)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getSeriesRecommendation(seriesId: Int): Resource<SeriesResponse> {
        val result = try {
            seriesService.getSeriesRecommendations(seriesId = seriesId)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getSeasonDetail(seriesId: Int, seasonNumber: Int): Resource<SeasonResponse> {
        val result = try {
            seriesService.getSeasonDetails(seriesId = seriesId, seasonNumber = seasonNumber)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getSeriesVideos(seriesId: Int): Resource<VideoResponse> {
        val result = try {
            seriesService.getSeriesVideos(seriesId = seriesId)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message!!.toString())
        }
        return Resource.Success(result)
    }

}