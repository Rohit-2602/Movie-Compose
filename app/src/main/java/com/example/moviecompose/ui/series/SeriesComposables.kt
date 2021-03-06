package com.example.moviecompose.ui.series

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.moviecompose.model.entities.SeriesPoster
import com.example.moviecompose.model.network.Season
import com.example.moviecompose.network.MovieDBApi
import com.example.moviecompose.ui.navigation.NavScreen

@Composable
fun SeriesImage(
    navController: NavController,
    series: SeriesPoster
) {
    val painter = rememberImagePainter(
        data = MovieDBApi.getPosterPath(series.poster_path)
    )
    Image(
        painter = painter,
        contentDescription = "Series Image",
        modifier = Modifier
            .width(150.dp)
            .height(200.dp)
            .padding(start = 10.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colors.background)
            .clickable {
                navController.navigate("${NavScreen.SeriesDetail.route}/${series.id}")
            },
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun SeriesRowList(
    navController: NavController,
    seriesList: List<SeriesPoster>,
) {
    LazyRow(modifier = Modifier.padding(end = 10.dp, top = 10.dp)) {
        val itemCount = if (seriesList.size > 10) {
            10
        } else {
            seriesList.size
        }
        items(itemCount) {
            SeriesImage(
                navController = navController,
                series = seriesList[it]
            )
        }
    }
}

@Composable
fun SeasonsList(
    seriesName: String,
    seriesId: Int,
    seasons: List<Season>,
    navController: NavController
) {
    Text(
        text = "Seasons",
        style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(start = 10.dp, top = 10.dp)
    )
    LazyRow(modifier = Modifier.padding(top = 10.dp, end = 10.dp)) {
        items(seasons.size) {
            val painter = rememberImagePainter(
                data = MovieDBApi.getPosterPath(seasons[it].poster_path)
            )
            Column(
                modifier = Modifier
                    .width(150.dp)
                    .padding(start = 10.dp)
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colors.background)
                    .clickable {
                        navController.navigate("${NavScreen.SeasonDetail.route}/${seriesId}/${seasons[it].season_number}/${seriesName}")
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painter,
                    contentDescription = "Series Image",
                    modifier = Modifier
                        .size(200.dp),
                    contentScale = ContentScale.FillWidth
                )
                Text(
                    text = seasons[it].name,
                    style = TextStyle(color = Color.White, fontSize = 16.sp),
                    modifier = Modifier.padding(top = 5.dp)
                )
                Text(
                    text = "Episodes ${seasons[it].episode_count}",
                    style = TextStyle(color = Color.White, fontSize = 14.sp),
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }
        }
    }
}