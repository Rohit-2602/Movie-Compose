package com.example.moviecompose.ui.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

@Composable
fun HomeScreenHeaderItem(text: String, selected: Boolean, onClick: () -> Unit) {
    val color = if (selected) {
        MaterialTheme.colors.primary
    } else {
        Color.White
    }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .clickable {
                onClick()
            },
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = color,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MoviesSeriesHeader(title: String) {
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
            modifier = Modifier.padding(start = 10.dp)
        )
        Text(text = "See all",
            style = TextStyle(color = MaterialTheme.colors.primary, fontSize = 15.sp),
            modifier = Modifier
                .padding(end = 20.dp)
                .clickable {

                }
        )
    }
}

@Composable
fun MovieSeriesImage(
    navController: NavController,
    posterPath: String?
) {
    val painter = rememberImagePainter(
        data = posterPath
    )
    Column(
        modifier = Modifier
            .width(150.dp)
            .padding(start = 10.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
    ) {
        Image(
            painter = painter,
            contentDescription = "Movie Image",
            modifier = Modifier
                .size(200.dp),
            contentScale = ContentScale.FillWidth
        )
    }
}

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