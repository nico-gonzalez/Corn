package com.ng.tvshowsdb.shows.list

import com.ng.tvshowsdb.core.ui.common.Presenter
import com.ng.tvshowsdb.shows.domain.usecase.GetMostPopularTvShows
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class ShowsPresenter @Inject constructor(
    private val view: ShowsView,
    private val getTvShows: GetMostPopularTvShows,
    private val showViewModelMapper: ShowViewModelMapper
) : Presenter() {

    private var currentPage = 1
    private var totalShowsPages = 1
    private val tvShows = mutableListOf<ShowItem>()

    init {
        view.setPresenter(this)
    }

    fun onShowMostPopularTvShows() {
        getTvShows(1)
            .doOnSuccess {
                view.hideLoading()

                it.error?.let {
                    view.showError()
                    return@doOnSuccess
                }

                it.value?.let {
                    currentPage = it.currentPage
                    totalShowsPages = it.totalPages
                    val shows = it.shows.map(showViewModelMapper::map)
                    tvShows.clear()
                    tvShows += shows
                    view.showShows(tvShows)
                }

            }
            .doOnSubscribe { view.showLoading() }
            .subscribe()
            .addTo(subscriptions)
    }

    fun onShowMoreShows() {
        val isLoadingMore = tvShows.last() == LoadingShowUiModel
        if (currentPage < totalShowsPages && !isLoadingMore) {
            getTvShows(++currentPage)
                .doOnSuccess {
                    tvShows.remove(LoadingShowUiModel)
                    view.showShows(tvShows)

                    it.error?.let {
                        view.showError()
                        return@doOnSuccess
                    }

                    it.value?.let {
                        currentPage = it.currentPage
                        totalShowsPages = it.totalPages
                        val shows = it.shows.map(showViewModelMapper::map)
                        tvShows += shows
                        view.showShows(tvShows)
                    }

                }
                .doOnSubscribe {
                    tvShows += LoadingShowUiModel
                    view.showShows(tvShows)
                }
                .subscribe()
                .addTo(subscriptions)
        }
    }

    fun onShowSelected(position: Int, showId: Long) {
        view.navigateToShowDetails(position, showId)
    }
}