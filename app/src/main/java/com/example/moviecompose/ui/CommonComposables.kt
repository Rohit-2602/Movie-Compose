package com.example.moviecompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.moviecompose.R
import com.example.moviecompose.model.Video
import com.example.moviecompose.network.MovieDBApi
import com.example.moviecompose.util.Routes

@Composable
fun RetrySection(error: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = error, style = TextStyle(color = Color.White, fontSize = 20.sp))
        Button(onClick = { onRetry() }) {
            Text(text = "Retry")
        }
    }
}

@Composable
fun MoviesSeriesHeader(
    mainNavController: NavController,
    title: String,
    isMovie: Boolean,
    genreId: Int
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Text(
            text = title, style = TextStyle(
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(start = 15.dp)
        )
        Text(text = "See all",
            style = TextStyle(color = MaterialTheme.colors.primary, fontSize = 15.sp),
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                .clickable {
                    if (isMovie) {
                        mainNavController.navigate("${Routes.GENRE_MOVIE_SCREEN}/${genreId}/${title}")
                    } else {
                        mainNavController.navigate("${Routes.GENRE_SERIES_SCREEN}/${genreId}/${title}")
                    }
                }
        )
    }
}

@Composable
fun BackDropPoster(navController: NavController, backDropPoster: String) {
    val backArrow = rememberImagePainter(R.drawable.ic_back)
    val moviePoster = rememberImagePainter(
        data = backDropPoster,
        builder = {
            size(1280 * 720)
        })

    Box {
        Image(
            painter = moviePoster,
            contentDescription = backDropPoster,
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, MaterialTheme.colors.background)
                    )
                )
                .fillMaxWidth()
                .height(50.dp)
                .align(Alignment.BottomCenter)
        ) {

        }
        Image(
            painter = backArrow,
            contentDescription = "Back Button",
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp)
                .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = MaterialTheme.colors.background)
                .size(30.dp)
                .padding(5.dp)
                .clickable {
                    navController.navigateUp()
                }
        )
    }
}

@Composable
fun GenreRatingDetail(genre: String, voteAverage: String) {
    Row(Modifier.padding(start = 10.dp, top = 10.dp)) {
        // Genres
        RatingGenreText(genre = genre, voteAverage = voteAverage, fontSize = 17.sp)
    }
}

@Composable
fun GenreRating(genre: String, voteAverage: String) {
    Row(
        Modifier
            .padding(start = 10.dp, bottom = 3.dp)
            .fillMaxHeight(),
        verticalAlignment = Alignment.Bottom
    ) {
        // Genres
        RatingGenreText(genre = genre, voteAverage = voteAverage, fontSize = 15.sp)
    }
}

@Composable
fun RatingGenreText(genre: String, voteAverage: String, fontSize: TextUnit) {
    Text(
        text = genre,
        style = TextStyle(
            color = Color.White,
            fontSize = fontSize
        ),
        modifier = Modifier
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colors.background)
            .padding(5.dp)
    )

    // Rating
    Row(
        modifier = Modifier
            .offset(x = 10.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colors.background)
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
            text = voteAverage,
            style = TextStyle(
                color = Color.White,
                fontSize = fontSize,
            ),
            modifier = Modifier.padding(start = 5.dp, end = 5.dp)
        )
    }
}


@Composable
fun TitleDescriptionDetail(
    title: String,
    description: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        )
        Text(
            text = description,
            style = TextStyle(color = Color.White, fontSize = 16.sp),
            modifier = Modifier.padding(top = 10.dp)
        )
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

@Composable
fun Trailers(trailers: List<Video>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp)
    ) {
        Text(
            text = "Trailers",
            style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        )
        LazyRow {
            items(trailers.size) {
                val painter =
                    rememberImagePainter(
                        data = MovieDBApi.getYoutubeThumbnail(trailers[it].key),
                        builder = {
                            size(120 * 90)
                        })
                Column(modifier = Modifier.padding(end = 10.dp)) {
                    Image(
                        painter = painter,
                        contentDescription = "Youtube Thumbnail",
                        modifier = Modifier
                            .width(160.dp)
                            .height(100.dp)
                            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(color = MaterialTheme.colors.background),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}