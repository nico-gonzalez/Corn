package com.ng.tvshowsdb.presentation.shows


sealed class TvShowItem {
    open val id: Long = -1
}

data class TvShowUiModel(
    override val id: Long,
    val title: String,
    val posterPath: String,
    val rating: String
) : TvShowItem()

object LoadingTvShowUiModel : TvShowItem()
