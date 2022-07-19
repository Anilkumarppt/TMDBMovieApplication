package com.example.moviesapplication.data.repository

import android.util.Log
import androidx.compose.material.rememberBottomSheetScaffoldState
import com.example.SpringJPAExample.entity.MovieDto
import com.example.moviesapplication.data.network.services.PopularMoviesListService
import com.example.moviesapplication.utils.Resource

import retrofit2.Response
import javax.inject.Inject


class MoviesListRepository @Inject constructor(private val popularMoviesListService: PopularMoviesListService):MainRepository{


    override suspend fun getPopularMoviesList(): Resource<MovieDto>? = try {
        val response=popularMoviesListService.getPopularMoviesList(1)
        val result= response.body()
        if(response.isSuccessful && result!=null)
            Resource.Success(result)
        else
            Resource.Error(response.errorBody().toString())
    } catch (e: Exception) {
        Resource.Error(e.message)
    }
    //   return popularMoviesListService.getPopularMoviesList(1)


}