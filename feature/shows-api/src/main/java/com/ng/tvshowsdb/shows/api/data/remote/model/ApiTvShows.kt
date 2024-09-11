package com.ng.tvshowsdb.shows.api.data.remote.model

class ApiTvShow(
    val id: Long,
    val name: String?,
    val overview: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val vote_average: Double?,
    val first_air_date: String?
)

class ApiTvShows(
    val results: List<ApiTvShow>,
    val page: Int,
    val total_pages: Int
)
