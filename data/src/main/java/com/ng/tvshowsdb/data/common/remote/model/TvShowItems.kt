package com.ng.tvshowsdb.data.common.remote.model

class TvShowItem(val id: Long, val name: String?, val overview: String?, val poster_path: String?,
    val backdrop_path: String?, val vote_average: Double?, val first_air_date: String?)

class TvShowItems(val results: List<TvShowItem>, val page: Int, val total_pages: Int)
