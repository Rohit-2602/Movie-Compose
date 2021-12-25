package com.example.moviecompose.persistence.converters

import androidx.room.TypeConverter
import com.google.gson.Gson

class IntegerListComparator {

    @TypeConverter
    fun listToJson(value: List<Int>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Int>::class.java).toList()

}