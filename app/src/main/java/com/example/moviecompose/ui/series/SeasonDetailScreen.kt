package com.example.moviecompose.ui.series

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.moviecompose.model.Episode
import com.example.moviecompose.network.MovieDBApi
import com.example.moviecompose.ui.RetrySection

@Composable
fun SeasonDetailScreen(
    seriesId: Int,
    seriesName: String,
    seasonNumber: Int,
    viewModel: SeriesViewModel = hiltViewModel()
) {

    val season by remember {
        viewModel.getSeasonDetails(seriesId = seriesId, seasonNumber = seasonNumber)
    }

    val isLoading by remember {
        viewModel.isLoading
    }

    val loadError by remember {
        viewModel.loadError
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        if (loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.getSeasonDetails(seriesId = seriesId, seasonNumber = seasonNumber)
            }
        }
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        if (!isLoading && loadError.isEmpty() && season != null) {
            LazyColumn {
                item {
                    val posterImage =
                        rememberImagePainter(data = MovieDBApi.getPosterPath(season!!.poster_path),
                            builder = {
                                size(240)
                            })
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = posterImage,
                            contentDescription = "Season Poster",
                            modifier = Modifier.size(240.dp)
                        )
                        Text(
                            text = seriesName,
                            style = TextStyle(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        )
                        Text(
                            text = "Season $seasonNumber",
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        )
                    }
                }
                item {
                    Text(
                        text = "Episodes",
                        style = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.padding(10.dp)
                    )
                }
                items(season!!.episodes.size) {
                    Episode(episode = season!!.episodes[it], index = it)
                }
            }
        }
    }
}

@Composable
fun Episode(episode: Episode, index: Int) {
    val painter =
        rememberImagePainter(data = MovieDBApi.getPosterPath(episode.still_path))
    Row(
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .shadow(5.dp, RoundedCornerShape(5.dp))
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colors.background)
    ) {
        Image(
            painter = painter,
            contentDescription = "Episode Image",
            modifier = Modifier
                .width(160.dp)
                .height(90.dp)
                .fillMaxHeight()
                .shadow(5.dp, RoundedCornerShape(5.dp))
                .clip(RoundedCornerShape(5.dp))
        )
        Column {
            Text(
                text = "Episode ${index + 1}",
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 12.sp,
                ),
                modifier = Modifier.padding(start = 10.dp)
            )
            Text(
                text = episode.name,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 10.dp)
            )
            Text(
                text = episode.overview,
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 13.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}