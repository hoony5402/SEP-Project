package com.example.sep.screen

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sep.MainActivity

var postnumber = "-1"

val tabs = listOf(
    TabItem(
        title = "Information",
        screen = {
            TabScreen(
                content = CardObject(),
                navController = it,
                type = "Information"
            )
        }
    ),
    TabItem(
        title = "Announcements",
        screen = {
            TabScreen(
                content = CardObject(),
                navController = it,
                type = "Information"
            )
        }
    ),
    TabItem(
        title = "Events",
        screen = {
            TabScreen(
                content = CardObject(),
                navController = it,
                type = "Events"
            )
        }
    )
)