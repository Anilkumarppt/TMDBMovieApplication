package com.example.moviesapplication.ui.screens

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesapplication.data.viewmodel.MoviesListViewModel


@Composable
fun MovieApp() {
    val navController: NavHostController = rememberNavController()
    val moviesListView: MoviesListViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = MovieAppScreen.MovieListScreen.route
    ) {
        composable(route = MovieAppScreen.MovieListScreen.route) {
            MainScreen(navController = navController, mainViewModel = moviesListView)
        }
        composable(
            route = MovieAppScreen.DetailsScreen.route + "/{movie_id}", arguments = listOf(
                navArgument("movie_id") {
                    type = NavType.IntType
                    nullable = false
                })
        ) {entry->
            SampleDetails(entry.arguments?.getInt("movie_id"),navController)
        }
    }
}