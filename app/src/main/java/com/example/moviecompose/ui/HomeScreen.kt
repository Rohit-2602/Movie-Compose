package com.example.moviecompose.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviecompose.ui.movie.MovieScreen
import com.example.moviecompose.ui.myList.MyListScreen
import com.example.moviecompose.ui.series.SeriesScreen

@Composable
fun HomeScreen(mainNavController: NavController) {

    var selectedTab by remember {
        mutableStateOf(0)
    }
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TabRow(
                contentColor = MaterialTheme.colors.primary,
                selectedTabIndex = selectedTab
            ) {
                titles.forEachIndexed { index, _ ->
                    Tab(
                        selectedContentColor = MaterialTheme.colors.primary,
                        unselectedContentColor = Color.White,
                        selected = selectedTab == index,
                        onClick = {
                            navController.navigate(titles[index].route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                .padding(top = 12.dp, bottom = 12.dp),
                            text = titles[index].label,
                            style = TextStyle(
                                color = if (selectedTab == index) MaterialTheme.colors.primary else Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = titles[0].route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(HomeScreenDestinations.Movies.route) {
                MovieScreen(navController = mainNavController)
                selectedTab = 0
            }
            composable(HomeScreenDestinations.Series.route) {
                SeriesScreen(navController = mainNavController)
                selectedTab = 1
            }
            composable(HomeScreenDestinations.MyList.route) {
                MyListScreen(navController = mainNavController)
                selectedTab = 2
            }
        }
    }
}

private val titles = listOf(
    HomeScreenDestinations.Movies,
    HomeScreenDestinations.Series,
    HomeScreenDestinations.MyList
)

sealed class HomeScreenDestinations(
    val route: String,
    val label: String
) {
    object Movies : HomeScreenDestinations("movies", "Movies")
    object Series : HomeScreenDestinations("series", "Series")
    object MyList : HomeScreenDestinations("myList", "MyList")
}