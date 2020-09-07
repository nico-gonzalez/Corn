package com.ng.tvshowsdb.shows.detail

import com.ng.tvshowsdb.core.ui.common.Presenter
import com.ng.tvshowsdb.shows.domain.usecase.GetSimilarTvShows
import com.ng.tvshowsdb.shows.domain.usecase.GetTvShow
import com.ng.tvshowsdb.shows.list.LoadingShowUiModel
import com.ng.tvshowsdb.shows.list.ShowItem
import com.ng.tvshowsdb.shows.list.ShowViewModelMapper
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject
import javax.inject.Named

class ShowDetailPresenter @Inject constructor(
    private val view: ShowDetailView,
    @Named("showId") private val showId: Long,
    private val getTvShow: GetTvShow,
    private val getSimilarTvShows: GetSimilarTvShows,
    private val showViewModelMapper: ShowViewModelMapper,
    private val showDetailsViewModelMapper: ShowDetailsViewModelMapper
) : Presenter() {

    private var currentSimilarShowsPage = 1
    private var totalSimilarShowsPages = 1
    private val similarTvShows = mutableListOf<ShowItem>()

    init {
        view.setPresenter(this)
    }

    fun onShowDetails() {
        getTvShow(showId)
            .doOnSuccess { result ->
                result.error?.let {
                    view.showError(it.localizedMessage.orEmpty())
                    return@doOnSuccess
                }

                result.value?.let { tvShow ->
                    val show = showDetailsViewModelMapper.map(tvShow)
                    view.showDetails(show)
                }
            }
            .subscribe()
            .addTo(subscriptions)

        getSimilarTvShows(GetSimilarTvShows.Params(showId, currentSimilarShowsPage))
            .doOnSuccess { result ->
                result.error?.let {
                    view.showError(it.localizedMessage.orEmpty())
                    return@doOnSuccess
                }
                result.value?.let {
                    currentSimilarShowsPage = it.currentPage
                    totalSimilarShowsPages = it.totalPages
                    val shows = it.shows.map(showViewModelMapper::map)
                    similarTvShows.clear()
                    similarTvShows += shows
                    view.showSimilarShows(similarTvShows)
                }
            }
            .subscribe()
            .addTo(subscriptions)
    }

    fun onShowMoreSimilarShows() {
        val isLoadingMore = similarTvShows.last() == LoadingShowUiModel
        if (currentSimilarShowsPage < totalSimilarShowsPages && !isLoadingMore) {
            getSimilarTvShows(GetSimilarTvShows.Params(showId, ++currentSimilarShowsPage))
                .doOnSuccess { result ->
                    similarTvShows.remove(LoadingShowUiModel)
                    view.showSimilarShows(similarTvShows)

                    result.error?.let {
                        view.showError(it.localizedMessage.orEmpty())
                        return@doOnSuccess
                    }

                    result.value?.let {
                        currentSimilarShowsPage = it.currentPage
                        totalSimilarShowsPages = it.totalPages
                        val shows = it.shows.map(showViewModelMapper::map)
                        similarTvShows += shows
                        view.showSimilarShows(similarTvShows)
                    }
                }
                .doOnSubscribe {
                    similarTvShows += LoadingShowUiModel
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