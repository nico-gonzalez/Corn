package com.ng.tvshowsdb.shows.api.domain

import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import com.ng.tvshowsdb.shows.api.domain.common.ImmediateSchedulers
import com.ng.tvshowsdb.shows.api.domain.model.TvShow
import com.ng.tvshowsdb.shows.api.domain.model.TvShows
import com.ng.tvshowsdb.shows.api.domain.repository.TvShowRepository
import com.ng.tvshowsdb.shows.api.domain.usecase.GetSimilarTvShows
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

private const val SHOW_ID = 1L
private const val PAGE = 1

class GetSimilarTvShowsTest {

    private lateinit var getSimilarTvShows: GetSimilarTvShows

    private val tvShowRepository: TvShowRepository = mockk()
    private val schedulerProvider: SchedulerProvider = ImmediateSchedulers()
    private val tvShow: TvShow = mockk()

    @Before
    fun setup() {
        getSimilarTvShows = GetSimilarTvShows(schedulerProvider, tvShowRepository)
    }

    @Test
    fun `Gets similar Shows from Repository for given show and page`() {
        val shows = TvShows(listOf(tvShow, tvShow, tvShow), 1, 5)
        every { tvShowRepository.getSimilarTvShows(SHOW_ID, PAGE) } returns Single.just(shows)

        getSimilarTvShows(GetSimilarTvShows.Params(SHOW_ID, PAGE))
            .test()
            .apply {
                assertValue {
                    it.value == shows
                }
            }
        verify { tvShowRepository.getSimilarTvShows(SHOW_ID, PAGE) }
    }

    @Test
    fun `Gets similar shows and fails returning an error`() {
        val error: Throwable = mockk()
        every { tvShowRepository.getSimilarTvShows(SHOW_ID, PAGE) } returns Single.error(error)

        getSimilarTvShows(GetSimilarTvShows.Params(SHOW_ID, PAGE))
            .test()
            .apply {
                assertValue {
                    it.error == error
                }
            }
        verify { tvShowRepository.getSimilarTvShows(SHOW_ID, PAGE) }
    }
}