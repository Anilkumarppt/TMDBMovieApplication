package com.example.moviesapplication.di

import com.example.moviesapplication.data.network.services.PopularMoviesListService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import java.lang.StringBuilder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesModule {
    @Provides
    fun providesMovieService(retrofitBuilder: Retrofit.Builder):PopularMoviesListService{
        return retrofitBuilder.build().create(PopularMoviesListService::class.java)
    }
}