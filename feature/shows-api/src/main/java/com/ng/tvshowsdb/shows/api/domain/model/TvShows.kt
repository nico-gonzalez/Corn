package com.ng.tvshowsdb.shows.api.domain.model

data class TvShows(
    val shows: List<TvShow>,
    val currentPage: Int,
    val totalPages: Int
)