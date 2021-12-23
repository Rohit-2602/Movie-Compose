package com.example.moviecompose.persistance.converters

import androidx.room.TypeConverter
import com.example.moviecompose.model.entities.SeriesPoster
import com.google.gson.Gson

class PosterSeriesListConverter {

    @TypeConverter
    fun listToJson(value: List<SeriesPoster>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<SeriesPoster>::class.java).toList()

}