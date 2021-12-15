package com.example.moviecompose.ui.genreDetailScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.moviecompose.R
import com.example.moviecompose.api.MovieDBApi
import com.example.moviecompose.model.Movie
import com.example.moviecompose.model.Series
import com.example.moviecompose.util.Constant
import com.example.moviecompose.util.Routes

@Composable
fun MovieList(
    mainNavController: NavController,
    index: Int,
    movieList: List<Movie>,
    genreList: List<Int>
) {

    val movie = movieList[index]
    val posterPath = MovieDBApi.getPosterPath(movie.poster_path)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colors.background)
            .clickable {
                mainNavController.navigate("${Routes.MOVIE_DETAIL_SCREEN}/${movie.id}")
            }
    ) {
        PosterImage(posterPath = posterPath)
        Column(modifier = Modifier.height(150.dp)) {
            TitleDescription(title = movie.title, description = movie.overview)
            RatingGenre(genreList = genreList, voteAverage = movie.vote_average, isMovie = true)
        }
    }
}

@Composable
fun SeriesList(
    mainNavController: NavController,
    index: Int,
    seriesList: List<Series>,
    genreList: List<Int>
) {

    val series = seriesList[index]
    val posterPath = MovieDBApi.getPosterPath(series.poster_path)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colors.background)
            .clickable {
                mainNavController.navigate("${Routes.SERIES_DETAIL_SCREEN}/${series.id}")
            }
    ) {
        PosterImage(posterPath = posterPath)
        Column(modifier = Modifier.height(150.dp)) {
            TitleDescription(title = series.name, description = series.overview)
            RatingGenre(genreList = genreList, voteAverage = series.vote_average, isMovie = false)
        }
    }
}

@Composable
fun RatingGenre(genreList: List<Int>, voteAverage: Float, isMovie: Boolean) {
    val genres = mutableListOf<String>()
    if (isMovie) {
        for ((key, value) in Constant.MOVIES_GENRE_LIST) {
            if (genreList.contains(value)) {
                genres.add(key)
            }
        }
    } else {
        for ((key, value) in Constant.SERIES_GENRE_LIST) {
            if (genreList.contains(value)) {
                genres.add(key)
            }
        }
    }

    Row(
        Modifier
            .padding(start = 10.dp, bottom = 3.dp)
            .fillMaxHeight(),
        verticalAlignment = Alignment.Bottom
    ) {
        // Genres
        Text(
            text = genres[0],
            style = TextStyle(
                color = Color.White,
                fontSize = 15.sp
            ),
            modifier = Modifier
                .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = Color.Black)
                .padding(5.dp)
        )

        // Rating
        Row(
            modifier = Modifier
                .offset(x = 10.dp)
                .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = Color.Black)
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val ratingStar = rememberImagePainter(data = R.drawable.ic_star)
            Image(
                painter = ratingStar,
                modifier = Modifier
                    .width(18.dp)
                    .height(18.dp),
                contentDescription = "Rating Start"
            )
            Text(
                text = voteAverage.toString(),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 15.sp,
                ),
                modifier = Modifier.padding(start = 5.dp, end = 5.dp)
            )
        }
    }
}

@Composable
fun TitleDescription(title: String, description: String) {
    Column(modifier = Modifier.padding(start = 10.dp, top = 5.dp)) {
        Text(
            text = title,
            style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = description,
            style = TextStyle(color = Color.White, fontSize = 16.sp),
            modifier = Modifier.padding(top = 5.dp),
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun PosterImage(posterPath: String) {
    val painter = rememberImagePainter(data = posterPath)
    Column(
        modifier = Modifier
            .width(120.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
    ) {
        Image(
            painter = painter,
            contentDescription = "Series Image",
            modifier = Modifier
                .size(150.dp)
                .background(color = MaterialTheme.colors.background),
            contentScale = ContentScale.FillWidth
        )
    }
}