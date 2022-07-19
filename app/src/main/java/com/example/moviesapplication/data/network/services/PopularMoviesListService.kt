package com.example.moviesapplication.data.network.services

import com.example.SpringJPAExample.entity.MovieDto
import com.example.moviesapplication.utils.AppConstants.GET_POPULAR
import com.example.moviesapplication.utils.Resource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.Flow

interface PopularMoviesListService {

    @GET(GET_POPULAR)
    suspend fun getPopularMoviesList(@Query("page") page:Int):Response<MovieDto>
}