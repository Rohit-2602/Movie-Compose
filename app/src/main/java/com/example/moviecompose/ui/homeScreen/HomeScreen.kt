package com.example.moviecompose.ui.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.moviecompose.model.Movie
import com.example.moviecompose.util.Constant

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    val isLoading by remember {
        viewModel.isLoading
    }

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
                viewModel.getTrendingMovies()
            }
        }
        if (errorMessage.isEmpty()) {
            LazyColumn(modifier = Modifier.padding(bottom = 10.dp)) {
                item {
                    TrendingMovieList(navController = navController)
                }
                item {
                    for ((key, value) in Constant.GENRES_LIST) {
                        GenreMovieList(navController = navController, title = key, genreId = value)
                    }
                }
            }
        }
    }
}

@Composable
fun TrendingMovieList(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val trendingMovies by remember {
        viewModel.trendingMovies
    }
    val isLoading by remember {
        viewModel.isLoading
    }
    if (isLoading) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else {
        MovieList(navController = navController, title = "Trending", movieList = trendingMovies)
    }
}

@Composable
fun GenreMovieList(
    navController: NavController,
    title: String,
    genreId: Int,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val animationMovies by remember {
        viewModel.getMoviesByGenre(genre = genreId)
    }
    val isLoading by remember {
        viewModel.isLoading
    }
    if (isLoading) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else {
        MovieList(navController = navController, title = title, movieList = animationMovies)
    }
}

@Composable
fun MovieList(
    navController: NavController,
    title: String,
    movieList: List<Movie>,
) {
    Column {
        MovieHeader(title = title)
        LazyRow(modifier = Modifier.padding(end = 10.dp, top = 10.dp)) {
            val itemCount = if (movieList.size > 10) {
                10
            } else {
                movieList.size
            }
            items(itemCount) {
                MovieEntry(navController = navController, movie = movieList[it])
            }
        }
    }
}

@Composable
fun MovieEntry(
    navController: NavController,
    movie: Movie
) {
    val painter = rememberImagePainter(
        data = movie.poster_path
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
fun MovieHeader(title: String) {
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