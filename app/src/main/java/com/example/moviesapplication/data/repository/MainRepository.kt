package com.example.moviesapplication.data.repository


import com.example.moviesapplication.data.network.model.*
import com.example.moviesapplication.utils.Resource
import kotlinx.coroutines.flow.Flow


interface MainRepository {
    suspend fun getPopularMoviesList(page:Int): Resource<MovieDto>?
    suspend fun getMovieItem(id:Int):Flow<Movie>
    suspend fun getTrailer(movieId:Int):Resource<TrailerDto>
    suspend fun getCastsList(movieId: Int):Resource<CastDto>
    suspend fun getProvidersList(movieId: Int):Resource<ProviderDto>
}