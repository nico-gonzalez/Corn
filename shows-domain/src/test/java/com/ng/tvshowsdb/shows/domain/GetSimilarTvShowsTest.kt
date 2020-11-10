package com.ng.tvshowsdb.shows.domain

import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import com.ng.tvshowsdb.shows.domain.common.ImmediateSchedulers
import com.ng.tvshowsdb.shows.domain.model.TvShow
import com.ng.tvshowsdb.shows.domain.model.TvShows
import com.ng.tvshowsdb.shows.domain.repository.TvShowRepository
import com.ng.tvshowsdb.shows.domain.usecase.GetSimilarTvShows
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

private const val SHOW_ID = 1L
private const val PAGE = 1

class GetSimilarTvShowsTest {

    private lateinit var getSimilarTvShows: GetSimilarTvShows

    private val tvShowRepository: TvShowRepository = mock()
    private val schedulerProvider: SchedulerProvider = ImmediateSchedulers()
    private val tvShow: TvShow = mock()

    @Before
    fun setup() {
        getSimilarTvShows = GetSimilarTvShows(schedulerProvider, tvShowRepository)
    }

    @Test
    fun `Gets similar Shows from Repository for given show and page`() {
        val shows = TvShows(listOf(tvShow, tvShow, tvShow), 1, 5)
        whenever(tvShowRepository.getSimilarTvShows(SHOW_ID, PAGE)) doReturn Single.just(shows)

        getSimilarTvShows(GetSimilarTvShows.Params(SHOW_ID, PAGE))
            .test()
            .apply {
                assertValue {
                    it.value == shows
                }
            }
        verify(tvShowRepository).getSimilarTvShows(SHOW_ID, PAGE)
    }

    @Test
    fun `Gets similar shows and fails returning an error`() {
        val error: Throwable = mock()
        whenever(tvShowRepository.getSimilarTvShows(SHOW_ID, PAGE)) doReturn Single.error(error)

        getSimilarTvShows(GetSimilarTvShows.Params(SHOW_ID, PAGE))
            .test()
            .apply {
                assertValue {
                    it.error == error
                }
            }
        verify(tvShowRepository).getSimilarTvShows(SHOW_ID, PAGE)
    }
}