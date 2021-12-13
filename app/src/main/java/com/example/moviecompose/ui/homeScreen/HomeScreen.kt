package com.example.moviecompose.ui.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviecompose.ui.homeScreen.movieScreen.MovieScreen
import com.example.moviecompose.ui.homeScreen.myListScreen.MyListScreen
import com.example.moviecompose.ui.homeScreen.seriesScreen.SeriesScreen

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    val selectedTab by remember {
        viewModel.selectedTab
    }
    Surface(
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        val navController = rememberNavController()
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HomeScreenHeaderItem(text = "Movies", selected = selectedTab == 0) {
                    navController.navigate("movie_screen")
                    viewModel.selectedTab.value = 0
                }
                HomeScreenHeaderItem(text = "Series", selected = selectedTab == 1) {
                    navController.navigate("series_screen")
                    viewModel.selectedTab.value = 1
                }
                HomeScreenHeaderItem(text = "My List", selected = selectedTab == 2) {
                    navController.navigate("my_list_screen")
                    viewModel.selectedTab.value = 2
                }
            }
            NavHost(navController = navController, startDestination = "movie_screen") {
                composable("movie_screen") {
                    MovieScreen(navController = navController)
                }
                composable("my_list_screen") {
                    MyListScreen(navController = navController)
                }
                composable("series_screen") {
                    SeriesScreen(navController = navController)
                }
            }
        }
    }
}