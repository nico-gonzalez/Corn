package com.ng.tvshowsdb.presentation.detail

import com.ng.tvshowsdb.domain.shows.GetSimilarTvShows
import com.ng.tvshowsdb.domain.shows.GetSimilarTvShows.Params
import com.ng.tvshowsdb.domain.shows.GetTvShow
import com.ng.tvshowsdb.presentation.common.Presenter
import com.ng.tvshowsdb.presentation.common.View
import com.ng.tvshowsdb.presentation.shows.LoadingTvShowUiModel
import com.ng.tvshowsdb.presentation.shows.TvShowItem
import com.ng.tvshowsdb.presentation.shows.TvShowViewModelMapper
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

interface ShowDetailView : View<TvShowDetailPresenter> {

    fun showError(message: String)

    fun showDetails(show: TvShowDetailsUiModel)

    fun showSimilarShows(shows: List<TvShowItem>)

    fun navigateToShowDetails(position: Int, showId: Long)
}

class TvShowDetailPresenter @Inject constructor(
    private val view: ShowDetailView,
    private val getTvShow: GetTvShow,
    private val getSimilarTvShows: GetSimilarTvShows,
    private val tvShowViewModelMapper: TvShowViewModelMapper,
    private val tvShowDetailsViewModelMapper: TvShowDetailsViewModelMapper
) : Presenter() {

    private var showId = -1L
    private var currentSimilarShowsPage = 1
    private var totalSimilarShowsPages = 1
    private val similarTvShows = mutableListOf<TvShowItem>()

    init {
        view.setPresenter(this)
    }

    fun onShowDetails(showId: Long) {
        this.showId = showId
        getTvShow.execute(showId)
            .doOnSuccess { result ->
                result.error?.let {
                    view.showError(it.localizedMessage)
                    return@doOnSuccess
                }

                result.value?.let { tvShow ->
                    val show = tvShowDetailsViewModelMapper.map(tvShow)
                    view.showDetails(show)
                }
            }
            .subscribe()
            .addTo(subscriptions)
        getSimilarTvShows.execute(Params(showId, currentSimilarShowsPage))
            .doOnSuccess { result ->
                result.error?.let {
                    view.showError(it.localizedMessage)
                    return@doOnSuccess
                }
                result.value?.let {
                    currentSimilarShowsPage = it.currentPage
                    totalSimilarShowsPages = it.totalPages
                    val shows = it.shows.map(tvShowViewModelMapper::map)
                    similarTvShows.clear()
                    similarTvShows += shows
                    view.showSimilarShows(similarTvShows)
                }
            }
            .subscribe()
            .addTo(subscriptions)
    }

    fun onShowMoreSimilarShows() {
        val isLoadingMore = similarTvShows.last() == LoadingTvShowUiModel
        if (currentSimilarShowsPage < totalSimilarShowsPages && !isLoadingMore) {
            getSimilarTvShows.execute(Params(showId, ++currentSimilarShowsPage))
                .doOnSuccess { result ->
                    similarTvShows.remove(LoadingTvShowUiModel)
                    view.showSimilarShows(similarTvShows)

                    result.error?.let {
                        view.showError(it.localizedMessage)
                        return@doOnSuccess
                    }

                    result.value?.let {
                        currentSimilarShowsPage = it.currentPage
                        totalSimilarShowsPages = it.totalPages
                        val shows = it.shows.map(tvShowViewModelMapper::map)
                        similarTvShows += shows
                        view.showSimilarShows(similarTvShows)
                    }

                }
                .doOnSubscribe {
                    similarTvShows += LoadingTvShowUiModel
                    view.showSimilarShows(similarTvShows)
                }
                .subscribe()
                .addTo(subscriptions)
        }
    }

    fun onSimilarShowSelected(position: Int, showId: Long) {
        view.navigateToShowDetails(position, showId)
    }
}