package com.ng.tvshowsdb.shows.detail

data class ShowDetailsUiModel(
    val id: Long,
    val title: String,
    val description: String,
    val posterPath: String,
    val backdropPath: String,
    val firstAirDate: String,
    val rating: String
)