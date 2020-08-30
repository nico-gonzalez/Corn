package com.ng.tvshowsdb.details

import androidx.test.runner.AndroidJUnit4
import com.ng.tvshowsdb.common.AndroidTest
import com.ng.tvshowsdb.common.buildTvShow
import com.ng.tvshowsdb.common.buildTvShows
import com.ng.tvshowsdb.domain.model.TvShows
import com.ng.tvshowsdb.domain.repository.TvShowRepository
import com.ng.tvshowsdb.presentation.detail.TvShowDetailsViewModelMapper
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyLong
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class ShowDetailsActivityTest : AndroidTest() {

    @Inject
    lateinit var showsRepository: TvShowRepository

    @Inject
    lateinit var showDetailsViewModelMapper: TvShowDetailsViewModelMapper

    private val tvShow = buildTvShow()
    private val tvShows = TvShows(buildTvShows(), 1, 5)
    private val tvShowViewModel by lazy { showDetailsViewModelMapper.map(tvShow) }

    @Before
    fun setup() {
        applicationComponent().inject(this)

        whenever(showsRepository.getShow(eq(tvShow.id))) doReturn Maybe.just(tvShow)
        whenever(showsRepository.getSimilarTvShows(anyLong(), page = anyInt())) doReturn
                Single.just(tvShows)
    }

    @Test
    fun testShowDetailsIsDisplayed() {
        showDetail(tvShowViewModel) {
            checkShowDetailsAreDisplayed()
        }
    }

    @Test
    fun testWhenSimilarShowClickedOpenDetails() {
        val selectedShow = tvShows.shows[1]
        whenever(showsRepository.getShow(eq(selectedShow.id))) doReturn Maybe.just(selectedShow)

        showDetail(tvShowViewModel) {
            clickOnSimilarTvShowAt(1)
            checkShowDetailsAreOpened(selectedShow.id)
        }
    }
}