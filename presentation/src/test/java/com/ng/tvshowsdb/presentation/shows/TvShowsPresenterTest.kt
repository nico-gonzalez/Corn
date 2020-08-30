package com.ng.tvshowsdb.presentation.shows

import com.ng.tvshowsdb.domain.common.Result
import com.ng.tvshowsdb.domain.model.TvShow
import com.ng.tvshowsdb.domain.model.TvShows
import com.ng.tvshowsdb.domain.shows.GetMostPopularTvShows
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

private const val SHOW_ID = 1L
private const val SIMILAR_SHOW_ID = 2L

class TvShowsPresenterTest {

    private lateinit var presenter: TvShowsPresenter

    private val view: TvShowsView = mock()
    private val getTvShows: GetMostPopularTvShows = mock()
    private val tvShowViewModelMapper: TvShowViewModelMapper = mock()
    private val tvShow: TvShow = mock {
        on { id } doReturn SHOW_ID
    }
    private val tvShowUiModel: TvShowUiModel = mock {
        on { id } doReturn SHOW_ID
    }

    @Before
    fun setup() {
        presenter = TvShowsPresenter(view, getTvShows, tvShowViewModelMapper)
    }

    @Test
    fun `On show most popular tv shows, gets popular tv shows and hand the view model to the view`() {
        val shows = Result.success(TvShows(listOf(tvShow), currentPage = 1, totalPages = 5))

        whenever(getTvShows.execute(1)) doReturn Single.just(shows)
        whenever(tvShowViewModelMapper.map(tvShow)) doReturn tvShowUiModel

        presenter.onShowMostPopularTvShows()

        inOrder(view, getTvShows, tvShowViewModelMapper) {
            verify(getTvShows).execute(1)
            verify(view).showLoading()
            verify(view).hideLoading()
            verify(tvShowViewModelMapper).map(tvShow)
            verify(view).showShows(argThat {
                this[0].id == SHOW_ID
            })
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `On show most popular tv shows, gets popular tv shows fails hands error message to view`() {
        val result = Result.error<TvShows>(Throwable("Error"))

        whenever(getTvShows.execute(1)) doReturn Single.just(result)

        presenter.onShowMostPopularTvShows()

        inOrder(view, getTvShows) {
            verify(getTvShows).execute(1)
            verify(view).showLoading()
            verify(view).hideLoading()
            verify(view).showError()
            verifyNoMoreInteractions()
        }

        verifyZeroInteractions(tvShowViewModelMapper)
    }

    @Test
    fun `On show more popular tv shows, Get second page of popular tv succeeds hands shows view model to view`() {
        val similarTvShow: TvShow = mock {
            on { id } doReturn SIMILAR_SHOW_ID
        }
        val similarTvShowUiModel: TvShowUiModel = mock {
            on { id } doReturn SIMILAR_SHOW_ID
        }
        val firstPageShows =
            Result.success(TvShows(listOf(tvShow), currentPage = 1, totalPages = 5))
        val secondPageShows = Result.success(
            TvShows(listOf(similarTvShow), currentPage = 2, totalPages = 5)
        )

        whenever(getTvShows.execute(1)) doReturn Single.just(firstPageShows)
        whenever(getTvShows.execute(2)) doReturn Single.just(secondPageShows)
        whenever(tvShowViewModelMapper.map(tvShow)) doReturn tvShowUiModel
        whenever(tvShowViewModelMapper.map(similarTvShow)) doReturn similarTvShowUiModel

        presenter.onShowMostPopularTvShows()
        presenter.onShowMoreShows()

        inOrder(view, getTvShows, tvShowViewModelMapper) {
            verify(getTvShows).execute(1)
            verify(view).showLoading()
            verify(view).hideLoading()
            verify(tvShowViewModelMapper).map(tvShow)
            verify(view).showShows(argThat {
                this[0].id == SHOW_ID
            })

            verify(getTvShows).execute(2)
            verify(tvShowViewModelMapper).map(similarTvShow)
            verify(view).showShows(argThat {
                this[1].id == SIMILAR_SHOW_ID
            })
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `On show more popular tv shows, get second page of popular tv shows fails hands error message to view`() {
        val firstPageShows =
            Result.success(TvShows(listOf(tvShow), currentPage = 1, totalPages = 5))
        val secondPageResult = Result.error<TvShows>(Throwable("Error"))

        whenever(getTvShows.execute(1)) doReturn Single.just(firstPageShows)
        whenever(getTvShows.execute(2)) doReturn Single.just(secondPageResult)
        whenever(tvShowViewModelMapper.map(tvShow)) doReturn tvShowUiModel

        presenter.onShowMostPopularTvShows()
        presenter.onShowMoreShows()

        inOrder(view, getTvShows, tvShowViewModelMapper) {
            verify(getTvShows).execute(1)
            verify(view).showLoading()
            verify(view).hideLoading()
            verify(tvShowViewModelMapper).map(tvShow)
            verify(view).showShows(argThat {
                this[0].id == SHOW_ID
            })


            verify(getTvShows).execute(2)
            verify(view).showError()
            verifyNoMoreInteractions()
        }
    }

}