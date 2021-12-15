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
import com.example.moviecompose.ui.genreDetailScreen.GenreMovieDetail
import com.example.moviecompose.ui.genreDetailScreen.GenreSeriesDetail
import com.example.moviecompose.ui.homeScreen.HomeScreen
import com.example.moviecompose.ui.homeScreen.movieScreen.MovieScreen
import com.example.moviecompose.ui.homeScreen.myListScreen.MyListScreen
import com.example.moviecompose.ui.homeScreen.seriesScreen.SeriesScreen
import com.example.moviecompose.ui.theme.ComposeMovieTheme
import com.example.moviecompose.util.Routes
import com.example.moviecompose.util.Routes.HOME_SCREEN
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMovieTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = HOME_SCREEN) {
                    composable(HOME_SCREEN) {
                        HomeScreen(mainNavController = navController)
                    }
                    composable(Routes.MOVIE_SCREEN) {
                        MovieScreen(mainNavController = navController)
                    }
                    composable(Routes.SERIES_SCREEN) {
                        SeriesScreen(mainNavController = navController)
                    }
                    composable(Routes.MY_LIST_SCREEN) {
                        MyListScreen(navController = navController)
                    }
                    composable(
                        route = "${Routes.MOVIE_DETAIL_SCREEN}/{movieId}",
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
                        route = "${Routes.SERIES_DETAIL_SCREEN}/{seriesId}",
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
                    composable(
                        route = "${Routes.GENRE_MOVIE_SCREEN}/{genreId}/{genreTitle}",
                        arguments = listOf(
                            navArgument("genreId") {
                                type = NavType.IntType
                            },
                            navArgument("genreTitle") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val genreId = remember {
                            it.arguments?.getInt("genreId")
                        }
                        val genreTitle = remember {
                            it.arguments?.getString("genreTitle")
                        }
                        GenreMovieDetail(mainNavController = navController, genreId = genreId!!, genreTitle = genreTitle!!)
                    }
                    composable(
                        route = "${Routes.GENRE_SERIES_SCREEN}/{genreId}/{genreTitle}",
                        arguments = listOf(
                            navArgument("genreId") {
                                type = NavType.IntType
                            },
                            navArgument("genreTitle") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val genreId = remember {
                            it.arguments?.getInt("genreId")
                        }
                        val genreTitle = remember {
                            it.arguments?.getString("genreTitle")
                        }
                        GenreSeriesDetail(mainNavController = navController, genreId = genreId!!, genreTitle = genreTitle!!)
                    }
                }
            }
        }
    }
}