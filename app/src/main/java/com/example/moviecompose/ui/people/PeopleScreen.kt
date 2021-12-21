package com.example.moviecompose.ui.people

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.moviecompose.model.network.People
import com.example.moviecompose.network.MovieDBApi
import com.example.moviecompose.ui.RetrySection

@ExperimentalFoundationApi
@Composable
fun PeopleScreen(
    viewModel: PeopleViewModel = hiltViewModel()
) {
    val searchQuery by remember {
        viewModel.searchQuery
    }
    val peopleList by remember {
        viewModel.searchPerson("")
    }
    val errorMessage by remember {
        viewModel.loadError
    }
    var isLoading by remember {
        viewModel.isLoading
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
    ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        if (errorMessage.isNotEmpty() && errorMessage != "HTTP 404 ") {
            RetrySection(error = errorMessage) {
                isLoading = true
                viewModel.getPeople()
            }
        } else {
            Column {
                SearchBar(
                    hint = "Search...",
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    viewModel.currentPage = 1
                    viewModel.searchQuery.value = it
                    viewModel.searchPerson(query = it)
                }
                LazyVerticalGrid(
                    cells = GridCells.Fixed(2),
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    itemsIndexed(peopleList) { index, people ->
                        if (index >= peopleList.size - 1) {
                            if (searchQuery == "") {
                                viewModel.loadMorePeople()
                            } else {
                                viewModel.loadMoreSearchResult(searchQuery)
                            }
                        }
                        PeopleList(people = people)
                    }
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun PeopleList(people: People) {
    Column(
        modifier = Modifier
            .padding(end = 10.dp, bottom = 10.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val painter =
            rememberImagePainter(data = MovieDBApi.getProfileImage(people.profile_path))
        Image(
            painter = painter,
            contentDescription = "People Image",
            modifier = Modifier
                .padding(top = 10.dp)
                .size(120.dp)
                .shadow(elevation = 5.dp, shape = RoundedCornerShape(120.dp))
                .clip(shape = RoundedCornerShape(120.dp)),
            contentScale = ContentScale.Crop
        )
        if (people.name != null) {
            Text(
                text = people.name,
                style = TextStyle(color = Color.White, fontSize = 15.sp),
                modifier = Modifier.padding(top = 5.dp)
            )
        }
        if (people.known_for_department != null) {
            Text(
                text = people.known_for_department,
                style = TextStyle(color = Color.White, fontSize = 14.sp),
                modifier = Modifier.padding(bottom = 5.dp)
            )
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    viewModel: PeopleViewModel = hiltViewModel(),
    onSearch: (String) -> Unit = {}
) {
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier.padding(10.dp).padding(top = 10.dp)) {
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