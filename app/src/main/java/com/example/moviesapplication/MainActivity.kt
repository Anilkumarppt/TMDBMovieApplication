package com.example.moviesapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.moviesapplication.data.network.model.Movie
import com.example.moviesapplication.data.viewmodel.MoviesListViewModel
import com.example.moviesapplication.ui.screens.MainScreen
import com.example.moviesapplication.ui.screens.MovieItemCard
import com.example.moviesapplication.ui.screens.ScaffoldWithTopBar
import com.example.moviesapplication.ui.theme.MoviesApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val moviesListView:MoviesListViewModel by viewModels()
    private  val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoviesApplicationTheme {
                // A surface container using the 'background' color from the theme

                    MainScreen(mainViewModel = moviesListView)

            }
        }
    }
}
@Composable
fun MovieScree(moviesListViewModel: MoviesListViewModel){
    val lazyMovieList:LazyPagingItems<Movie> = moviesListViewModel.movies.collectAsLazyPagingItems()
    Log.d("Main", "MovieScree: ${lazyMovieList.itemSnapshotList.size}")
    LazyColumn(){
        items(lazyMovieList){   movie->
                MovieItemCard(movie = movie!!, onMovieItemClicked ={} , modifier =Modifier.fillMaxWidth() )
        }
    }
}
@Composable
fun MoviesListScreen(moviesListViewModel: MoviesListViewModel){
   val state=moviesListViewModel.popularMoviesList.collectAsState(initial = MoviesListViewModel.MoviesEvent.Loading).value
    when(state){
        is MoviesListViewModel.MoviesEvent.Loading->{
            Log.d("MainActivity", "MoviesListScreen:  Loading")
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            }
        is MoviesListViewModel.MoviesEvent.Success->{
            LazyColumn{
                items(state.moviesList){movie->
                    Log.d("MainActivity", "MoviesListScreen: ${movie.title}")
                    MovieItemCard(movie = movie, onMovieItemClicked ={movieId->
                        //Toast.makeText(LocalContext.current,"Selected Id is ${movieId}",Toast.LENGTH_LONG).show()
                        Log.d("TAG", "MoviesListScreen: ${movieId}")
                    } , modifier =Modifier.fillMaxWidth() )
                    /*MovieItemCard(movie = movie, onMovieItemClicked = {}, modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                        .fillMaxWidth())*/
                }
            }
        }
        is MoviesListViewModel.MoviesEvent.Failure->{}
        is MoviesListViewModel.MoviesEvent.Empty->{}
    }
    //LazyColumn(content = moviesListView.)
}
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MoviesApplicationTheme {
        Greeting("Android")
        Column(Modifier.fillMaxWidth()) {

        }
    }
}