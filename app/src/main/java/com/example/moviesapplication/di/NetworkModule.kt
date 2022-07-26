package com.example.moviesapplication.di

import com.example.moviesapplication.BuildConfig
import com.example.moviesapplication.data.network.interceptor.AuthenticationInterceptor
import com.example.moviesapplication.utils.AppConstants.BASE_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Dns
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient,gsonConverterFactory: GsonConverterFactory): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
    }
    @Singleton
    @Provides
    fun provideGsonFactory():GsonConverterFactory{
        return GsonConverterFactory.create(GsonBuilder().create())
    }
    @Singleton
    @Provides
    fun provideOkHttpClient(authenticateInterceptor: AuthenticationInterceptor): OkHttpClient {
        val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        else
            logging.setLevel(HttpLoggingInterceptor.Level.NONE)
        return OkHttpClient().newBuilder()
            .dns(Dns.SYSTEM)
            .connectTimeout(60,TimeUnit.SECONDS)
            .addInterceptor(interceptor = authenticateInterceptor)
               /*TODO Comment below line until complete Project. Below Line prints the HTTPS Logs.*/
            //.addInterceptor(logging)
            .build()
    }
}