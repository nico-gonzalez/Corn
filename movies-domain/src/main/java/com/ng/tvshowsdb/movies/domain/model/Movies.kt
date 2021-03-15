package com.ng.tvshowsdb.movies.domain.model

data class Movies(
    val movies: List<Movie>,
    val currentPage: Int,
    val totalPages: Int
)
