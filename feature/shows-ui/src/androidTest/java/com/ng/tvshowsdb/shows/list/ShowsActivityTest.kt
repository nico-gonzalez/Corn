package com.ng.tvshowsdb.shows.list

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ng.tvshowsdb.core.ui.testing.AndroidTest
import com.ng.tvshowsdb.core.ui.testing.injection.MockApplicationModule
import com.ng.tvshowsdb.core.ui.testing.injection.TestApplicationComponent
import com.ng.tvshowsdb.shows.di.MockRepositoryModule
import com.ng.tvshowsdb.shows.di.ShowsActivityBindingModule
import com.ng.tvshowsdb.shows.api.domain.model.TvShows
import com.ng.tvshowsdb.shows.api.domain.repository.TvShowRepository
import com.ng.tvshowsdb.shows.fixtures.buildTvShow
import com.ng.tvshowsdb.shows.fixtures.buildTvShows
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.whenever
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import javax.inject.Inject
import javax.inject.Singleton

@RunWith(AndroidJUnit4::class)
class ShowsActivityTest : AndroidTest<ShowsActivityTest.ShowTestComponent>() {

    @Inject
    internal lateinit var showsRepository: TvShowRepository

    private val tvShows = TvShows(buildTvShows(), currentPage = 1, totalPages = 5)
    private val tvShow = buildTvShow()
    private val similarTvShows = TvShows(buildTvShows(), currentPage = 1, totalPages = 5)

    @Singleton
    @Component(
        modules = [
            AndroidSupportInjectionModule::class,
            MockApplicationModule::class,
            MockRepositoryModule::class,
            ShowsActivityBindingModule::class
        ]
    )
    interface ShowTestComponent : TestApplicationComponent {

        fun inject(showsActivityTest: ShowsActivityTest)

    }

    @Before
    fun setup() {
        val testComponent = DaggerShowsActivityTest_ShowTestComponent.builder()
            .build()
        application().testComponent = testComponent
        testComponent.inject(this)

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