package com.example.moviesapplication.utils

object AppConstants {
    const val BASE_URL = "https://api.themoviedb.org/3/"

    // Get a list of the current popular movies on TMDB. This list updates daily.
    const val GET_POPULAR="movie/popular"

    // must be added in every call
    const val API_PARAM = "api_key"
    // API key for themovieDB.org
    // Go to the themoviedb.org, request an API key and paste it here
        const val API_KEY = "25e34d6e0c1879dffdee9ef13dc4186d"

    // to load the poster thumbs thumbUrl + thumbSize + posterUrl
    // url for loading the movie poster thumbs
    const val thumbUrl = "https://image.tmdb.org/t/p/"
    // different sizes available for these poster thumbs
    const val thumbSize154 = "w154"
    const val thumbSize185 = "w185" // recommended
    const val thumbSize500 = "w500"


}