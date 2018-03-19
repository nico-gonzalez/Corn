package com.ng.tvshowsdb.domain.model

data class TvShow(val id: Long, val title: String, val description: String, val posterPath: String,
    val backdropPath: String, val firstAirDate: String, val rating: Double)