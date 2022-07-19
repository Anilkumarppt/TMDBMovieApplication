package com.example.moviesapplication.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviesapplication.data.network.model.Movie
import com.example.moviesapplication.data.repository.MoviesListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.moviesapplication.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class MoviesListViewModel @Inject constructor(private val moviesListRepository: MoviesListRepository):ViewModel(){

    sealed class MoviesEvent{
        class Success(val moviesList:List<Movie>):MoviesEvent()
        class Failure(val errorText:String):MoviesEvent()
        object Loading:MoviesEvent()
        object Empty:MoviesEvent()
    }
    private val _popularMoviesList=MutableStateFlow<MoviesEvent>(MoviesEvent.Empty)
    val popularMoviesList:StateFlow<MoviesEvent> =_popularMoviesList

    fun getPopularMoviesList(){
    _popularMoviesList.value=MoviesEvent.Loading
        viewModelScope.launch(Dispatchers.IO) {
         when(val result=moviesListRepository.getPopularMoviesList()){
             is Resource.Error->
                 _popularMoviesList.value=MoviesEvent.Failure(result.message!!)
             is Resource.Success->{
                 val movies=result.data!!
                 _popularMoviesList.value=MoviesEvent.Success(movies.moviesList)
             }
         }
        }
    }

}
