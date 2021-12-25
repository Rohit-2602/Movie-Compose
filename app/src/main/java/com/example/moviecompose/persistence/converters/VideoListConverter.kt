package com.example.moviecompose.persistence.converters

import androidx.room.TypeConverter
import com.example.moviecompose.model.network.Video
import com.google.gson.Gson

class VideoListConverter {

    @TypeConverter
    fun listToJson(value: List<Video>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Video>::class.java).toList()

}