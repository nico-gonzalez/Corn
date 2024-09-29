package com.ng.tvshowsdb.shows.list

import com.ng.tvshowsdb.core.domain.common.Result
import com.ng.tvshowsdb.shows.api.domain.model.TvShow
import com.ng.tvshowsdb.shows.api.domain.model.TvShows
import com.ng.tvshowsdb.shows.api.domain.usecase.GetMostPopularTvShows
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import io.reactivex.Single
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

private const val SHOW_ID = 1L
private const val SIMILAR_SHOW_ID = 2L

class ShowsPresenterTest {

    private lateinit var presenter: ShowsPresenter

    private val view: ShowsView = mockk(relaxUnitFun = true)
    private val getTvShows: GetMostPopularTvShows = mockk()
    private val showViewModelMapper: ShowViewModelMapper = mockk()
    private val tvShow: TvShow = mockk {
        every { id } returns SHOW_ID
    }
    private val tvShowUiModel: ShowUiModel = mockk {
        every { id } returns SHOW_ID
    }

    @Before
    fun setup() {
        presenter = ShowsPresenter(view, getTvShows, showViewModelMapper)
    }

    @Test
    fun `On show most popular tv shows, gets popular tv shows and hand the view model to the view`() {
        val shows = Result.success(TvShows(listOf(tvShow), currentPage = 1, totalPages = 5))

        every { getTvShows(1) } returns Single.just(shows)
        every { showViewModelMapper.map(tvShow) } returns tvShowUiModel

        presenter.onShowMostPopularTvShows()

        verifyOrder {
            getTvShows.invoke(1)
            view.showLoading()
            view.hideLoading()
            showViewModelMapper.map(tvShow)

            view.showShows(match {
                it[0].id == SHOW_ID
            })
        }
    }

    @Test
    fun `On show most popular tv shows, gets popular tv shows fails hands error message to view`() {
        val result = Result.error<TvShows>(Throwable("Error"))

        every { getTvShows(1) } returns Single.just(result)

        presenter.onShowMostPopularTvShows()

        verifyOrder {
            getTvShows(1)
            view.showLoading()
            view.hideLoading()
            view.showError()
        }

        verify { showViewModelMapper wasNot Called }
    }

    @Test
    fun `On show more popular tv shows, Get second page of popular tv succeeds hands shows view model to view`() {
        val similarTvShow: TvShow = mockk {
            every { id } returns SIMILAR_SHOW_ID
        }
        val similarTvShowUiModel: ShowUiModel = mockk {
            every { id } returns SIMILAR_SHOW_ID
        }
        val firstPageShows =
            Result.success(TvShows(listOf(tvShow), currentPage = 1, totalPages = 5))
        val secondPageShows = Result.success(
            TvShows(listOf(similarTvShow), currentPage = 2, totalPages = 5)
        )

        every { getTvShows(1) } returns Single.just(firstPageShows)
        every { getTvShows(2) } returns Single.just(secondPageShows)
        every { showViewModelMapper.map(tvShow) } returns tvShowUiModel
        every { showViewModelMapper.map(similarTvShow) } returns similarTvShowUiModel

        presenter.onShowMostPopularTvShows()
        presenter.onShowMoreShows()

        verifyOrder {
            getTvShows(1)
            view.showLoading()
            view.hideLoading()
            showViewModelMapper.map(tvShow)

            view.showShows(match {
                it[0].id == SHOW_ID
            })

            getTvShows(2)
            showViewModelMapper.map(similarTvShow)

            view.showShows(withArg {
                assertThat(it[1].id, equalTo(SIMILAR_SHOW_ID))
            })
        }
    }

    @Test
    fun `On show more popular tv shows, get second page of popular tv shows fails hands error message to view`() {
        val firstPageShows =
            Result.success(TvShows(listOf(tvShow), currentPage = 1, totalPages = 5))
        val secondPageResult = Result.error<TvShows>(Throwable("Error"))

        every { getTvShows(1) } returns Single.just(firstPageShows)
        every { getTvShows(2) } returns Single.just(secondPageResult)
        every { showViewModelMapper.map(tvShow) } returns tvShowUiModel

        presenter.onShowMostPopularTvShows()
        presenter.onShowMoreShows()

        verifyOrder {
            getTvShows(1)
            view.showLoading()
            view.hideLoading()
            showViewModelMapper.map(tvShow)
            view.showShows(match {
                it[0].id == SHOW_ID
            })

            getTvShows(2)
            view.showError()
        }
    }
}