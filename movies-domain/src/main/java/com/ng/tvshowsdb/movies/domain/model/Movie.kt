package com.ng.tvshowsdb.movies.domain.model

data class Movie(
    val id: Long,
    val title: String?,
    val description: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val rating: Double,
)
