package com.ng.tvshowsdb.shows.domain.model

data class TvShows(
    val shows: List<TvShow>,
    val currentPage: Int,
    val totalPages: Int
)