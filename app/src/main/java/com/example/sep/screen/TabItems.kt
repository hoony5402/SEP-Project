package com.example.sep.screen

val tabs = listOf(
    TabItem(
        title = "Information",
        screen = {
            TabScreen(
                navController = it,
                type = "Information"
            )
        }
    ),
    TabItem(
        title = "Announcements",
        screen = {
            TabScreen(
                navController = it,
                type = "Announcements"
            )
        }
    ),
    TabItem(
        title = "Events",
        screen = {
            TabScreen(
                navController = it,
                type = "Events"
            )
        }
    )
)