package com.example.moviecompose.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviecompose.model.entities.Multi
import com.example.moviecompose.network.MovieDBApi
import com.example.moviecompose.ui.GenreRating
import com.example.moviecompose.ui.PosterImage
import com.example.moviecompose.ui.RetrySection
import com.example.moviecompose.ui.TitleDescription
import com.example.moviecompose.ui.navigation.NavScreen
import com.example.moviecompose.util.Constant

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {

    val searchList by remember {
        viewModel.searchedList
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
    val searchQuery by remember {
        viewModel.searchQuery
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
    ) {
        Column {
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                viewModel.searchQuery.value = it
                viewModel.search(it)
            }
            SearchSomethingText()
            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            if (errorMessage.isNotEmpty()) {
                RetrySection(error = errorMessage) {
                    viewModel.search(searchQuery)
                }
            }
            if (searchList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        modifier = Modifier.padding(20.dp),
                        text = "No Result found for query \"${searchQuery}\"",
                        style = TextStyle(color = Color.White, fontSize = 18.sp, textAlign = TextAlign.Center)
                    )
                }
            }
            if (errorMessage.isEmpty() && !isLoading && searchList.isNotEmpty()) {
                LazyColumn {
                    items(searchList.size) {
                        if (it >= searchList.size - 1 && !endReached && !isLoading) {
                            viewModel.loadMoreResult(searchQuery)
                        }
                        SearchListItem(
                            navController = navController,
                            mediaType = searchList[it].media_type,
                            multi = searchList[it]
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchSomethingText(viewModel: SearchViewModel = hiltViewModel()) {
    val searchQuery by remember {
        viewModel.searchQuery
    }
    if (searchQuery == "") {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Search For Movie or Series, and we'll track it down for you",
                style = TextStyle(color = Color.White, fontSize = 18.sp, textAlign = TextAlign.Center)
            )
        }
    }
}

@Composable
fun SearchListItem(navController: NavController, mediaType: String, multi: Multi) {
    val posterPath = MovieDBApi.getPosterPath(multi.poster_path)
    val genres = mutableListOf<String>()
    if (mediaType == "tv") {
        for (genre in Constant.SERIES_GENRE_LIST) {
            if (multi.genre_ids != null && multi.genre_ids.contains(genre.id)) {
                genres.add(genre.name)
            }
        }
    } else {
        for (genre in Constant.MOVIES_GENRE_LIST) {
            if (multi.genre_ids != null && multi.genre_ids.contains(genre.id)) {
                genres.add(genre.name)
            }
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
                if (mediaType == "tv") {
                    navController.navigate("${NavScreen.SeriesDetail.route}/${multi.id}")
                } else if (mediaType == "movie") {
                    navController.navigate("${NavScreen.MovieDetail.route}/${multi.id}")
                }
            }
    ) {
        PosterImage(posterPath = posterPath)
        Column(
            modifier = Modifier
                .height(150.dp)
                .padding(start = 10.dp)
        ) {
            val title: String = if (mediaType == "tv") {
                multi.name!!
            } else {
                multi.title!!
            }
            TitleDescription(title = title, description = multi.overview)
            if (genres.isNotEmpty()) {
                GenreRating(genre = genres[0], voteAverage = multi.vote_average)
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    viewModel: SearchViewModel = hiltViewModel(),
    onSearch: (String) -> Unit = {}
) {
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(
        modifier = modifier
            .padding(10.dp)
            .padding(top = 10.dp)
    ) {
        BasicTextField(
            value = viewModel.searchQuery.value,
            onValueChange = {
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(MaterialTheme.colors.background, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused && viewModel.searchQuery.value.isEmpty()
                }
        )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.White,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}