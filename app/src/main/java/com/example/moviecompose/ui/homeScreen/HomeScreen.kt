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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviecompose.ui.homeScreen.movieScreen.MovieScreen
import com.example.moviecompose.ui.homeScreen.myListScreen.MyListScreen
import com.example.moviecompose.ui.homeScreen.seriesScreen.SeriesScreen
import com.example.moviecompose.util.Routes.MOVIE_SCREEN_ROUTE
import com.example.moviecompose.util.Routes.MY_LIST_SCREEN_ROUTE
import com.example.moviecompose.util.Routes.SERIES_SCREEN_ROUTE

@Composable
fun HomeScreen(
    mainNavController: NavController,
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
        val homeNavController = rememberNavController()
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HomeScreenHeaderItem(text = "Movies", selected = selectedTab == 0) {
                    homeNavController.navigate(MOVIE_SCREEN_ROUTE)
                    viewModel.selectedTab.value = 0
                }
                HomeScreenHeaderItem(text = "Series", selected = selectedTab == 1) {
                    homeNavController.navigate(SERIES_SCREEN_ROUTE)
                    viewModel.selectedTab.value = 1
                }
                HomeScreenHeaderItem(text = "My List", selected = selectedTab == 2) {
                    homeNavController.navigate(MY_LIST_SCREEN_ROUTE)
                    viewModel.selectedTab.value = 2
                }
            }
            NavHost(navController = homeNavController, startDestination = MOVIE_SCREEN_ROUTE) {
                composable(MOVIE_SCREEN_ROUTE) {
                    MovieScreen(mainNavController = mainNavController)
                }
                composable(SERIES_SCREEN_ROUTE) {
                    SeriesScreen(mainNavController = mainNavController)
                }
                composable(MY_LIST_SCREEN_ROUTE) {
                    MyListScreen(navController = mainNavController)
                }
            }
        }
    }
}