package com.example.moviecompose.persistence.converters

import androidx.room.TypeConverter
import com.example.moviecompose.model.entities.Series
import com.google.gson.Gson

class SeriesListConverter {

    @TypeConverter
    fun listToJson(value: List<Series>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Series>::class.java).toList()

}