package com.ng.tvshowsdb.domain.shows

import com.ng.tvshowsdb.domain.common.ImmediateSchedulers
import com.ng.tvshowsdb.domain.common.Result
import com.ng.tvshowsdb.domain.common.SchedulerProvider
import com.ng.tvshowsdb.domain.model.TvShow
import com.ng.tvshowsdb.domain.model.TvShows
import com.ng.tvshowsdb.domain.repository.TvShowRepository
import com.ng.tvshowsdb.domain.shows.GetSimilarTvShows.Params
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test

private const val SHOW_ID = 1L
private const val PAGE = 1

class GetSimilarTvShowsTest {

  private lateinit var getSimilarTvShows: GetSimilarTvShows

  private val tvShowRepository: TvShowRepository = mock()
  private val schedulerProvider: SchedulerProvider = ImmediateSchedulers()
  private val tvShow: TvShow = mock()

  private val subscriber = TestSubscriber<Result<TvShows>>()

  @Before
  fun setup() {
    getSimilarTvShows = GetSimilarTvShows(schedulerProvider, tvShowRepository)
  }

  @Test
  fun `Gets similar Shows from Repository for given show and page`() {
    val shows = TvShows(listOf(tvShow, tvShow, tvShow), 1, 5)
    whenever(tvShowRepository.getSimilarTvShows(SHOW_ID, PAGE)) doReturn Flowable.just(shows)

    getSimilarTvShows.execute(Params(SHOW_ID, PAGE)).subscribe(subscriber)
    subscriber.assertValue {
      it.result == shows
    }
    verify(tvShowRepository).getSimilarTvShows(SHOW_ID, PAGE)
  }

  @Test
  fun `Gets similar shows and fails returning an error`() {
    val error: Throwable = mock()
    whenever(tvShowRepository.getSimilarTvShows(SHOW_ID, PAGE)) doReturn Flowable.error(error)

    getSimilarTvShows.execute(Params(SHOW_ID, PAGE)).subscribe(subscriber)
    subscriber.assertValue {
      it.error == error
    }
    verify(tvShowRepository).getSimilarTvShows(SHOW_ID, PAGE)
  }
}