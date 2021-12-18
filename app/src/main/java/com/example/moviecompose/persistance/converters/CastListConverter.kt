package com.example.moviecompose.persistance.converters

import androidx.room.TypeConverter
import com.example.moviecompose.model.network.Cast
import com.google.gson.Gson

class CastListConverter {

    @TypeConverter
    fun listToJson(value: List<Cast>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Cast>::class.java).toList()

}