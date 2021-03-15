package com.ng.tvshowsdb.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import com.ng.tvshowsdb.shows.domain.model.TvShow
import com.ng.tvshowsdb.shows.domain.usecase.GetLatestTvShow
import com.ng.tvshowsdb.shows.domain.usecase.GetMostPopularTvShows
import com.ng.tvshowsdb.shows.domain.usecase.GetTopRatedTvShows
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    schedulerProvider: SchedulerProvider,
    getMostPopularTvShows: GetMostPopularTvShows,
    getTopRatedTvShows: GetTopRatedTvShows,
    getLatestTvShow: GetLatestTvShow,
) : ViewModel() {

    private val subscriptions = CompositeDisposable()

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    init {
        Single.zip(
            getMostPopularTvShows(params = 1),
            getTopRatedTvShows(params = 1),
            getLatestTvShow(Unit)
        ) { popular, topRated, latestShow ->
            ViewState.TvShows(
                mostPopular = popular.value?.shows.orEmpty(),
                topRated = topRated.value?.shows.orEmpty(),
                latest = latestShow.value
            )
        }
            .observeOn(schedulerProvider.ui())
            .doOnSuccess(::handleGetMostPopularTvShowsSuccess)
            .doOnError(::handleError)
            .doOnSubscribe { showLoading() }
            .subscribe()
            .addTo(subscriptions)
    }

    private fun showLoading() {
        _viewState.value = ViewState.Loading
    }

    private fun handleError(throwable: Throwable) {
        _viewState.value = ViewState.GetTvShowsError(throwable)
    }

    private fun handleGetMostPopularTvShowsSuccess(tvShows: ViewState.TvShows) {
        _viewState.value = tvShows
    }

    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class TvShows(
            val mostPopular: List<TvShow>,
            val topRated: List<TvShow>,
            val latest: TvShow? = null,
        ) : ViewState()

        data class GetTvShowsError(val error: Throwable) : ViewState()
    }

}
