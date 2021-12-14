package com.example.moviecompose.ui.detailScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.moviecompose.R
import com.example.moviecompose.model.Genre

@Composable
fun BackDropPoster(backDropPoster: String) {

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
        Image(
            painter = backArrow,
            contentDescription = "Back Button",
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp)
                .width(30.dp)
                .height(30.dp)
        )
    }
}

@Composable
fun GenreRating(genre: Genre, voteAverage: Double) {
    Row(Modifier.padding(start = 10.dp, top = 10.dp)) {
        // Genres
        Text(
            text = genre.name,
            style = TextStyle(
                color = Color.White,
                fontSize = 17.sp
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
                text = voteAverage.toString(),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 17.sp,
                ),
                modifier = Modifier.padding(start = 5.dp, end = 5.dp)
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

@Composable
fun Title(
    movieTitle: String,
    movieDescription: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = movieTitle,
            style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        )
        Text(
            text = movieDescription,
            style = TextStyle(color = Color.White, fontSize = 16.sp),
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}