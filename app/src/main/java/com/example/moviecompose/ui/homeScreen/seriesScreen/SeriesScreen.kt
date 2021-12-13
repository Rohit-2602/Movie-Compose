package com.example.moviecompose.ui.homeScreen.seriesScreen

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
import com.example.moviecompose.model.Series
import com.example.moviecompose.ui.homeScreen.HomeScreenViewModel
import com.example.moviecompose.ui.homeScreen.MovieSeriesImage
import com.example.moviecompose.ui.homeScreen.MoviesSeriesHeader
import com.example.moviecompose.ui.homeScreen.RetrySection
import com.example.moviecompose.util.Constant

@Composable
fun SeriesScreen(
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
                viewModel.getTrendingSeries()
            }
        }
        if (errorMessage.isEmpty()) {
            LazyColumn(modifier = Modifier.padding(bottom = 10.dp)) {
                item {
                    TrendingSeriesList(navController = navController)
                }
                item {
                    for ((key, value) in Constant.SERIES_GENRE_LIST) {
                        GenreSeriesList(navController = navController, title = key, genreId = value)
                    }
                }
            }
        }
    }
}

@Composable
fun TrendingSeriesList(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val trendingSeries by remember {
        viewModel.trendingSeries
    }
    val isLoading by remember {
        viewModel.isLoading
    }
    if (isLoading) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else {
        SeriesList(navController = navController, title = "Trending", seriesList = trendingSeries)
    }
}

@Composable
fun GenreSeriesList(
    navController: NavController,
    title: String,
    genreId: Int,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val genreSeries by remember {
        viewModel.getSeriesByGenre(genre = genreId)
    }
    val isLoading by remember {
        viewModel.isLoading
    }
    if (isLoading) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else {
        SeriesList(navController = navController, title = title, seriesList = genreSeries)
    }
}

@Composable
fun SeriesList(
    navController: NavController,
    title: String,
    seriesList: List<Series>,
) {
    Column {
        MoviesSeriesHeader(title = title)
        LazyRow(modifier = Modifier.padding(end = 10.dp, top = 10.dp)) {
            val itemCount = if (seriesList.size > 10) {
                10
            } else {
                seriesList.size
            }
            items(itemCount) {
                MovieSeriesImage(navController = navController, posterPath = seriesList[it].poster_path)
            }
        }
    }
}