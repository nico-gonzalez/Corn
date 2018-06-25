package com.ng.tvshowsdb.domain.shows

import com.ng.tvshowsdb.domain.common.ImmediateSchedulers
import com.ng.tvshowsdb.domain.common.SchedulerProvider
import com.ng.tvshowsdb.domain.model.TvShow
import com.ng.tvshowsdb.domain.model.TvShows
import com.ng.tvshowsdb.domain.repository.TvShowRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

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

    getTvShows.execute(PAGE)
        .test()
        .apply {
          assertValue {
            it.result == shows
          }
        }
    verify(tvShowRepository).getMostPopularShows(PAGE)
  }

  @Test
  fun `Gets Shows and fails returning an error`() {
    val error: Throwable = mock()
    whenever(tvShowRepository.getMostPopularShows(PAGE)) doReturn Single.error(error)

    getTvShows.execute(PAGE)
        .test()
        .apply {
          assertValue {
            it.error == error
          }
        }
    verify(tvShowRepository).getMostPopularShows(PAGE)
  }
}