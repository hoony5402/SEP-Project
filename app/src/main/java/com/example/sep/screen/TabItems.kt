package com.example.sep.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place

val tabs = listOf(
    TabItem(
        title = "Account",
        screen = { TabScreen(content = "Account Page") }
    ),
    TabItem(
        title = "Favorite",
        screen = { TabScreen(content = "Favorite list")}
    ),
    TabItem(
        title = "Place",
        screen = { TabScreen(content = "Places")}
    )
)