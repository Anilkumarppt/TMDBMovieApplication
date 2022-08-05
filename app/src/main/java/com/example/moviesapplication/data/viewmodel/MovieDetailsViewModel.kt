package com.example.moviesapplication.data.viewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.moviesapplication.data.network.model.Movie
import com.example.moviesapplication.data.repository.MoviesListRepository
import com.example.moviesapplication.utils.AppConstants.thumbSize154
import com.example.moviesapplication.utils.AppConstants.thumbSize185
import com.example.moviesapplication.utils.AppConstants.thumbUrl
import com.example.moviesapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor( private val moviesListRepository: MoviesListRepository):ViewModel() {

    sealed class MovieUiState {
        object Empty : MovieUiState()
        object Loading : MovieUiState()
        class Loaded(val data: Any) : MovieUiState()
        class Error(val message: String) : MovieUiState()
    }
    private val _movie= MutableStateFlow<MovieUiState>(MovieUiState.Empty)
    public var movie= emptyFlow<Movie>()


   
    
    fun getMovieById(movieId:Int?):Flow<Movie>{
        if (movieId!=null){
         viewModelScope.launch {
             moviesListRepository.getMovieItem(id = movieId).also {
                 movie = it
                 Log.d("Poster Image URL", "getMovieById: ")
             }
         }
        }
       return movie
    }

    
    /*fun getCast(movieId:Int?):Flow<Cast>{

    }
    fun getProviders(movieId:Int?):Flow<List<ProvidersByCountry>>{}*/
}