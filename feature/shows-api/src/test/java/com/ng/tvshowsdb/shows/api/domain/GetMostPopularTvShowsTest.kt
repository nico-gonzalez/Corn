package com.ng.tvshowsdb.shows.api.domain

import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import com.ng.tvshowsdb.shows.api.domain.common.ImmediateSchedulers
import com.ng.tvshowsdb.shows.api.domain.model.TvShow
import com.ng.tvshowsdb.shows.api.domain.model.TvShows
import com.ng.tvshowsdb.shows.api.domain.repository.TvShowRepository
import com.ng.tvshowsdb.shows.api.domain.usecase.GetMostPopularTvShows
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

private const val PAGE = 1

class GetMostPopularTvShowsTest {

    private lateinit var getTvShows: GetMostPopularTvShows

    private val tvShowRepository: TvShowRepository = mock()
    private val schedulerProvider: SchedulerProvider = ImmediateSchedulers()
    private val tvShow: TvShow = mock()

    @Before
    fun setup() {
        getTvShows = GetMostPopularTvShows(schedulerProvider, tvShowRepository)
    }

    @Test
    fun `Gets Shows from Repository for given page`() {
        val shows = TvShows(listOf(tvShow, tvShow, tvShow), 1, 5)
        whenever(tvShowRepository.getMostPopularShows(PAGE)) doReturn Single.just(shows)

        getTvShows(PAGE)
            .test()
            .apply {
                assertValue {
                    it.value == shows
                }
            }
        verify(tvShowRepository).getMostPopularShows(PAGE)
    }

    @Test
    fun `Gets Shows and fails returning an error`() {
        val error: Throwable = mock()
        whenever(tvShowRepository.getMostPopularShows(PAGE)) doReturn Single.error(error)

        getTvShows(PAGE)
            .test()
            .apply {
                assertValue {
                    it.error == error
                }
            }
        verify(tvShowRepository).getMostPopularShows(PAGE)
    }
}