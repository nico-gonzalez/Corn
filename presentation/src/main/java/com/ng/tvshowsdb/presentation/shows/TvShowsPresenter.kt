package com.ng.tvshowsdb.presentation.shows

import com.ng.tvshowsdb.domain.shows.GetMostPopularTvShows
import com.ng.tvshowsdb.presentation.common.Presenter
import com.ng.tvshowsdb.presentation.common.View

interface TvShowsView : View<TvShowsPresenter> {

  fun showShows(shows: List<TvShowViewModel>)

  fun showLoading()

  fun hideLoading()

  fun showError(message: String)

  fun showLoadingMoreShows()

  fun hideLoadingMoreShows()

  fun showMoreShows(shows: List<TvShowViewModel>)

  fun navigateToShowDetails(position: Int, showId: Long)
}

class TvShowsPresenter(private val view: TvShowsView,
    private val getTvShows: GetMostPopularTvShows,
    private val tvShowViewModelMapper: TvShowViewModelMapper) : Presenter() {

  private var currentPage = 1
  private var totalShowsPages = 1

  init {
    view.setPresenter(this)
  }

  fun onShowMostPopularTvShows() {
    subscriptions.add(
        getTvShows.execute(1)
            .doOnNext {
              view.hideLoading()

              it.error?.let {
                view.showError(it.localizedMessage)
                return@doOnNext
              }

              it.result?.let {
                currentPage = it.currentPage
                totalShowsPages = it.totalPages
                val shows = it.shows.map(tvShowViewModelMapper::map)
                view.showShows(shows)
              }

            }
            .doOnSubscribe {
              view.showLoading()
            }
            .subscribe()
    )
  }

  fun onShowMoreShows() {
    if (currentPage < totalShowsPages) {
      subscriptions.add(
          getTvShows.execute(++currentPage)
              .doOnNext {
                view.hideLoadingMoreShows()

                it.error?.let {
                  view.showError(it.localizedMessage)
                  return@doOnNext
                }

                it.result?.let {
                  currentPage = it.currentPage
                  totalShowsPages = it.totalPages
                  val shows = it.shows.map(tvShowViewModelMapper::map)
                  view.showMoreShows(shows)
                }

              }
              .doOnSubscribe {
                view.showLoadingMoreShows()
              }
              .subscribe()
      )
    }
  }

  fun onShowSelected(position: Int, showId: Long) {
    view.navigateToShowDetails(position, showId)
  }
}