package com.example.moviecompose.di

import android.content.Context
import androidx.room.Room
import com.example.moviecompose.persistence.AppDatabase
import com.example.moviecompose.persistence.MovieDao
import com.example.moviecompose.persistence.SeriesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideRoomDataBase(@ApplicationContext context: Context): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, "MovieCompose.db")
        .allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao = appDatabase.getMovieDao()

    @Provides
    @Singleton
    fun provideTvDao(appDatabase: AppDatabase): SeriesDao = appDatabase.getSeriesDao()

}