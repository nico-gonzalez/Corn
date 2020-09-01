package com.ng.tvshowsdb.shows.list

import com.ng.tvshowsdb.core.ui.common.View

interface ShowsView : View<ShowsPresenter> {

    fun showShows(shows: List<ShowItem>)

    fun showLoading()

    fun hideLoading()

    fun showError()

    fun navigateToShowDetails(position: Int, showId: Long)
}