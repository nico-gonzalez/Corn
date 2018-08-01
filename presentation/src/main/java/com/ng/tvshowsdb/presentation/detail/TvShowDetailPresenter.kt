package com.ng.tvshowsdb.presentation.detail

import com.ng.tvshowsdb.domain.common.extensions.addTo
import com.ng.tvshowsdb.domain.shows.GetSimilarTvShows
import com.ng.tvshowsdb.domain.shows.GetSimilarTvShows.Params
import com.ng.tvshowsdb.domain.shows.GetTvShow
import com.ng.tvshowsdb.presentation.common.Presenter
import com.ng.tvshowsdb.presentation.common.View
import com.ng.tvshowsdb.presentation.shows.TvShowViewModel
import com.ng.tvshowsdb.presentation.shows.TvShowViewModelMapper

interface ShowDetailView : View<TvShowDetailPresenter> {

  fun showError(message: String)

  fun showDetails(show: TvShowDetailsViewModel)

  fun showSimilarShows(shows: List<TvShowViewModel>)

  fun navigateToShowDetails(position: Int, showId: Long)

  fun showLoadingMoreSimilarShows()

  fun hideLoadingMoreSimilarShows()

  fun showMoreSimilarShows(shows: List<TvShowViewModel>)
}

class TvShowDetailPresenter(
  private val view: ShowDetailView,
  private val getTvShow: GetTvShow,
  private val getSimilarTvShows: GetSimilarTvShows,
  private val tvShowViewModelMapper: TvShowViewModelMapper,
  private val tvShowDetailsViewModelMapper: TvShowDetailsViewModelMapper
) : Presenter() {

  private var showId = -1L
  private var currentSimilarShowsPage = 1
  private var totalSimilarShowsPages = 1

  init {
    view.setPresenter(this)
  }

  fun onShowDetails(showId: Long) {
    this.showId = showId
    getTvShow.execute(showId)
      .doOnNext { result ->

        result.error?.let {
          view.showError(it.localizedMessage)
          return@doOnNext
        }

        result.result?.let {
          val shows = tvShowDetailsViewModelMapper.map(it)
          view.showDetails(shows)
        }

      }
      .subscribe().addTo(disposables)

    getSimilarTvShows.execute(Params(showId, currentSimilarShowsPage))
      .doOnNext { result ->

        result.error?.let {
          view.showError(it.localizedMessage)
          return@doOnNext
        }

        result.result?.let {
          currentSimilarShowsPage = it.currentPage
          totalSimilarShowsPages = it.totalPages
          val shows = it.shows.map(tvShowViewModelMapper::map)
          view.showSimilarShows(shows)
        }

      }
      .subscribe().addTo(disposables)
  }

  fun onShowMoreSimilarShows() {
    if (currentSimilarShowsPage < totalSimilarShowsPages) {
      getSimilarTvShows.execute(Params(showId, ++currentSimilarShowsPage))
        .doOnNext { result ->
          view.hideLoadingMoreSimilarShows()

          result.error?.let {
            view.showError(it.localizedMessage)
            return@doOnNext
          }

          result.result?.let {
            currentSimilarShowsPage = it.currentPage
            totalSimilarShowsPages = it.totalPages
            val shows = it.shows.map(tvShowViewModelMapper::map)
            view.showMoreSimilarShows(shows)
          }

        }
        .doOnSubscribe {
          view.showLoadingMoreSimilarShows()
        }
        .subscribe().addTo(disposables)
    }
  }

  fun onSimilarShowSelected(position: Int, showId: Long) {
    view.navigateToShowDetails(position, showId)
  }
}