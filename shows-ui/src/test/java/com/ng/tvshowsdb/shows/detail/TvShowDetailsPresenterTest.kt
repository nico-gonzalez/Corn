package com.ng.tvshowsdb.shows.detail

import com.ng.tvshowsdb.core.domain.common.Result
import com.ng.tvshowsdb.shows.domain.model.TvShow
import com.ng.tvshowsdb.shows.domain.model.TvShows
import com.ng.tvshowsdb.shows.domain.usecase.GetSimilarTvShows
import com.ng.tvshowsdb.shows.domain.usecase.GetTvShow
import com.ng.tvshowsdb.shows.list.ShowUiModel
import com.ng.tvshowsdb.shows.list.ShowViewModelMapper
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

private const val SHOW_ID = 1L

class TvShowDetailsPresenterTest {

    private lateinit var presenter: ShowDetailPresenter

    private val view: ShowDetailView = mock()
    private val getTvShow: GetTvShow = mock()
    private val getSimilarTvShows: GetSimilarTvShows = mock()
    private val tvShowViewModelMapper: ShowViewModelMapper = mock()
    private val tvShowDetailsViewModelMapper: ShowDetailsViewModelMapper = mock()
    private val tvShow: TvShow = mock()
    private val tvShowDetailsUiModel: ShowDetailsUiModel = mock()
    private val tvShowUiModel: ShowUiModel = mock()

    @Before
    fun setup() {
        presenter = ShowDetailPresenter(
            view,
            SHOW_ID,
            getTvShow,
            getSimilarTvShows,
            tvShowViewModelMapper,
            tvShowDetailsViewModelMapper
        )
    }

    @Test
    fun `On show details loads show and hands view model to view`() {
        val showDetails = Result.success(tvShow)
        val similarTvShows =
            Result.success(TvShows(listOf(tvShow), currentPage = 1, totalPages = 5))

        whenever(getTvShow(SHOW_ID)) doReturn Single.just(showDetails)
        whenever(getSimilarTvShows(GetSimilarTvShows.Params(SHOW_ID, page = 1))) doReturn
                Single.just(similarTvShows)
        whenever(tvShowDetailsViewModelMapper.map(tvShow)) doReturn tvShowDetailsUiModel

        presenter.onShowDetails()

        inOrder(view, getTvShow, getSimilarTvShows, tvShowDetailsViewModelMapper) {
            verify(getTvShow).invoke(SHOW_ID)
            verify(tvShowDetailsViewModelMapper).map(tvShow)
            verify(view).showDetails(tvShowDetailsUiModel)
        }
    }

    @Test
    fun `On show details loads show fails and hands error message to view`() {
        val showDetails = Result.error<TvShow>(Throwable("Error"))
        val similarTvShows =
            Result.success(TvShows(listOf(tvShow), currentPage = 1, totalPages = 5))

        whenever(getTvShow(SHOW_ID)) doReturn Single.just(showDetails)
        whenever(getSimilarTvShows(GetSimilarTvShows.Params(SHOW_ID, page = 1))) doReturn
                Single.just(similarTvShows)
        whenever(tvShowDetailsViewModelMapper.map(tvShow)) doReturn tvShowDetailsUiModel

        presenter.onShowDetails()

        inOrder(view, getTvShow, getSimilarTvShows, tvShowDetailsViewModelMapper) {
            verify(getTvShow).invoke(SHOW_ID)
            verify(view).showError(argThat {
                this == "Error"
            })
            verifyZeroInteractions(tvShowDetailsViewModelMapper)
        }
    }

    @Test
    fun `On show details loads similar shows and hands view model to view`() {
        val showDetails = Result.success(tvShow)
        val similarTvShows =
            Result.success(TvShows(listOf(tvShow), currentPage = 1, totalPages = 5))

        whenever(getTvShow(SHOW_ID)) doReturn Single.just(showDetails)
        whenever(getSimilarTvShows(GetSimilarTvShows.Params(SHOW_ID, page = 1))) doReturn
                Single.just(similarTvShows)
        whenever(tvShowViewModelMapper.map(tvShow)) doReturn tvShowUiModel

        presenter.onShowDetails()

        inOrder(view, getSimilarTvShows, tvShowViewModelMapper) {
            verify(getSimilarTvShows).invoke(check {
                assertThat(it.showId, `is`(equalTo(SHOW_ID)))
                assertThat(it.page, `is`(equalTo(1)))
            })
            verify(tvShowViewModelMapper).map(tvShow)
            verify(view).showSimilarShows(argThat {
                this[0] == tvShowUiModel
            })
        }
    }

    @Test
    fun `On similar show selected navigates to show details`() {
        presenter.onSimilarShowSelected(1, SHOW_ID)

        verify(view).navigateToShowDetails(1, SHOW_ID)
    }
}