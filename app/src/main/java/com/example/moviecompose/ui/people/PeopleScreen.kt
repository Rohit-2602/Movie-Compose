package com.example.moviecompose.ui.people

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun PeopleScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Text(text = "People Screen", style = TextStyle(color = Color.White, fontSize = 20.sp))
    }
}