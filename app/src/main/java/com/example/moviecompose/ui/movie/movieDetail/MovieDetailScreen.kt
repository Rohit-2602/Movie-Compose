package com.example.moviecompose.ui.movie.movieDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviecompose.model.entities.Movie
import com.example.moviecompose.network.MovieDBApi
import com.example.moviecompose.ui.*
import com.example.moviecompose.ui.movie.MovieRowList
import com.example.moviecompose.ui.movie.MovieRunTime

@Composable
fun MovieDetailScreen(
    navController: NavController,
    movieId: Int,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {

    var isFavMovie by remember {
        viewModel.isFavourite(movieId = movieId)
    }

    val movieDetails by remember {
        viewModel.getMovieDetails(movieId = movieId)
    }

    val castList by remember {
        viewModel.getMovieCast(movieId = movieId)
    }

    val trailerList by remember {
        viewModel.getMovieTrailers(movieId = movieId)
    }

    val movieRecommendation by remember {
        viewModel.getMoviePosterRecommendation(movieId = movieId)
    }

    val isLoading by remember {
        viewModel.isLoading
    }

    val errorMessage by remember {
        viewModel.loadError
    }

    Surface(
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        if (isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
        if (errorMessage.isNotEmpty()) {
            RetrySection(error = errorMessage) {
                viewModel.getMovieDetails(movieId = movieId)
            }
        }
        if (!isLoading && errorMessage.isEmpty() && movieDetails != null) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    BackDropPoster(
                        backDropPoster = MovieDBApi.getBackDropPath(movieDetails!!.backdrop_path),
                        navController = navController,
                        isFavourite = isFavMovie
                    ) {
                        val genreIds = movieDetails!!.genres.map { genre ->
                            genre.id
                        }
                        val movie = Movie(
                            id = movieDetails!!.id,
                            title = movieDetails!!.title,
                            original_title = movieDetails!!.original_title,
                            overview = movieDetails!!.overview,
                            backdrop_path = movieDetails!!.backdrop_path,
                            poster_path = movieDetails!!.poster_path,
                            genre_ids = genreIds,
                            vote_average = movieDetails!!.vote_average,
                            trailers = trailerList,
                            cast = castList,
                            recommendation = movieRecommendation
                        )
                        if (isFavMovie) {
                            viewModel.removeMovieFromFavourite(movie = movie)
                            isFavMovie = false
                        } else {
                            viewModel.addMovieToFavourite(movie = movie)
                            isFavMovie = true
                        }
                    }
                    Row {
                        GenreRatingDetail(
                            genre = movieDetails!!.genres[0].name,
                            voteAverage = movieDetails!!.vote_average
                        )
                        MovieRunTime(runTime = movieDetails!!.runtime)
                    }
                    TitleDescriptionDetail(
                        title = movieDetails!!.original_title,
                        description = movieDetails!!.overview
                    )
                    if (trailerList.isNotEmpty()) {
                        Trailers(trailers = trailerList)
                    }
                    CastList(castList = castList, navController = navController)
                    if (movieRecommendation.isNotEmpty()) {
                        Column(modifier = Modifier.padding(bottom = 10.dp, top = 10.dp)) {
                            Text(
                                text = "Recommendations",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(start = 10.dp)
                            )
                            MovieRowList(
                                navController = navController,
                                movieList = movieRecommendation
                            )
                        }
                    }
                }
            }
        }
    }
}