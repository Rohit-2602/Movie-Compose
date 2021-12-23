package com.example.moviecompose.network.service

import com.example.moviecompose.model.network.PersonDetail
import com.example.moviecompose.model.network.PersonMovieResponse
import com.example.moviecompose.model.network.PersonResponse
import com.example.moviecompose.model.network.PersonSeriesResponse
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("3/person/{personId}")
    suspend fun getPersonDetails(
        @Path("personId") personId: Int,
    ): PersonDetail

    @GET("3/person/{personId}/movie_credits")
    suspend fun getPersonMovieCredit(
        @Path("personId") personId: Int,
    ): PersonMovieResponse

    @GET("3/person/{personId}/tv_credits")
    suspend fun getPersonSeriesCredit(
        @Path("personId") personId: Int,
    ): PersonSeriesResponse

}