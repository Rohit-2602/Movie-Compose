package com.example.moviecompose.repository

import com.example.moviecompose.model.network.PeopleResponse
import com.example.moviecompose.network.Resource
import com.example.moviecompose.network.service.PeopleService
import retrofit2.HttpException
import javax.inject.Inject

class PeopleRepository @Inject constructor(
    private val peopleService: PeopleService
) {

    suspend fun getPersonList(page: Int = 1): Resource<PeopleResponse> {
        val result = try {
            peopleService.getPersonList(page = page)
        } catch (exception: HttpException) {
            return Resource.Error(exception.message!!.toString())
        } catch (exception: Exception) {
            return Resource.Error(exception.message!!.toString())
        }
        return Resource.Success(result)
    }

    suspend fun searchPerson(page: Int = 1, query: String): Resource<PeopleResponse> {
        val result = try {
            peopleService.searchPerson(query = query, page = page)
        } catch (exception: HttpException) {
            return Resource.Error(exception.message!!.toString())
        } catch (exception: Exception) {
            return Resource.Error(exception.message!!.toString())
        }
        return Resource.Success(result)
    }

}