package com.example.moviesapplication.ui.screens



sealed class MovieAppScreen(val route:String){
    object MovieListScreen:MovieAppScreen(route = "movie_list")
    object DetailsScreen:MovieAppScreen(route = "detail_screen")

    fun withNavArgs( vararg args:Int):String{
        return buildString {
            append(route)
            args.forEach {  arg->
                append("/$arg")
            }
        }
    }
}