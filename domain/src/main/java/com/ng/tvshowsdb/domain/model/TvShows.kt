package com.ng.tvshowsdb.domain.model

data class TvShows(val shows: List<TvShow>, val currentPage: Int, val totalPages: Int = 0)