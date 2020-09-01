package com.ng.tvshowsdb.shows.detail

import com.ng.tvshowsdb.core.ui.common.View
import com.ng.tvshowsdb.shows.list.ShowItem

interface ShowDetailView : View<ShowDetailPresenter> {

    fun showError(message: String)

    fun showDetails(show: ShowDetailsUiModel)

    fun showSimilarShows(shows: List<ShowItem>)

    fun navigateToShowDetails(position: Int, showId: Long)
}