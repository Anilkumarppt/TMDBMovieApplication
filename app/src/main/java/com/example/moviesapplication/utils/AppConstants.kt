package com.example.moviesapplication.utils

object AppConstants {
    const val BASE_URL = "https://api.themoviedb.org/3/"

    // Get a list of the current popular movies on TMDB. This list updates daily.
    const val GET_POPULAR="movie/popular"

    // Get the primary information about a movie.
    const val MOVIE = "movie/{movie_id}"

    // Get the most newly created movie. This is a live response and will continuously change.
    const val GET_LATEST = "movie/latest"

    // Discover movies by different types of data like average rating or number of votes
    const val DISCOVER_MOVIES = "discover/movie"

    // Get the videos that have been added to a movie.
    const val VIDEOS = "movie/{id}/videos"

    // Powered by our partnership with JustWatch, you can query this method to get a list of the availabilities per country by provider.
    // Please note: In order to use this data you must attribute the source of the data as JustWatch. If we find any usage not complying with these terms we will revoke access to the API.
    const val PROVIDERS = "movie/{id}/watch/providers"

    // Get the cast and crew for a movie.
    const val CREDITS = "movie/{id}/credits"

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