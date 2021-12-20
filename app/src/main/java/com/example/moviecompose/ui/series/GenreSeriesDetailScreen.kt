package com.example.moviecompose.ui.series

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviecompose.model.entities.Series
import com.example.moviecompose.network.MovieDBApi
import com.example.moviecompose.ui.GenreRating
import com.example.moviecompose.ui.PosterImage
import com.example.moviecompose.ui.RetrySection
import com.example.moviecompose.ui.TitleDescription
import com.example.moviecompose.ui.navigation.NavScreen
import com.example.moviecompose.util.Constant

@Composable
fun GenreSeriesDetail(
    navController: NavController,
    genreId: Int,
    genreTitle: String,
    viewModel: SeriesViewModel = hiltViewModel()
) {

    val seriesList by rememberSaveable {
        viewModel.getPaginatedSeries(genreId = genreId)
    }

    val isLoading by remember {
        viewModel.isLoading
    }

    val errorMessage by remember {
        viewModel.loadError
    }

    val endReached by remember {
        viewModel.endReached
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        if (errorMessage.isNotEmpty()) {
            RetrySection(error = errorMessage) {
                viewModel.getPaginatedSeries(genreId = genreId)
            }
        }
        if (errorMessage.isEmpty()) {
            LazyColumn {
                item {
                    Text(
                        text = "$genreTitle Series",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(10.dp)
                    )
                }
                items(seriesList.size) {
                    if (it >= seriesList.size - 1 && !endReached && !isLoading) {
                        viewModel.getPaginatedSeries(genreId)
                    }
                    SeriesList(
                        navController = navController,
                        index = it,
                        seriesList = seriesList,
                        genreIdList = seriesList[it].genre_ids
                    )
                }
            }
        }
    }
}

@Composable
fun SeriesList(
    navController: NavController,
    index: Int,
    seriesList: List<Series>,
    genreIdList: List<Int>
) {

    val series = seriesList[index]
    val posterPath = MovieDBApi.getPosterPath(series.poster_path)
    val genres = mutableListOf<String>()
    for (genre in Constant.SERIES_GENRE_LIST) {
        if (genreIdList.contains(genre.id)) {
            genres.add(genre.name)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colors.background)
            .clickable {
                navController.navigate("${NavScreen.SeriesDetail.route}/${series.id}")
            }
    ) {
        PosterImage(posterPath = posterPath)
        Column(modifier = Modifier.height(150.dp).padding(start = 10.dp)) {
            TitleDescription(title = series.name, description = series.overview)
            GenreRating(genre = genres[0], voteAverage = series.vote_average)
        }
    }
}