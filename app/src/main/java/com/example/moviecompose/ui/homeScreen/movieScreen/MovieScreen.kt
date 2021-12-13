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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviecompose.model.Movie
import com.example.moviecompose.ui.homeScreen.HomeScreenViewModel
import com.example.moviecompose.ui.homeScreen.MovieSeriesImage
import com.example.moviecompose.ui.homeScreen.MoviesSeriesHeader
import com.example.moviecompose.ui.homeScreen.RetrySection
import com.example.moviecompose.util.Constant

@Composable
fun MovieScreen(
    navController: NavController,
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
                        navController = navController
                    )
                }
                item {
                    for ((key, value) in Constant.MOVIES_GENRE_LIST) {
                        GenreMovieList(
                            navController = navController,
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
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val trendingMovies by remember {
        viewModel.trendingMovies
    }
    val isLoading by remember {
        viewModel.isLoading
    }
    if (isLoading) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else {
        MovieList(
            navController = navController,
            title = "Trending",
            movieList = trendingMovies
        )
    }
}

@Composable
fun GenreMovieList(
    navController: NavController,
    title: String,
    genreId: Int,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val animationMovies by remember {
        viewModel.getMoviesByGenre(genre = genreId)
    }
    val isLoading by remember {
        viewModel.isLoading
    }
    if (isLoading) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else {
        MovieList(
            navController = navController,
            title = title,
            movieList = animationMovies
        )
    }
}

@Composable
fun MovieList(
    navController: NavController,
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
                MovieSeriesImage(navController = navController, posterPath = movieList[it].poster_path)
            }
        }
    }
}