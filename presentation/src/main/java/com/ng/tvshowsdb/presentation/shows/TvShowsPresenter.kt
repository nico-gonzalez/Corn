package com.ng.tvshowsdb.presentation.shows

import com.ng.tvshowsdb.domain.common.extensions.addTo
import com.ng.tvshowsdb.domain.shows.GetMostPopularTvShows
import com.ng.tvshowsdb.presentation.common.Presenter
import com.ng.tvshowsdb.presentation.common.View

interface TvShowsView : View<TvShowsPresenter> {

  fun showShows(shows: List<TvShowViewModel>)

  fun showLoading()

  fun hideLoading()

  fun showError()

  fun showLoadingMoreShows()

  fun hideLoadingMoreShows()

  fun showMoreShows(shows: List<TvShowViewModel>)

  fun navigateToShowDetails(position: Int, showId: Long)
}

class TvShowsPresenter(
  private val view: TvShowsView,
  private val getTvShows: GetMostPopularTvShows,
  private val tvShowViewModelMapper: TvShowViewModelMapper
) : Presenter() {

  private var currentPage = 1
  private var totalShowsPages = 1

  init {
    view.setPresenter(this)
  }

  fun onShowMostPopularTvShows() {
    getTvShows.execute(1)
      .doOnNext { result ->
        view.hideLoading()

        result.error?.let {
          view.showError()
          return@doOnNext
        }

        result.result?.let {
          currentPage = it.currentPage
          totalShowsPages = it.totalPages
          val shows = it.shows.map(tvShowViewModelMapper::map)
          view.showShows(shows)
        }

      }
      .doOnSubscribe {
        view.showLoading()
      }
      .subscribe().addTo(disposables)
  }

  fun onShowMoreShows() {
    if (currentPage < totalShowsPages) {
      getTvShows.execute(++currentPage)
        .doOnNext { result ->
          view.hideLoadingMoreShows()

          result.error?.let {
            view.showError()
            return@doOnNext
          }

          result.result?.let {
            currentPage = it.currentPage
            totalShowsPages = it.totalPages
            val shows = it.shows.map(tvShowViewModelMapper::map)
            view.showMoreShows(shows)
          }

        }
        .doOnSubscribe {
          view.showLoadingMoreShows()
        }
        .subscribe().addTo(disposables)
    }
  }

  fun onShowSelected(position: Int, showId: Long) {
    view.navigateToShowDetails(position, showId)
  }
}