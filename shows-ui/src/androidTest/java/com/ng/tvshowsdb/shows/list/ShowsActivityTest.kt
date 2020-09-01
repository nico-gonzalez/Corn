package com.ng.tvshowsdb.shows.list

import androidx.test.runner.AndroidJUnit4
import com.ng.tvshowsdb.core.ui.testing.AndroidTest
import com.ng.tvshowsdb.core.ui.testing.injection.BaseApplicationComponent
import com.ng.tvshowsdb.core.ui.testing.injection.MockApplicationModule
import com.ng.tvshowsdb.shows.di.ShowsActivityBindingModule
import com.ng.tvshowsdb.shows.domain.model.TvShows
import com.ng.tvshowsdb.shows.domain.repository.TvShowRepository
import com.ng.tvshowsdb.shows.fixtures.buildTvShow
import com.ng.tvshowsdb.shows.fixtures.buildTvShows
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import javax.inject.Singleton

@RunWith(AndroidJUnit4::class)
class ShowsActivityTest : AndroidTest<ShowsActivityTest.ShowTestComponent>() {

    private val showsRepository: TvShowRepository = mock()
    private val tvShows = TvShows(buildTvShows(), currentPage = 1, totalPages = 5)
    private val tvShow = buildTvShow()
    private val similarTvShows = TvShows(buildTvShows(), currentPage = 1, totalPages = 5)

    @Singleton
    @Component(
        modules = [
            AndroidSupportInjectionModule::class,
            MockApplicationModule::class,
            ShowsActivityBindingModule::class
        ]
    )
    interface ShowTestComponent : BaseApplicationComponent {

        fun inject(showsActivityTest: ShowsActivityTest)

        @Component.Builder
        interface Builder {

            @BindsInstance
            fun repository(tvShowRepository: TvShowRepository): Builder

            fun build(): ShowTestComponent
        }
    }

    @Before
    fun setup() {
        val testComponent = DaggerShowsActivityTest_ShowTestComponent.builder()
            .repository(showsRepository)
            .build()
        application().testComponent = testComponent
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