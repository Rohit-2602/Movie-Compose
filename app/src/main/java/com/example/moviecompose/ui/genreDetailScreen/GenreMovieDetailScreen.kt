package com.example.moviecompose.ui.genreDetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviecompose.ui.homeScreen.RetrySection

@Composable
fun GenreMovieDetail(
    mainNavController: NavController,
    genreId: Int,
    genreTitle: String,
    viewModel: GenreDetailViewModel = hiltViewModel()
) {

    val movieList by rememberSaveable {
        viewModel.getMovies(genreId = genreId)
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
                viewModel.getMovies(genreId = genreId)
            }
        }
        if (errorMessage.isEmpty()) {
            LazyColumn {
                item {
                    Text(
                        text = "$genreTitle Movies",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(10.dp)
                    )
                }
                items(movieList.size) {
                    if (it >= movieList.size - 1 && !endReached && !isLoading) {
                        viewModel.getMovies(genreId)
                    }
                    MovieList(
                        mainNavController = mainNavController,
                        index = it,
                        movieList = movieList,
                        genreList = movieList[it].genre_ids
                    )
                }
            }
        }
    }

}