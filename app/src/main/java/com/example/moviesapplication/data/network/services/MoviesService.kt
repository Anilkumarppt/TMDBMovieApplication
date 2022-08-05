package com.example.moviesapplication.data.network.services

import com.example.moviesapplication.data.network.model.*
import com.example.moviesapplication.utils.AppConstants.CREDITS
import com.example.moviesapplication.utils.AppConstants.GET_POPULAR
import com.example.moviesapplication.utils.AppConstants.MOVIE
import com.example.moviesapplication.utils.AppConstants.PROVIDERS
import com.example.moviesapplication.utils.AppConstants.VIDEOS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET(GET_POPULAR)
    suspend fun getPopularMoviesList(@Query("page") page:Int):Response<MovieDto>
    @GET(MOVIE)
    suspend fun getMovieItem(@Path("movie_id") movieId:Int):Response<Movie>
    @GET(VIDEOS)
    suspend fun getTrailerVideo(@Path("id") movieId:Int):Response<TrailerDto>
    @GET(CREDITS)
    suspend  fun getMovieCasts(@Path("movie_id") movieId:Int):Response<CastDto>
    @GET(PROVIDERS)
    suspend fun getProviders(@Path("id") movieId:Int):Response<ProviderDto>
}