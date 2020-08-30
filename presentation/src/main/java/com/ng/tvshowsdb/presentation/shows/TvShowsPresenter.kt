package com.ng.tvshowsdb.presentation.shows

import com.ng.tvshowsdb.domain.shows.GetMostPopularTvShows
import com.ng.tvshowsdb.presentation.common.Presenter
import com.ng.tvshowsdb.presentation.common.View
import javax.inject.Inject

interface TvShowsView : View<TvShowsPresenter> {

    fun showShows(shows: List<TvShowItem>)

    fun showLoading()

    fun hideLoading()

    fun showError()

    fun navigateToShowDetails(position: Int, showId: Long)
}

class TvShowsPresenter @Inject constructor(
    private val view: TvShowsView,
    private val getTvShows: GetMostPopularTvShows,
    private val tvShowViewModelMapper: TvShowViewModelMapper
) : Presenter() {

    private var currentPage = 1
    private var totalShowsPages = 1
    private val tvShows = mutableListOf<TvShowItem>()

    init {
        view.setPresenter(this)
    }

    fun onShowMostPopularTvShows() {
        subscriptions.add(
            getTvShows.execute(1)
                .doOnSuccess {
                    view.hideLoading()

                    it.error?.let {
                        view.showError()
                        return@doOnSuccess
                    }

                    it.value?.let {
                        currentPage = it.currentPage
                        totalShowsPages = it.totalPages
                        val shows = it.shows.map(tvShowViewModelMapper::map)
                        tvShows.clear()
                        tvShows += shows
                        view.showShows(tvShows)
                    }

                }
                .doOnSubscribe { view.showLoading() }
                .subscribe()
        )
    }

    fun onShowMoreShows() {
        val isLoadingMore = tvShows.last() == LoadingTvShowUiModel
        if (currentPage < totalShowsPages && !isLoadingMore) {
            subscriptions.add(
                getTvShows.execute(++currentPage)
                    .doOnSuccess {
                        tvShows.remove(LoadingTvShowUiModel)
                        view.showShows(tvShows)

                        it.error?.let {
                            view.showError()
                            return@doOnSuccess
                        }

                        it.value?.let {
                            currentPage = it.currentPage
                            totalShowsPages = it.totalPages
                            val shows = it.shows.map(tvShowViewModelMapper::map)
                            tvShows += shows
                            view.showShows(tvShows)
                        }

                    }
                    .doOnSubscribe {
                        tvShows += LoadingTvShowUiModel
                        view.showShows(tvShows)
                    }
                    .subscribe()
            )
        }
    }

    fun onShowSelected(position: Int, showId: Long) {
        view.navigateToShowDetails(position, showId)
    }
}