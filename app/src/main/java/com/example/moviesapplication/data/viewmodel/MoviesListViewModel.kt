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
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviesapplication.data.MoviePagingSource
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
    val popularMoviesList:Flow<MoviesEvent> =_popularMoviesList

    val movies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 20)) {
        MoviePagingSource(moviesListRepository)
    }.flow

   /* fun getPopularMoviesList(){
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
    }*/

}
