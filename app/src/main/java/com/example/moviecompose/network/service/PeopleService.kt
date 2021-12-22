package com.example.moviecompose.network.service

import com.example.moviecompose.model.network.PersonResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PersonService {

    @GET("3/person/popular")
    suspend fun getPersonList(
        @Query("page") page: Int
    ): PersonResponse

    @GET("3/search/person")
    suspend fun searchPerson(
        @Query("page") page: Int,
        @Query("query") query: String,
    ): PersonResponse

}