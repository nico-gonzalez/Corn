package com.ng.tvshowsdb.presentation.details

import com.ng.tvshowsdb.domain.common.Result
import com.ng.tvshowsdb.domain.model.TvShow
import com.ng.tvshowsdb.domain.model.TvShows
import com.ng.tvshowsdb.domain.shows.GetSimilarTvShows
import com.ng.tvshowsdb.domain.shows.GetSimilarTvShows.Params
import com.ng.tvshowsdb.domain.shows.GetTvShow
import com.ng.tvshowsdb.presentation.detail.ShowDetailView
import com.ng.tvshowsdb.presentation.detail.TvShowDetailPresenter
import com.ng.tvshowsdb.presentation.detail.TvShowDetailsViewModel
import com.ng.tvshowsdb.presentation.detail.TvShowDetailsViewModelMapper
import com.ng.tvshowsdb.presentation.shows.TvShowViewModel
import com.ng.tvshowsdb.presentation.shows.TvShowViewModelMapper
import com.nhaarman.mockito_kotlin.argThat
import com.nhaarman.mockito_kotlin.check
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.inOrder
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

private const val SHOW_ID = 1L

class TvShowDetailsPresenterTest {

  private lateinit var presenter: TvShowDetailPresenter

  private val view: ShowDetailView = mock()
  private val getTvShow: GetTvShow = mock()
  private val getSimilarTvShows: GetSimilarTvShows = mock()
  private val tvShowViewModelMapper: TvShowViewModelMapper = mock()
  private val tvShowDetailsViewModelMapper: TvShowDetailsViewModelMapper = mock()
  private val tvShow: TvShow = mock()
  private val tvShowDetailsViewModel: TvShowDetailsViewModel = mock()
  private val tvShowViewModel: TvShowViewModel = mock()

  @Before
  fun setup() {
    presenter = TvShowDetailPresenter(view, getTvShow, getSimilarTvShows, tvShowViewModelMapper,
        tvShowDetailsViewModelMapper)
  }

  @Test
  fun `On show details loads show and hands view model to view`() {
    val showDetails = Result.success(tvShow)
    val similarTvShows = Result.success(TvShows(listOf(tvShow), currentPage = 1, totalPages = 5))

    whenever(getTvShow.execute(SHOW_ID)) doReturn Flowable.just(showDetails)
    whenever(getSimilarTvShows.execute(Params(SHOW_ID, page = 1))) doReturn
        Flowable.just(similarTvShows)
    whenever(tvShowDetailsViewModelMapper.map(tvShow)) doReturn tvShowDetailsViewModel

    presenter.onShowDetails(SHOW_ID)

    inOrder(view, getTvShow, getSimilarTvShows, tvShowDetailsViewModelMapper) {
      verify(getTvShow).execute(SHOW_ID)
      verify(tvShowDetailsViewModelMapper).map(tvShow)
      verify(view).showDetails(tvShowDetailsViewModel)
    }
  }

  @Test
  fun `On show details loads show fails and hands error message to view`() {
    val showDetails = Result.error<TvShow>(Throwable("Error"))
    val similarTvShows = Result.success(TvShows(listOf(tvShow), currentPage = 1, totalPages = 5))

    whenever(getTvShow.execute(SHOW_ID)) doReturn Flowable.just(showDetails)
    whenever(getSimilarTvShows.execute(Params(SHOW_ID, page = 1))) doReturn
        Flowable.just(similarTvShows)
    whenever(tvShowDetailsViewModelMapper.map(tvShow)) doReturn tvShowDetailsViewModel

    presenter.onShowDetails(SHOW_ID)

    inOrder(view, getTvShow, getSimilarTvShows, tvShowDetailsViewModelMapper) {
      verify(getTvShow).execute(SHOW_ID)
      verify(view).showError(argThat {
        this == "Error"
      })
      verifyZeroInteractions(tvShowDetailsViewModelMapper)
    }
  }

  @Test
  fun `On show details loads similar shows and hands view model to view`() {
    val showDetails = Result.success(tvShow)
    val similarTvShows = Result.success(TvShows(listOf(tvShow), currentPage = 1, totalPages = 5))

    whenever(getTvShow.execute(SHOW_ID)) doReturn Flowable.just(showDetails)
    whenever(getSimilarTvShows.execute(Params(SHOW_ID, page = 1))) doReturn
        Flowable.just(similarTvShows)
    whenever(tvShowViewModelMapper.map(tvShow)) doReturn tvShowViewModel

    presenter.onShowDetails(SHOW_ID)

    inOrder(view, getSimilarTvShows, tvShowViewModelMapper) {
      verify(getSimilarTvShows).execute(check {
        assertThat(it.showId, `is`(equalTo(SHOW_ID)))
        assertThat(it.page, `is`(equalTo(1)))
      })
      verify(tvShowViewModelMapper).map(tvShow)
      verify(view).showSimilarShows(argThat {
        this[0] == tvShowViewModel
      })
    }
  }

  @Test
  fun `On similar show selected navigates to show details`() {
    presenter.onSimilarShowSelected(1, SHOW_ID)

    verify(view).navigateToShowDetails(1, SHOW_ID)
  }
}