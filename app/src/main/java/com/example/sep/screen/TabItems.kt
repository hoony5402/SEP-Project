package com.example.sep.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place

val tabs = listOf(
    TabItem(
        title = "Information",
        screen = { TabScreen(content = "Information") }
    ),
    TabItem(
        title = "Announcements",
        screen = { TabScreen(content = "Announcements")}
    ),
    TabItem(
        title = "Events",
        screen = { TabScreen(content = "Events")}
    )
)