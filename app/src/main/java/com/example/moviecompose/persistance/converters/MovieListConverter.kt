package com.example.moviecompose.persistance.converters

import androidx.room.TypeConverter
import com.example.moviecompose.model.entities.Movie
import com.google.gson.Gson

class MovieListConverter {

    @TypeConverter
    fun listToJson(value: List<Movie>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Movie>::class.java).toList()

}