package com.example.sep

sealed class Routes(val route: String) {
    object Login : Routes("Login")
    object Register : Routes("Register")
    object Homepage : Routes("Homepage")

}