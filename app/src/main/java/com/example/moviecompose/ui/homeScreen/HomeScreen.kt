package com.example.moviecompose.ui.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviecompose.ui.movie.MovieScreen
import com.example.moviecompose.ui.myList.MyListScreen
import com.example.moviecompose.ui.series.SeriesScreen
import com.example.moviecompose.util.Routes.MOVIE_SCREEN
import com.example.moviecompose.util.Routes.MY_LIST_SCREEN
import com.example.moviecompose.util.Routes.SERIES_SCREEN

@Composable
fun HomeScreen(
    mainNavController: NavController,
) {

    var selectedTab by remember {
        mutableStateOf(0)
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
                    homeNavController.navigate(MOVIE_SCREEN) {
                        popUpTo(MOVIE_SCREEN) {
                            inclusive = true
                        }
                    }
                    selectedTab = 0
                }
                HomeScreenHeaderItem(text = "Series", selected = selectedTab == 1) {
                    homeNavController.navigate(SERIES_SCREEN)
                    selectedTab = 1
                }
                HomeScreenHeaderItem(text = "My List", selected = selectedTab == 2) {
                    homeNavController.navigate(MY_LIST_SCREEN)
                    selectedTab = 2
                }
            }
            NavHost(navController = homeNavController, startDestination = MOVIE_SCREEN) {
                composable(MOVIE_SCREEN) {
                    MovieScreen(mainNavController = mainNavController)
                    selectedTab = 0
                }
                composable(SERIES_SCREEN) {
                    SeriesScreen(mainNavController = mainNavController)
                    selectedTab = 1
                }
                composable(MY_LIST_SCREEN) {
                    MyListScreen(mainNavController = mainNavController)
                    selectedTab = 2
                }
            }
        }
    }
}

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