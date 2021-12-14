package com.example.moviecompose.ui.homeScreen.movieScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.moviecompose.ui.homeScreen.HomeScreenViewModel
import com.example.moviecompose.ui.homeScreen.MovieImage
import com.example.moviecompose.ui.homeScreen.MoviesSeriesHeader
import com.example.moviecompose.ui.homeScreen.RetrySection
import com.example.moviecompose.util.Constant

@Composable
fun MovieScreen(
    mainNavController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

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
        if (errorMessage.isEmpty()) {
            LazyColumn(modifier = Modifier.padding(bottom = 10.dp)) {
                item {
                    TrendingMovieList(
                        mainNavController = mainNavController
                    )
                }
                item {
                    for ((key, value) in Constant.MOVIES_GENRE_LIST) {
                        GenreMovieList(
                            mainNavController = mainNavController,
                            title = key,
                            genreId = value
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
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val trendingMovies by rememberSaveable {
        viewModel.getTrendingMovies()
    }
    val isLoading by remember {
        viewModel.isLoading
    }
    if (isLoading) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        MovieList(
            mainNavController = mainNavController,
            title = "Trending",
            movieList = trendingMovies
        )
    }
}

@Composable
fun GenreMovieList(
    mainNavController: NavController,
    title: String,
    genreId: Int,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val genreMovies by rememberSaveable {
        viewModel.getMoviesByGenre(genre = genreId)
    }
    val isLoading by remember {
        viewModel.isLoading
    }
    if (isLoading) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        MovieList(
            mainNavController = mainNavController,
            title = title,
            movieList = genreMovies
        )
    }
}

@Composable
fun MovieList(
    mainNavController: NavController,
    title: String,
    movieList: List<Movie>,
) {
    Column {
        MoviesSeriesHeader(title = title)
        LazyRow(modifier = Modifier.padding(end = 10.dp, top = 10.dp)) {
            val itemCount = if (movieList.size > 10) {
                10
            } else {
                movieList.size
            }
            items(itemCount) {
                MovieImage(
                    mainNavController = mainNavController,
                    movie = movieList[it]
                )
            }
        }
    }
}