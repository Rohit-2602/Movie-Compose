package com.example.moviecompose.ui.series

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviecompose.model.entities.Series
import com.example.moviecompose.ui.MoviesSeriesHeader
import com.example.moviecompose.ui.RetrySection
import com.example.moviecompose.util.Constant

@Composable
fun SeriesScreen(
    navController: NavController,
    viewModel: SeriesViewModel = hiltViewModel()
) {

    val trendingSeries by remember {
        viewModel.getTrendingSeries()
    }

    val isLoading by remember {
        viewModel.isLoading
    }

    val errorMessage by remember {
        viewModel.loadError
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        if (errorMessage.isNotEmpty()) {
            RetrySection(error = errorMessage) {
                viewModel.getTrendingSeries()
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
                    TrendingSeriesList(
                        navController = navController,
                        trendingSeries = trendingSeries
                    )
                }
                item {
                    for (genre in Constant.SERIES_GENRE_LIST) {
                        GenreSeriesList(
                            navController = navController,
                            title = genre.name,
                            genreId = genre.id
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TrendingSeriesList(
    navController: NavController,
    trendingSeries: List<Series>
) {
    Column {
        MoviesSeriesHeader(
            navController = navController,
            title = "Trending",
            isMovie = false,
            genreId = 0
        )
        SeriesRowList(
            navController = navController,
            seriesList = trendingSeries
        )
    }
}

@Composable
fun GenreSeriesList(
    navController: NavController,
    title: String,
    genreId: Int,
    viewModel: SeriesViewModel = hiltViewModel(),
) {
    val genreSeries by remember {
        viewModel.getSeriesByGenre(genre = genreId)
    }
    Column {
        MoviesSeriesHeader(
            navController = navController,
            title = title,
            isMovie = false,
            genreId = genreId
        )
        SeriesRowList(
            navController = navController,
            seriesList = genreSeries
        )
    }
}