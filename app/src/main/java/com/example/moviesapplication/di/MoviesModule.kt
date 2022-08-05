package com.example.moviesapplication.di

import com.example.moviesapplication.data.network.services.MoviesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object MoviesModule {
    @Provides
    fun providesMovieService(retrofitBuilder: Retrofit.Builder):MoviesService{
        return retrofitBuilder.build().create(MoviesService::class.java)
    }
}