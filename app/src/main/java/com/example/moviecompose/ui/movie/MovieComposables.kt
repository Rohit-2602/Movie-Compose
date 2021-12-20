package com.example.moviecompose.ui.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.moviecompose.model.entities.Movie
import com.example.moviecompose.network.MovieDBApi
import com.example.moviecompose.ui.navigation.NavScreen

@Composable
fun MovieImage(
    navController: NavController,
    movie: Movie
) {
    val painter = rememberImagePainter(
        data = MovieDBApi.getPosterPath(movie.poster_path)
    )
    Image(
        painter = painter,
        contentDescription = "Movie Image",
        modifier = Modifier
            .width(150.dp)
            .height(200.dp)
            .padding(start = 10.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colors.background)
            .clickable {
                navController.navigate("${NavScreen.MovieDetail.route}/${movie.id}")
            },
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun MovieRowList(
    navController: NavController,
    movieList: List<Movie>,
) {
    LazyRow(modifier = Modifier.padding(end = 10.dp, top = 10.dp)) {
        val itemCount = if (movieList.size > 10) {
            10
        } else {
            movieList.size
        }
        items(itemCount) {
            MovieImage(
                navController = navController,
                movie = movieList[it]
            )
        }
    }
}

@Composable
fun MovieRunTime(runTime: Int) {
    // Movie Length
    val hour = runTime / 60
    val min = runTime - 60 * hour
    Text(
        text = "${hour}h ${min}m",
        style = TextStyle(
            color = Color.White,
            fontSize = 17.sp
        ),
        modifier = Modifier
            .offset(x = 20.dp, y = 10.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colors.background)
            .padding(5.dp)
    )
}