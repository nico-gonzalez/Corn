package com.ng.tvshowsdb.shows.detail

import com.ng.tvshowsdb.core.domain.common.Result
import com.ng.tvshowsdb.shows.api.domain.model.TvShow
import com.ng.tvshowsdb.shows.api.domain.model.TvShows
import com.ng.tvshowsdb.shows.api.domain.usecase.GetSimilarTvShows
import com.ng.tvshowsdb.shows.api.domain.usecase.GetTvShow
import com.ng.tvshowsdb.shows.list.ShowUiModel
import com.ng.tvshowsdb.shows.list.ShowViewModelMapper
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

private const val SHOW_ID = 1L

class ShowDetailsPresenterTest {

    private lateinit var presenter: ShowDetailPresenter

    private val view: ShowDetailView = mockk(relaxUnitFun = true)
    private val getTvShow: GetTvShow = mockk()
    private val getSimilarTvShows: GetSimilarTvShows = mockk()
    private val tvShowViewModelMapper: ShowViewModelMapper = mockk()
    private val tvShowDetailsViewModelMapper: ShowDetailsViewModelMapper = mockk()
    private val tvShow: TvShow = mockk()
    private val tvShowDetailsUiModel: ShowDetailsUiModel = mockk()
    private val tvShowUiModel: ShowUiModel = mockk()

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

        every { getTvShow(SHOW_ID) } returns Single.just(showDetails)
        every { getSimilarTvShows(GetSimilarTvShows.Params(SHOW_ID, page = 1)) } returns
                Single.just(similarTvShows)
        every { tvShowDetailsViewModelMapper.map(tvShow) } returns tvShowDetailsUiModel
        every { tvShowViewModelMapper.map(tvShow) } returns tvShowUiModel

        presenter.onShowDetails()

        verifyOrder {
            getTvShow(SHOW_ID)
            tvShowDetailsViewModelMapper.map(tvShow)
            view.showDetails(tvShowDetailsUiModel)
        }
    }

    @Test
    fun `On show details loads show fails and hands error message to view`() {
        val showDetails = Result.error<TvShow>(Throwable("Error"))
        val similarTvShows =
            Result.success(TvShows(listOf(tvShow), currentPage = 1, totalPages = 5))

        every { getTvShow(SHOW_ID) } returns Single.just(showDetails)
        every { getSimilarTvShows(GetSimilarTvShows.Params(SHOW_ID, page = 1)) } returns
                Single.just(similarTvShows)
        every { tvShowDetailsViewModelMapper.map(tvShow) } returns tvShowDetailsUiModel
        every { tvShowViewModelMapper.map(tvShow) } returns tvShowUiModel

        presenter.onShowDetails()

        verifyOrder {
            getTvShow(SHOW_ID)
            view.showError(withArg { assertThat(it, `is`(equalTo("Error"))) })
            tvShowDetailsViewModelMapper wasNot Called
        }
    }

    @Test
    fun `On show details loads similar shows and hands view model to view`() {
        val showDetails = Result.success(tvShow)
        val similarTvShows =
            Result.success(TvShows(listOf(tvShow), currentPage = 1, totalPages = 5))

        every { getTvShow(SHOW_ID) } returns Single.just(showDetails)
        every { getSimilarTvShows(GetSimilarTvShows.Params(SHOW_ID, page = 1)) } returns
                Single.just(similarTvShows)
        every { tvShowDetailsViewModelMapper.map(tvShow) } returns tvShowDetailsUiModel
        every { tvShowViewModelMapper.map(tvShow) } returns tvShowUiModel

        presenter.onShowDetails()

        verifyOrder {
            getSimilarTvShows.invoke(withArg {
                assertThat(it.showId, `is`(equalTo(SHOW_ID)))
                assertThat(it.page, `is`(equalTo(1)))
            })
            tvShowViewModelMapper.map(tvShow)
            view.showSimilarShows(withArg {
                assertThat(it[0], `is`(equalTo(tvShowUiModel)))
            })
        }
    }

    @Test
    fun `On similar show selected navigates to show details`() {
        presenter.onSimilarShowSelected(1, SHOW_ID)

        verify { view.navigateToShowDetails(1, SHOW_ID) }
    }
}
