package com.ng.tvshowsdb.shows.details

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ng.tvshowsdb.core.ui.testing.AndroidTest
import com.ng.tvshowsdb.core.ui.testing.injection.MockApplicationModule
import com.ng.tvshowsdb.core.ui.testing.injection.TestApplicationComponent
import com.ng.tvshowsdb.shows.api.domain.model.TvShows
import com.ng.tvshowsdb.shows.api.domain.repository.TvShowRepository
import com.ng.tvshowsdb.shows.detail.ShowDetailsViewModelMapper
import com.ng.tvshowsdb.shows.di.MockRepositoryModule
import com.ng.tvshowsdb.shows.di.ShowsActivityBindingModule
import com.ng.tvshowsdb.shows.fixtures.buildTvShow
import com.ng.tvshowsdb.shows.fixtures.buildTvShows
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import io.mockk.every
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Singleton

@RunWith(AndroidJUnit4::class)
class ShowDetailsActivityTest : AndroidTest<ShowDetailsActivityTest.ShowDetailsTestComponent>() {

    @Inject
    internal lateinit var showsRepository: TvShowRepository

    @Inject
    lateinit var showDetailsViewModelMapper: ShowDetailsViewModelMapper

    private val tvShow = buildTvShow()
    private val tvShows = TvShows(buildTvShows(), 1, 5)
    private val tvShowViewModel by lazy { showDetailsViewModelMapper.map(tvShow) }

    @Singleton
    @Component(
        modules = [
            AndroidSupportInjectionModule::class,
            MockApplicationModule::class,
            MockRepositoryModule::class,
            ShowsActivityBindingModule::class
        ]
    )
    interface ShowDetailsTestComponent : TestApplicationComponent {

        fun inject(showDetailsActivityTest: ShowDetailsActivityTest)
    }

    @Before
    fun setup() {
        val testComponent = DaggerShowDetailsActivityTest_ShowDetailsTestComponent.builder()
            .build()
        application().testComponent = testComponent
        testComponent.inject(this)

        every { showsRepository.getShow(eq(tvShow.id)) } returns Maybe.just(tvShow)
        every { showsRepository.getSimilarTvShows(any(), page = any()) } returns
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
        every { showsRepository.getShow(eq(selectedShow.id)) } returns Maybe.just(selectedShow)

        showDetail(tvShowViewModel) {
            clickOnSimilarTvShowAt(1)
            checkShowDetailsAreOpened(selectedShow.id)
        }
    }
}