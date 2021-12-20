package com.example.moviecompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviecompose.ui.movie.GenreMovieDetail
import com.example.moviecompose.ui.movie.MovieDetailScreen
import com.example.moviecompose.ui.navigation.NavScreen
import com.example.moviecompose.ui.people.PeopleScreen
import com.example.moviecompose.ui.search.SearchScreen
import com.example.moviecompose.ui.series.GenreSeriesDetail
import com.example.moviecompose.ui.series.SeasonDetailScreen
import com.example.moviecompose.ui.series.SeriesDetailScreen
import com.example.moviecompose.ui.theme.ComposeMovieTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMovieTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigation {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            bottomNavDestinations.forEach { bottomNavDestination ->
                                BottomNavigationItem(
                                    icon = {
                                        Icon(
                                            imageVector = bottomNavDestination.icon,
                                            contentDescription = "Bottom Nav Icon",
                                            tint = Color.White
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = bottomNavDestination.label,
                                            style = TextStyle(color = Color.White)
                                        )
                                    },
                                    alwaysShowLabel = false,
                                    selected = currentDestination?.hierarchy?.any { it.route == bottomNavDestination.route } == true,
                                    onClick = {
                                        navController.navigate(bottomNavDestination.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = bottomNavDestinations[0].route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(MainBottomNavDestination.Home.route) {
                            HomeScreen(mainNavController = navController)
                        }
                        composable(
                            route = NavScreen.MovieDetail.routeWithArgument,
                            arguments = listOf(
                                navArgument(NavScreen.MovieDetail.argument0) {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            val movieId = it.arguments?.getInt(NavScreen.MovieDetail.argument0)
                            MovieDetailScreen(navController = navController, movieId = movieId!!)
                        }
                        composable(
                            route = NavScreen.GenreMovieDetail.routeWithArgument,
                            arguments = listOf(
                                navArgument(NavScreen.GenreMovieDetail.argument0) {
                                    type = NavType.IntType
                                },
                                navArgument(NavScreen.GenreMovieDetail.argument1) {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val genreId = it.arguments?.getInt(NavScreen.GenreMovieDetail.argument0)
                            val genreTitle = it.arguments?.getString(NavScreen.GenreMovieDetail.argument1)
                            GenreMovieDetail(
                                navController = navController,
                                genreId = genreId!!,
                                genreTitle = genreTitle!!
                            )
                        }
                        composable(
                            route = NavScreen.SeriesDetail.routeWithArgument,
                            arguments = listOf(
                                navArgument(NavScreen.SeriesDetail.argument0) {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            val seriesId = it.arguments?.getInt(NavScreen.SeriesDetail.argument0)
                            SeriesDetailScreen(navController = navController, seriesId = seriesId!!)
                        }
                        composable(
                            route = NavScreen.GenreSeriesDetail.routeWithArgument,
                            arguments = listOf(
                                navArgument(NavScreen.GenreSeriesDetail.argument0) {
                                    type = NavType.IntType
                                },
                                navArgument(NavScreen.GenreSeriesDetail.argument1) {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val genreId = it.arguments?.getInt(NavScreen.GenreSeriesDetail.argument0)
                            val genreTitle = it.arguments?.getString(NavScreen.GenreSeriesDetail.argument1)
                            GenreSeriesDetail(
                                navController = navController,
                                genreId = genreId!!,
                                genreTitle = genreTitle!!
                            )
                        }
                        composable(
                            route = NavScreen.SeasonDetail.routeWithArgument,
                            arguments = listOf(
                                navArgument(NavScreen.SeasonDetail.argument0) {
                                    type = NavType.IntType
                                },
                                navArgument(NavScreen.SeasonDetail.argument1) {
                                    type = NavType.IntType
                                },
                                navArgument(NavScreen.SeasonDetail.argument2) {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val seriesId = it.arguments?.getInt(NavScreen.SeasonDetail.argument0)
                            val seasonNumber = it.arguments?.getInt(NavScreen.SeasonDetail.argument1)
                            val seasonName = it.arguments?.getString(NavScreen.SeasonDetail.argument2)
                            SeasonDetailScreen(
                                seriesId = seriesId!!,
                                seriesName = seasonName!!,
                                seasonNumber = seasonNumber!!
                            )
                        }
                        composable(MainBottomNavDestination.Search.route) {
                            SearchScreen(navController = navController)
                        }
                        composable(MainBottomNavDestination.People.route) {
                            PeopleScreen()
                        }
                    }
                }
            }
        }
    }
}

private val bottomNavDestinations = listOf(
    MainBottomNavDestination.Home,
    MainBottomNavDestination.Search,
    MainBottomNavDestination.People
)

sealed class MainBottomNavDestination(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : MainBottomNavDestination("home", Icons.Outlined.Home, "Home")
    object Search : MainBottomNavDestination("search", Icons.Outlined.Search, "Search")
    object People : MainBottomNavDestination("people", Icons.Outlined.People, "People")
}