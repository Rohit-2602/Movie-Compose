package com.example.moviecompose.di

import com.example.moviecompose.BuildConfig
import com.example.moviecompose.network.MovieDBApi.BASE_URL
import com.example.moviecompose.network.service.MovieService
import com.example.moviecompose.network.service.PersonService
import com.example.moviecompose.network.service.SearchService
import com.example.moviecompose.network.service.SeriesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()

            chain.proceed(original.newBuilder().url(url).build())
        }
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)

    @Provides
    @Singleton
    fun provideSeriesService(retrofit: Retrofit): SeriesService =
        retrofit.create(SeriesService::class.java)

    @Provides
    @Singleton
    fun provideSearchService(retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)

    @Provides
    @Singleton
    fun providePersonService(retrofit: Retrofit): PersonService =
        retrofit.create(PersonService::class.java)

}