package com.example.moviesapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.moviesapplication.data.viewmodel.MoviesListViewModel
import com.example.moviesapplication.ui.screens.MainScreen
import com.example.moviesapplication.ui.theme.MoviesApplicationTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val moviesListView: MoviesListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesApplicationTheme {
                MainScreen(mainViewModel = moviesListView)
            }
        }
    }
}