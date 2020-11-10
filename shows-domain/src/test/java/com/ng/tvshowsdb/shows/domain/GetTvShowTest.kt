package com.ng.tvshowsdb.shows.domain

import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import com.ng.tvshowsdb.shows.domain.common.ImmediateSchedulers
import com.ng.tvshowsdb.shows.domain.model.TvShow
import com.ng.tvshowsdb.shows.domain.repository.TvShowRepository
import com.ng.tvshowsdb.shows.domain.usecase.GetTvShow
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Test

private const val SHOW_ID = 1L

class GetTvShowTest {

    private lateinit var getTvShow: GetTvShow

    private val tvShowRepository: TvShowRepository = mock()
    private val schedulerProvider: SchedulerProvider = ImmediateSchedulers()
    private val tvShow: TvShow = mock()

    @Before
    fun setup() {
        getTvShow = GetTvShow(schedulerProvider, tvShowRepository)
    }

    @Test
    fun `Gets Show from Repository by it's Id`() {
        whenever(tvShowRepository.getShow(SHOW_ID)) doReturn Maybe.just(tvShow)

        getTvShow(SHOW_ID)
            .test()
            .apply {
                assertValue {
                    it.value == tvShow
                }
            }
        verify(tvShowRepository).getShow(SHOW_ID)
    }

    @Test
    fun `Gets Show and fails returning an error`() {
        val error: Throwable = mock()
        whenever(tvShowRepository.getShow(SHOW_ID)) doReturn Maybe.error(error)

        getTvShow(SHOW_ID)
            .test()
            .assertError(error)
        verify(tvShowRepository).getShow(SHOW_ID)
    }
}