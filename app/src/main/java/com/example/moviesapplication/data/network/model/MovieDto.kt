package com.example.SpringJPAExample.entity

import com.example.moviesapplication.data.network.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieDto(
    val page: Int,
    @SerializedName("results")
    val moviesList: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)