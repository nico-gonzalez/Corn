package com.ng.tvshowsdb.shows.data

import com.ng.tvshowsdb.shows.data.remote.ShowsService
import com.ng.tvshowsdb.shows.data.remote.model.ApiTvShow
import com.ng.tvshowsdb.shows.data.remote.model.ApiTvShows
import com.ng.tvshowsdb.shows.data.shows.TvShowMapper
import com.ng.tvshowsdb.shows.data.shows.TvShowRepositoryImpl
import com.ng.tvshowsdb.shows.domain.model.TvShow
import com.ng.tvshowsdb.shows.domain.model.TvShows
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

private const val FIRST_PAGE = 1
private const val TOTAL_PAGES = 5
private const val FIRST_SHOW_ID = 1L
private const val SECOND_SHOW_ID = 2L
private const val THIRD_SHOW_ID = 3L

class TvShowRepositoryTest {

    private lateinit var repository: TvShowRepositoryImpl

    private val service: ShowsService = mock()
    private val mapper: TvShowMapper = mock()
    private val firstApiTvShow: ApiTvShow = mock {
        on { id }.doReturn(FIRST_SHOW_ID)
    }
    private val secondApiTvShow: ApiTvShow = mock {
        on { id }.doReturn(SECOND_SHOW_ID)
    }
    private val thirdApiTvShow: ApiTvShow = mock {
        on { id }.doReturn(THIRD_SHOW_ID)
    }
    private val firstTvShow: TvShow = mock {
        on { id }.doReturn(FIRST_SHOW_ID)
    }
    private val secondTvShow: TvShow = mock {
        on { id }.doReturn(SECOND_SHOW_ID)
    }
    private val thirdTvShow: TvShow = mock {
        on { id }.doReturn(THIRD_SHOW_ID)
    }
    private val tvShowItems = ApiTvShows(
        listOf(firstApiTvShow, secondApiTvShow, thirdApiTvShow),
        FIRST_PAGE,
        TOTAL_PAGES
    )
    private val tvShows = TvShows(
        listOf(firstTvShow, secondTvShow, thirdTvShow),
        FIRST_PAGE,
        TOTAL_PAGES
    )

    @Before
    fun setup() {
        repository = TvShowRepositoryImpl(service, mapper)

        whenever(mapper.map(tvShowItems)) doReturn tvShows
    }

    @Test
    fun `Get shows gets most popular shows from remote and caches in memory cache`() {

        whenever(service.getMostPopularTvShows(FIRST_PAGE)) doReturn Single.just(tvShowItems)

        repository.getMostPopularShows(FIRST_PAGE)
            .test()

        verify(service).getMostPopularTvShows(FIRST_PAGE)

        repository.showCache.apply {
            assertThat(size, `is`(equalTo(3)))
            assertThat(get(SECOND_SHOW_ID), `is`(equalTo(secondTvShow)))
        }
    }

    @Test
    fun `Get show gets show from in memory cache`() {

        repository.showCache[THIRD_SHOW_ID] = thirdTvShow

        repository.getShow(THIRD_SHOW_ID)
            .test()
            .apply {
                assertValue(thirdTvShow)
            }
    }

    @Test
    fun `Get similar shows gets from remote and caches in memory cache`() {

        repository.showCache[FIRST_SHOW_ID] = firstTvShow
        repository.showCache[SECOND_SHOW_ID] = secondTvShow

        val similarShowItems = ApiTvShows(listOf(thirdApiTvShow), FIRST_PAGE, TOTAL_PAGES)
        whenever(mapper.map(similarShowItems)) doReturn TvShows(
            listOf(thirdTvShow), FIRST_PAGE,
            TOTAL_PAGES
        )
        whenever(service.getSimilarTvShows(FIRST_SHOW_ID, FIRST_PAGE)) doReturn
                Single.just(similarShowItems)

        repository.getSimilarTvShows(FIRST_SHOW_ID, FIRST_PAGE)
            .test()

        verify(service).getSimilarTvShows(FIRST_SHOW_ID, FIRST_PAGE)

        repository.showCache.apply {
            assertThat(size, `is`(equalTo(3)))
            assertThat(get(THIRD_SHOW_ID), `is`(equalTo(thirdTvShow)))
        }
    }
}