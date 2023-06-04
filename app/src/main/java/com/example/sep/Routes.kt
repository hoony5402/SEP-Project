package com.example.sep

sealed class Routes(val route: String) {
    object Login : Routes("Login")
    object Register : Routes("Register")
    object Homepage : Routes("Homepage")
    object Map : Routes("Map")
    object Calendar : Routes("Calendar")
    object Menu : Routes("Menu")
    object WritePost : Routes("WritePost")
    object Post_Homepage : Routes("Post_Homepage")
    object Post_Calendar : Routes("Post_Calendar")

}