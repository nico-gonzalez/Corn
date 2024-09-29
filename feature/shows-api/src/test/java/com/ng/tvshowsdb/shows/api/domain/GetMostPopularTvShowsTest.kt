package com.ng.tvshowsdb.shows.api.domain

import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import com.ng.tvshowsdb.shows.api.domain.common.ImmediateSchedulers
import com.ng.tvshowsdb.shows.api.domain.model.TvShow
import com.ng.tvshowsdb.shows.api.domain.model.TvShows
import com.ng.tvshowsdb.shows.api.domain.repository.TvShowRepository
import com.ng.tvshowsdb.shows.api.domain.usecase.GetMostPopularTvShows
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

private const val PAGE = 1

class GetMostPopularTvShowsTest {

    private lateinit var getTvShows: GetMostPopularTvShows

    private val tvShowRepository: TvShowRepository = mockk()
    private val schedulerProvider: SchedulerProvider = ImmediateSchedulers()
    private val tvShow: TvShow = mockk()

    @Before
    fun setup() {
        getTvShows = GetMostPopularTvShows(schedulerProvider, tvShowRepository)
    }

    @Test
    fun `Gets Shows from Repository for given page`() {
        val shows = TvShows(listOf(tvShow, tvShow, tvShow), 1, 5)
        every { tvShowRepository.getMostPopularShows(PAGE) } returns Single.just(shows)

        getTvShows(PAGE)
            .test()
            .apply {
                assertValue {
                    it.value == shows
                }
            }
        verify { tvShowRepository.getMostPopularShows(PAGE) }
    }

    @Test
    fun `Gets Shows and fails returning an error`() {
        val error: Throwable = mockk()
        every { tvShowRepository.getMostPopularShows(PAGE) } returns Single.error(error)

        getTvShows(PAGE)
            .test()
            .apply {
                assertValue {
                    it.error == error
                }
            }
        verify { tvShowRepository.getMostPopularShows(PAGE) }
    }
}