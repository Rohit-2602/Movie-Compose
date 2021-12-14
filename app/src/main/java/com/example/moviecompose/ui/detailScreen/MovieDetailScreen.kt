package com.example.moviecompose.ui.detailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviecompose.ui.homeScreen.RetrySection

@Composable
fun MovieDetailScreen(
    navController: NavController,
    movieId: Int,
    viewModel: DetailScreenViewModel = hiltViewModel()
) {

    val movieDetails by remember {
        viewModel.getMovieDetails(movieId = movieId)
    }

    val isLoading by remember {
        viewModel.isLoading
    }

    val errorMessage by remember {
        viewModel.errorMessage
    }

    Surface(
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        if (isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
        if (errorMessage.isNotEmpty()) {
            RetrySection(error = errorMessage) {
                viewModel.getMovieDetails(movieId = movieId)
            }
        }
        if (!isLoading && errorMessage.isEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    val movie = movieDetails!!
                    BackDropPoster(backDropPoster = viewModel.getBackDropPoster(movie.backdrop_path))
                    Row {
                        GenreRating(genre = movie.genres[0].name, voteAverage = movie.vote_average)
                        MovieRunTime(runTime = movie.runtime)
                    }
                    Title(movieTitle = movie.original_title, movieDescription = movie.overview)
                }
            }
        }
    }

}