package com.example.sep.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

data class TabItem (
    val title: String,
    val screen: @Composable () -> Unit
)