package com.example.moviecompose.ui.detailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
fun SeriesDetailScreen(
    navController: NavController,
    seriesId: Int,
    viewModel: DetailScreenViewModel = hiltViewModel()
) {

    val seriesDetail by remember {
        viewModel.getSeriesDetails(seriesId = seriesId)
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
                viewModel.getMovieDetails(movieId = seriesId)
            }
        }
        if (!isLoading && errorMessage.isEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    val series = seriesDetail!!
                    BackDropPoster(backDropPoster = viewModel.getBackDropPoster(series.backdrop_path))
                    GenreRating(
                        genre = series.genres[0].name,
                        voteAverage = series.vote_average
                    )
                    Title(
                        movieTitle = series.name,
                        movieDescription = seriesDetail!!.overview
                    )
                }
            }
        }
    }

}