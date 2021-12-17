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
import com.example.moviecompose.ui.homeScreen.HomeScreen
import com.example.moviecompose.ui.movie.GenreMovieDetail
import com.example.moviecompose.ui.movie.MovieDetailScreen
import com.example.moviecompose.ui.series.GenreSeriesDetail
import com.example.moviecompose.ui.series.SeasonDetailScreen
import com.example.moviecompose.ui.series.SeriesDetailScreen
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
                    composable(
                        route = "${Routes.SEASON_DETAIL_SCREEN}/{seriesId}/{seasonNumber}/{seasonName}",
                        arguments = listOf(
                            navArgument("seriesId") {
                                type = NavType.IntType
                            },
                            navArgument("seasonNumber") {
                                type = NavType.IntType
                            },
                            navArgument("seasonName") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val seriesId = remember {
                            it.arguments?.getInt("seriesId")
                        }
                        val seasonNumber = remember {
                            it.arguments?.getInt("seasonNumber")
                        }
                        val seasonName = remember {
                            it.arguments?.getString("seasonName")
                        }
                        SeasonDetailScreen(seriesId = seriesId!!, seasonNumber = seasonNumber!!, seriesName = seasonName!!)
                    }
                }
            }
        }
    }
}