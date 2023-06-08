package com.example.sep.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

data class TabItem (
    val title: String,
    val screen: @Composable (navController: NavController) -> Unit

)