package com.example.moviesapplication.data


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesapplication.data.network.model.Movie
import com.example.moviesapplication.data.repository.MoviesListRepository
import com.example.moviesapplication.utils.AppConstants.thumbSize185
import com.example.moviesapplication.utils.AppConstants.thumbUrl
import retrofit2.HttpException
import javax.inject.Inject

class MoviePagingSource @Inject constructor(private val moviesListRepo:MoviesListRepository):
    PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {

        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val movieListResponse = moviesListRepo.getPopularMoviesList(nextPage)
            movieListResponse.data?.moviesList?.map {
                it.poster_path=thumbUrl+thumbSize185+it.poster_path
            }
            LoadResult.Page(
                data = movieListResponse.data!!.moviesList,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = movieListResponse.data.page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(Exception(e.localizedMessage))
        }
        catch (e:HttpException){
            LoadResult.Error(Exception(e.localizedMessage))
        }
    }
}