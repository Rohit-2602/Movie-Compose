package com.example.moviecompose.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviecompose.model.entities.Movie
import com.example.moviecompose.model.entities.Series
import com.example.moviecompose.persistence.converters.*

@Database(entities = [Movie::class, Series::class], exportSchema = false, version = 1)
@TypeConverters(
    value = [
        (CastListConverter::class), (MovieListConverter::class), (SeriesListConverter::class),
        (VideoListConverter::class), (IntegerListComparator::class), (PosterMovieListConverter::class),
        (PosterSeriesListConverter::class)
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao
    abstract fun getSeriesDao(): SeriesDao

}