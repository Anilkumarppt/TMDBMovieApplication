package com.example.moviesapplication.data.network.model

import com.google.gson.annotations.SerializedName

data class MovieDto(
    val page: Int,
    @SerializedName("results")
    val moviesList: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)
data class CastDto(
    val id: Int,
    val cast: List<Cast>
)
data class TrailerDto(
    val id: Int,
    val results: List<Trailer>
)
data class ProviderDto(
    @SerializedName("display_priority") val display_priority: String,
    @SerializedName("logo_path") val logo: String,
    @SerializedName("provider_id") val provider_id: Int,
    @SerializedName("provider_name") val name: String,
)