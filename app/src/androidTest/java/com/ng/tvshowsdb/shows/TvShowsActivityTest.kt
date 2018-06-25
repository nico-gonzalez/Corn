package com.ng.tvshowsdb.shows

import android.support.test.runner.AndroidJUnit4
import com.ng.tvshowsdb.common.AndroidTest
import com.ng.tvshowsdb.common.buildTvShow
import com.ng.tvshowsdb.common.buildTvShows
import com.ng.tvshowsdb.domain.model.TvShows
import com.ng.tvshowsdb.domain.repository.TvShowRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class TvShowsActivityTest : AndroidTest() {

  @Inject
  lateinit var showsRepository: TvShowRepository

  private val tvShows = TvShows(buildTvShows(), currentPage = 1, totalPages = 5)
  private val tvShow = buildTvShow()
  private val similarTvShows = TvShows(buildTvShows(), currentPage = 1, totalPages = 5)

  @Before
  fun setup() {
    applicationComponent().inject(this)

    whenever(showsRepository.getMostPopularShows(anyInt())) doReturn Single.just(tvShows)
    whenever(showsRepository.getShow(eq(tvShow.id))) doReturn Maybe.just(tvShow)
    whenever(showsRepository.getSimilarTvShows(eq(tvShow.id), page = anyInt())) doReturn
        Single.just(similarTvShows)
  }

  @Test
  fun testShowListIsDisplayed() {
    tvShows {
      checkShowsAreDisplayed()
    }
  }

  @Test
  fun testOnShowClickedOpensShowDetails() {
    tvShows {
      selectShowAt(position = 0)
      checkShowDetailsAreOpened(showId = 1)
    }
  }
}