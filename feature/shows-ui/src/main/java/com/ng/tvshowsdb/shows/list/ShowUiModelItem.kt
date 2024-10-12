package com.ng.tvshowsdb.shows.list


sealed class ShowItem {
    open val id: Long = -1
}

data class ShowUiModel(
    override val id: Long,
    val title: String,
    val posterPath: String,
    val rating: String
) : ShowItem()

data object LoadingShowUiModel : ShowItem()
