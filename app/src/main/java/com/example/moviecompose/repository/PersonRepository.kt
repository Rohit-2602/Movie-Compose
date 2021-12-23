package com.example.moviecompose.repository

import com.example.moviecompose.model.network.PersonDetail
import com.example.moviecompose.model.network.PersonMovieResponse
import com.example.moviecompose.model.network.PersonResponse
import com.example.moviecompose.model.network.PersonSeriesResponse
import com.example.moviecompose.network.Resource
import com.example.moviecompose.network.service.PersonService
import retrofit2.HttpException
import javax.inject.Inject

class PersonRepository @Inject constructor(
    private val PersonService: PersonService
) {

    suspend fun getPersonList(page: Int = 1): Resource<PersonResponse> {
        val result = try {
            PersonService.getPersonList(page = page)
        } catch (exception: HttpException) {
            return Resource.Error(exception.message!!.toString())
        } catch (exception: Exception) {
            return Resource.Error(exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun searchPerson(page: Int = 1, query: String): Resource<PersonResponse> {
        val result = try {
            PersonService.searchPerson(query = query, page = page)
        } catch (exception: HttpException) {
            return Resource.Error(exception.message!!.toString())
        } catch (exception: Exception) {
            return Resource.Error(exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getPersonDetail(personId: Int): Resource<PersonDetail> {
        val result = try {
            PersonService.getPersonDetails(personId = personId)
        } catch (exception: HttpException) {
            return Resource.Error(exception.message!!.toString())
        } catch (exception: Exception) {
            return Resource.Error(exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getPersonMovieCredit(personId: Int): Resource<PersonMovieResponse> {
        val result = try {
            PersonService.getPersonMovieCredit(personId = personId)
        } catch (exception: HttpException) {
            return Resource.Error(exception.message!!.toString())
        } catch (exception: Exception) {
            return Resource.Error(exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun getPersonSeriesCredit(personId: Int): Resource<PersonSeriesResponse> {
        val result = try {
            PersonService.getPersonSeriesCredit(personId = personId)
        } catch (exception: HttpException) {
            return Resource.Error(exception.message!!.toString())
        } catch (exception: Exception) {
            return Resource.Error(exception.message!!.toString())
        }
        return Resource.Success(result)
    }

}