package com.example.moviecompose.ui.homeScreen.myListScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MyListScreen(
    navController: NavController
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        Text(
            text = "My List Not Implemented Yet",
            style = TextStyle(color = Color.White, fontSize = 20.sp)
        )
    }

}