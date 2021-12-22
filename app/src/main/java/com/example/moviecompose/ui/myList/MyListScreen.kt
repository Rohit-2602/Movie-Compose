package com.example.moviecompose.ui.myList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviecompose.ui.movie.genreMovieList.MovieList
import com.example.moviecompose.ui.series.genreSeriesList.SeriesList

@Composable
fun MyListScreen(
    navController: NavController,
    viewModel: MyListViewModel = hiltViewModel()
) {

    val movieList by remember {
        viewModel.getFavouriteMovieList()
    }

    val seriesList by remember {
        viewModel.getFavouriteSeriesList()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        LazyColumn {
            if (movieList.isNotEmpty()) {
                item {
                    Text(
                        text = "Favourite Movies",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(10.dp)
                    )
                }
                items(movieList.size) {
                    MovieList(
                        navController = navController,
                        index = it,
                        movieList = movieList,
                        genreIdList = movieList[it].genre_ids
                    )
                }
            }
            if (seriesList.isNotEmpty()) {
                item {
                    Text(
                        text = "Favourite Series",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(10.dp)
                    )
                }
                items(seriesList.size) {
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