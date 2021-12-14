package com.example.moviecompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviecompose.ui.detailScreen.MovieDetailScreen
import com.example.moviecompose.ui.detailScreen.SeriesDetailScreen
import com.example.moviecompose.ui.homeScreen.HomeScreen
import com.example.moviecompose.ui.homeScreen.movieScreen.MovieScreen
import com.example.moviecompose.ui.homeScreen.myListScreen.MyListScreen
import com.example.moviecompose.ui.homeScreen.seriesScreen.SeriesScreen
import com.example.moviecompose.ui.theme.ComposeMovieTheme
import com.example.moviecompose.util.Routes
import com.example.moviecompose.util.Routes.HOME_SCREEN_ROUTE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMovieTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = HOME_SCREEN_ROUTE) {
                    composable(HOME_SCREEN_ROUTE) {
                        HomeScreen(mainNavController = navController)
                    }
                    composable(Routes.MOVIE_SCREEN_ROUTE) {
                        MovieScreen(mainNavController = navController)
                    }
                    composable(Routes.SERIES_SCREEN_ROUTE) {
                        SeriesScreen(mainNavController = navController)
                    }
                    composable(Routes.MY_LIST_SCREEN_ROUTE) {
                        MyListScreen(navController = navController)
                    }
                    composable(
                        route = "${Routes.MOVIE_DETAIL_SCREEN_ROUTE}/{movieId}",
                        arguments = listOf(
                            navArgument("movieId") {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        val movieId = remember {
                            it.arguments?.getInt("movieId")
                        }
                        MovieDetailScreen(navController = navController, movieId = movieId!!)
                    }
                    composable(
                        route = "${Routes.SERIES_DETAIL_SCREEN_ROUTE}/{seriesId}",
                        arguments = listOf(
                            navArgument("seriesId") {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        val seriesId = remember {
                            it.arguments?.getInt("seriesId")
                        }
                        SeriesDetailScreen(navController = navController, seriesId = seriesId!!)
                    }
                }
            }
        }
    }
}