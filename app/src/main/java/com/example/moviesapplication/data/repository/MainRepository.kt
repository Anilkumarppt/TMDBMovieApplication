package com.example.moviesapplication.data.repository

import com.example.SpringJPAExample.entity.MovieDto
import com.example.moviesapplication.utils.Resource
import java.util.concurrent.Flow

interface MainRepository {
    suspend fun getPopularMoviesList(): Resource<MovieDto>?
}