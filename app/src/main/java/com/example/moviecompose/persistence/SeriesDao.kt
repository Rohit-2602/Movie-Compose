package com.example.moviecompose.persistence

import androidx.room.*
import com.example.moviecompose.model.entities.Series

@Dao
interface SeriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(series: Series)

    @Delete
    suspend fun removeSeries(series: Series)

    @Query("SELECT * FROM SERIES WHERE id = :id")
    suspend fun getSeries(id: Int): Series

    @Query("SELECT * FROM Series")
    suspend fun getSeriesList(): List<Series>

}