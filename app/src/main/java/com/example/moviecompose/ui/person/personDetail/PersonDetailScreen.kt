package com.example.moviecompose.ui.person.personDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.moviecompose.R
import com.example.moviecompose.network.MovieDBApi
import com.example.moviecompose.ui.Chip
import com.example.moviecompose.ui.RetrySection
import com.example.moviecompose.ui.navigation.NavScreen

@Composable
fun PersonDetailScreen(
    navController: NavController,
    personId: Int,
    viewModel: PersonDetailViewModel = hiltViewModel()
) {

    val isLoading by remember {
        viewModel.isLoading
    }
    val loadingError by remember {
        viewModel.loadingError
    }
    val personDetails by remember {
        viewModel.getPersonDetails(personId = personId)
    }
    val movieCredit by remember {
        viewModel.getPersonMovieCredit(personId = personId)
    }
    val seriesCredit by remember {
        viewModel.getPersonSeriesCredit(personId = personId)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
    ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        if (loadingError.isNotEmpty()) {
            RetrySection(error = loadingError) {
                viewModel.getPersonDetails(personId = personId)
            }
        }
        if (loadingError.isEmpty() && !isLoading && personDetails != null) {
            LazyColumn {
                item {
                    val painter =
                        rememberImagePainter(data = MovieDBApi.getProfileImage(personDetails!!.profile_path))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .width(160.dp)
                                .height(240.dp)
                                .shadow(5.dp, RoundedCornerShape(10.dp))
                                .clip(RoundedCornerShape(10.dp)),
                            painter = painter,
                            contentDescription = "Poster Path"
                        )
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = personDetails!!.name,
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
                item {
                    Chip(
                        text = personDetails!!.place_of_birth,
                        fontSize = 15.sp,
                        imageResId = R.drawable.ic_location
                    )
                }
                item {
                    Chip(
                        modifier = Modifier.padding(top = 10.dp),
                        text = personDetails!!.birthday,
                        fontSize = 15.sp,
                        imageResId = R.drawable.ic_born
                    )
                }
                item {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = personDetails!!.biography,
                        style = TextStyle(color = Color.White, fontSize = 16.sp)
                    )
                }
                item {
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = "Movies Known For",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    LazyRow(modifier = Modifier.padding(top = 10.dp, end = 10.dp, bottom = 10.dp)) {
                        items(movieCredit.size) {
                            KnownForPoster(
                                id = movieCredit[it].id,
                                posterImage = movieCredit[it].poster_path!!,
                                type = "movie",
                                navController = navController
                            )
                        }
                    }
                }
                item {
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = "Series Known For",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    LazyRow(modifier = Modifier.padding(top = 10.dp, end = 10.dp, bottom = 10.dp)) {
                        items(seriesCredit.size) {
                            KnownForPoster(
                                id = seriesCredit[it].id,
                                posterImage = seriesCredit[it].poster_path!!,
                                type = "tv",
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KnownForPoster(id: Int, posterImage: String, type: String, navController: NavController) {
    val painter = rememberImagePainter(
        data = MovieDBApi.getPosterPath("/$posterImage")
    )
    Image(
        painter = painter,
        contentDescription = "Image",
        modifier = Modifier
            .width(150.dp)
            .height(200.dp)
            .padding(start = 10.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colors.background)
            .clickable {
                if (type == "movie") {
                    navController.navigate("${NavScreen.MovieDetail.route}/${id}")
                } else {
                    navController.navigate("${NavScreen.SeriesDetail.route}/${id}")
                }
            },
        contentScale = ContentScale.FillWidth
    )
}
