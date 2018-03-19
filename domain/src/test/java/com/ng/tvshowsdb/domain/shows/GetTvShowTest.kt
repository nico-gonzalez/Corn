package com.ng.tvshowsdb.domain.shows

import com.ng.tvshowsdb.domain.common.ImmediateSchedulers
import com.ng.tvshowsdb.domain.common.Result
import com.ng.tvshowsdb.domain.common.SchedulerProvider
import com.ng.tvshowsdb.domain.model.TvShow
import com.ng.tvshowsdb.domain.repository.TvShowRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test

private const val SHOW_ID = 1L

class GetTvShowTest {

  private lateinit var getTvShow: GetTvShow

  private val tvShowRepository: TvShowRepository = mock()
  private val schedulerProvider: SchedulerProvider = ImmediateSchedulers()
  private val tvShow: TvShow = mock()

  private val subscriber = TestSubscriber<Result<TvShow>>()

  @Before
  fun setup() {
    getTvShow = GetTvShow(schedulerProvider, tvShowRepository)
  }

  @Test
  fun `Gets Show from Repository by it's Id`() {
    whenever(tvShowRepository.getShow(SHOW_ID)) doReturn Flowable.just(tvShow)

    getTvShow.execute(SHOW_ID).subscribe(subscriber)
    subscriber.assertValue {
      it.result == tvShow
    }
    verify(tvShowRepository).getShow(SHOW_ID)
  }

  @Test
  fun `Gets Show and fails returning an error`() {
    val error: Throwable = mock()
    whenever(tvShowRepository.getShow(SHOW_ID)) doReturn Flowable.error(error)

    getTvShow.execute(SHOW_ID).subscribe(subscriber)
    subscriber.assertValue {
      it.error == error
    }
    verify(tvShowRepository).getShow(SHOW_ID)
  }
}