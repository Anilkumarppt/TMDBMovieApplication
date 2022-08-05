package com.example.moviesapplication.data.repository



import com.example.moviesapplication.data.network.SafeApiRequest
import com.example.moviesapplication.data.network.model.*
import com.example.moviesapplication.data.network.services.MoviesService
import com.example.moviesapplication.utils.AppConstants
import com.example.moviesapplication.utils.AppConstants.thumbSize185
import com.example.moviesapplication.utils.AppConstants.thumbUrl
import com.example.moviesapplication.utils.NetworkConnectivityManager
import com.example.moviesapplication.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import retrofit2.Response


import javax.inject.Inject


class MoviesListRepository @Inject constructor(private val moviesService: MoviesService):MainRepository,SafeApiRequest(){


    override suspend fun getPopularMoviesList( page:Int): Resource<MovieDto> = try {

        val response=moviesService.getPopularMoviesList(page = page)
        val result= response.body()
        if(response.isSuccessful && result!=null)
            Resource.Success(result)
        else
            Resource.Error(response.errorBody().toString())
    } catch (e: Exception) {
        Resource.Error(e.message)
    }

    override suspend fun getMovieItem(id: Int): Flow<Movie> {

        return flow {  val movieData = moviesService.getMovieItem(movieId = id)
              if (movieData.isSuccessful){
                val movie:Movie=movieData.body()!!
                  movie.poster_path= thumbUrl + thumbSize185 +movie.poster_path
                  movie.backdrop_path= thumbUrl + thumbSize185 +movie.backdrop_path
                  emit(movie)
              }
              else
                  emptyFlow<Movie>()
          }

    }
    /* return try {
            val movie=moviesService.getMovieItem(movieId = id).body()!!
              Resource.Success(movie)
        }
        catch (ex:Exception){
            Resource.Error(ex.message)
        }*/


    override suspend fun getTrailer(movieId: Int): Resource<TrailerDto> =safeApiCall { moviesService.getTrailerVideo(movieId = movieId).body()!! }
    override suspend fun getCastsList(movieId: Int): Resource<CastDto> =
          safeApiCall { moviesService.getMovieCasts(movieId = movieId).body()!!}



    override suspend fun getProvidersList(movieId: Int): Resource<ProviderDto> {
        TODO("Not yet implemented")
    }

    //   return popularMoviesListService.getPopularMoviesList(1)


}