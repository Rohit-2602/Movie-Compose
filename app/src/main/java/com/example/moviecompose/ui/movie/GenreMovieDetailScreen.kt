package com.example.moviecompose.ui.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviecompose.model.Movie
import com.example.moviecompose.network.MovieDBApi
import com.example.moviecompose.ui.GenreRating
import com.example.moviecompose.ui.PosterImage
import com.example.moviecompose.ui.RetrySection
import com.example.moviecompose.ui.TitleDescription
import com.example.moviecompose.util.Constant
import com.example.moviecompose.util.Routes

@Composable
fun GenreMovieDetail(
    mainNavController: NavController,
    genreId: Int,
    genreTitle: String,
    viewModel: MovieViewModel = hiltViewModel()
) {

    val movieList by rememberSaveable {
        viewModel.getMovies(genreId = genreId)
    }

    val isLoading by remember {
        viewModel.isLoading
    }

    val errorMessage by remember {
        viewModel.loadError
    }

    val endReached by remember {
        viewModel.endReached
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        if (errorMessage.isNotEmpty()) {
            RetrySection(error = errorMessage) {
                viewModel.getMovies(genreId = genreId)
            }
        }
        if (errorMessage.isEmpty()) {
            LazyColumn {
                item {
                    Text(
                        text = "$genreTitle Movies",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(10.dp)
                    )
                }
                items(movieList.size) {
                    if (it >= movieList.size - 1 && !endReached && !isLoading) {
                        viewModel.getMovies(genreId)
                    }
                    MovieList(
                        mainNavController = mainNavController,
                        index = it,
                        movieList = movieList,
                        genreIdList = movieList[it].genre_ids
                    )
                }
            }
        }
    }
}

@Composable
fun MovieList(
    mainNavController: NavController,
    index: Int,
    movieList: List<Movie>,
    genreIdList: List<Int>
) {
    val movie = movieList[index]
    val posterPath = MovieDBApi.getPosterPath(movie.poster_path)
    val genres = mutableListOf<String>()
    for (genre in Constant.MOVIES_GENRE_LIST) {
        if (genreIdList.contains(genre.id)) {
            genres.add(genre.name)
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colors.background)
            .clickable {
                mainNavController.navigate("${Routes.MOVIE_DETAIL_SCREEN}/${movie.id}")
            }
    ) {
        PosterImage(posterPath = posterPath)
        Column(modifier = Modifier.height(150.dp)) {
            TitleDescription(title = movie.title, description = movie.overview)
            GenreRating(genre = genres[0], voteAverage = movie.vote_average.toString())
        }
    }
}