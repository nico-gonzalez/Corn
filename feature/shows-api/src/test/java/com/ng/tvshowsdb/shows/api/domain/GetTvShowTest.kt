package com.ng.tvshowsdb.shows.api.domain

import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import com.ng.tvshowsdb.shows.api.domain.common.ImmediateSchedulers
import com.ng.tvshowsdb.shows.api.domain.model.TvShow
import com.ng.tvshowsdb.shows.api.domain.repository.TvShowRepository
import com.ng.tvshowsdb.shows.api.domain.usecase.GetTvShow
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Test

private const val SHOW_ID = 1L

class GetTvShowTest {

    private lateinit var getTvShow: GetTvShow

    private val tvShowRepository: TvShowRepository = mockk()
    private val schedulerProvider: SchedulerProvider = ImmediateSchedulers()
    private val tvShow: TvShow = mockk()

    @Before
    fun setup() {
        getTvShow = GetTvShow(schedulerProvider, tvShowRepository)
    }

    @Test
    fun `Gets Show from Repository by it's Id`() {
        every { tvShowRepository.getShow(SHOW_ID) } returns Maybe.just(tvShow)

        getTvShow(SHOW_ID)
            .test()
            .apply {
                assertValue {
                    it.value == tvShow
                }
            }
        verify { tvShowRepository.getShow(SHOW_ID) }
    }

    @Test
    fun `Gets Show and fails returning an error`() {
        val error: Throwable = mockk()
        every { tvShowRepository.getShow(SHOW_ID) } returns Maybe.error(error)

        getTvShow(SHOW_ID)
            .test()
            .assertError(error)
        verify { tvShowRepository.getShow(SHOW_ID) }
    }
}