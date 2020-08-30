package com.ng.tvshowsdb.data.shows

import com.ng.tvshowsdb.data.common.remote.TheMovieDBService
import com.ng.tvshowsdb.data.common.remote.model.TvShowItem
import com.ng.tvshowsdb.data.common.remote.model.TvShowItems
import com.ng.tvshowsdb.domain.model.TvShow
import com.ng.tvshowsdb.domain.model.TvShows
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

private const val FIRST_PAGE = 1
private const val TOTAL_PAGES = 5
private const val FIRST_SHOW_ID = 1L
private const val SECOND_SHOW_ID = 2L
private const val THIRD_SHOW_ID = 3L

class TheMovieDbTvShowRepositoryTest {

    private lateinit var repository: TheMovieDbTvShowRepository

    private val service: TheMovieDBService = mock()
    private val mapper: TvShowMapper = mock()
    private val firstTvShowItem: TvShowItem = mock {
        on { id }.doReturn(FIRST_SHOW_ID)
    }
    private val secondTvShowItem: TvShowItem = mock {
        on { id }.doReturn(SECOND_SHOW_ID)
    }
    private val thirdTvShowItem: TvShowItem = mock {
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
    private val tvShowItems = TvShowItems(
      listOf(firstTvShowItem, secondTvShowItem, thirdTvShowItem),
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
        repository = TheMovieDbTvShowRepository(service, mapper)

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

        val similarShowItems = TvShowItems(listOf(thirdTvShowItem), FIRST_PAGE, TOTAL_PAGES)
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