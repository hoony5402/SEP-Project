package com.example.sep.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

val tabs = listOf(
    TabItem(
        title = "Information",
        screen = {
            TabScreen(
                content = CardObject()
            )
        }
    ),
    TabItem(
        title = "Announcements",
        screen = {
            TabScreen(
                content = CardObject()
            )
        }
    ),
    TabItem(
        title = "Events",
        screen = {
            TabScreen(
                content = CardObject()
            )
        }
    )
)