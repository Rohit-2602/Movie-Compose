package com.example.moviecompose.persistence

import androidx.room.*
import com.example.moviecompose.model.entities.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Delete
    suspend fun removeMovie(movie: Movie)

    @Query("SELECT * FROM MOVIE WHERE id = :id")
    suspend fun getMovie(id: Int): Movie

    @Query("SELECT * FROM Movie")
    suspend fun getMovieList(): List<Movie>

}