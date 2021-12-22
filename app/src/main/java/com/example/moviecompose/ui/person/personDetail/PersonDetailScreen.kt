package com.example.moviecompose.ui.person.personDetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviecompose.model.network.KnownFor
import com.example.moviecompose.ui.person.PersonViewModel
import com.google.gson.Gson

@ExperimentalFoundationApi
@Composable
fun PersonDetailScreen(
    navController: NavController,
    personId: Int,
    personKnownFor: String,
    viewModel: PersonViewModel = hiltViewModel()
) {

    val knownFor = Gson().fromJson(personKnownFor, Array<KnownFor>::class.java).toList()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
    ) {
        LazyColumn {
            item {
                Text(text = knownFor.toString(), style = TextStyle(color = Color.White))
            }
        }
    }
}
