package com.example.moviecompose.persistance.converters

import androidx.room.TypeConverter
import com.example.moviecompose.model.entities.MoviePoster
import com.google.gson.Gson

class PosterMovieListConverter {

    @TypeConverter
    fun listToJson(value: List<MoviePoster>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<MoviePoster>::class.java).toList()

}