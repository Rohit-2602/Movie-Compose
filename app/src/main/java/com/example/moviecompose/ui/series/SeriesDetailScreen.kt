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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviecompose.model.entities.Series
import com.example.moviecompose.network.MovieDBApi
import com.example.moviecompose.ui.*

@Composable
fun SeriesDetailScreen(
    navController: NavController,
    seriesId: Int,
    viewModel: SeriesViewModel = hiltViewModel()
) {

    var isFavSeries by remember {
        viewModel.isFavourite(seriesId = seriesId)
    }

    val seriesDetail by remember {
        viewModel.getSeriesDetails(seriesId = seriesId)
    }

    val castList by remember {
        viewModel.getSeriesCast(seriesId = seriesId)
    }

    val trailerList by remember {
        viewModel.getSeriesTrailers(seriesId = seriesId)
    }

    val seriesRecommendation by remember {
        viewModel.getSeriesRecommendation(seriesId = seriesId)
    }

    val isLoading by remember {
        viewModel.isLoading
    }

    val errorMessage by remember {
        viewModel.loadError
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
                viewModel.getSeriesDetails(seriesId = seriesId)
            }
        }
        if (!isLoading && errorMessage.isEmpty() && seriesDetail != null) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    BackDropPoster(
                        backDropPoster = MovieDBApi.getBackDropPath(seriesDetail!!.backdrop_path),
                        navController = navController,
                        isFavourite = isFavSeries
                    ) {
                        val genreIds = seriesDetail!!.genres.map { genre ->
                            genre.id
                        }
                        val series = Series(
                            id = seriesDetail!!.id,
                            name = seriesDetail!!.name,
                            original_name = seriesDetail!!.original_name,
                            overview = seriesDetail!!.overview,
                            backdrop_path = seriesDetail!!.backdrop_path,
                            poster_path = seriesDetail!!.poster_path,
                            genre_ids = genreIds,
                            vote_average = seriesDetail!!.vote_average,
                            trailers = trailerList,
                            cast = castList,
                            recommendation = seriesRecommendation
                        )
                        if (isFavSeries) {
                            viewModel.removeSeriesFromFavourite(series = series)
                            isFavSeries = false
                        } else {
                            viewModel.addSeriesToFavourite(series = series)
                            isFavSeries = true
                        }
                    }
                    GenreRatingDetail(
                        genre = seriesDetail!!.genres[0].name,
                        voteAverage = seriesDetail!!.vote_average
                    )
                    TitleDescriptionDetail(
                        title = seriesDetail!!.name,
                        description = seriesDetail!!.overview
                    )
                    if (trailerList.isNotEmpty()) {
                        Trailers(trailers = trailerList)
                    }
                    CastList(castList = castList)
                    SeasonsList(seriesName = seriesDetail!!.name, seriesId = seriesId, seasons = seriesDetail!!.seasons, navController = navController)
                    Column(modifier = Modifier.padding(bottom = 10.dp, top = 10.dp)) {
                        Text(
                            text = "Recommendations",
                            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(start = 10.dp)
                        )
                        SeriesRowList(mainNavController = navController, seriesList = seriesRecommendation)
                    }
                }
            }
        }
    }
}