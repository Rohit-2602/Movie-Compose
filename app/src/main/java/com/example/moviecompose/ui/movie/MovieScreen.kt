package com.example.moviecompose.ui.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviecompose.model.Movie
import com.example.moviecompose.ui.MoviesSeriesHeader
import com.example.moviecompose.ui.RetrySection
import com.example.moviecompose.util.Constant

@Composable
fun MovieScreen(
    mainNavController: NavController,
    viewModel: MovieViewModel = hiltViewModel()
) {

    val trendingMovies by rememberSaveable {
        viewModel.getTrendingMovies()
    }

    val isLoading by remember {
        viewModel.isLoading
    }

    val errorMessage by remember {
        viewModel.loadError
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        if (errorMessage.isNotEmpty()) {
            RetrySection(error = errorMessage) {
                viewModel.getTrendingMovies()
            }
        }
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        if (errorMessage.isEmpty() && !isLoading) {
            LazyColumn(modifier = Modifier.padding(bottom = 10.dp)) {
                item {
                    TrendingMovieList(
                        mainNavController = mainNavController,
                        trendingMovies = trendingMovies
                    )
                }
                item {
                    for (genre in Constant.MOVIES_GENRE_LIST) {
                        GenreMovieList(
                            mainNavController = mainNavController,
                            title = genre.name, genreId = genre.id
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TrendingMovieList(
    mainNavController: NavController,
    trendingMovies: List<Movie>
) {
    Column {
        MoviesSeriesHeader(
            mainNavController = mainNavController,
            title = "Trending", isMovie = true, genreId = 0
        )
        MovieRowList(
            mainNavController = mainNavController,
            movieList = trendingMovies
        )
    }
}

@Composable
fun GenreMovieList(
    mainNavController: NavController,
    title: String,
    genreId: Int,
    viewModel: MovieViewModel = hiltViewModel(),
) {
    val genreMovies by rememberSaveable {
        viewModel.getMoviesByGenre(genre = genreId)
    }
    Column {
        MoviesSeriesHeader(
            mainNavController = mainNavController,
            title = title, isMovie = true, genreId = genreId
        )
        MovieRowList(
            mainNavController = mainNavController,
            movieList = genreMovies
        )
    }
}