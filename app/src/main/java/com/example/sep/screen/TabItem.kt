package com.example.sep.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

data class TabItem (
    val title: String,
    val screen: @Composable (navController: NavController) -> Unit

)