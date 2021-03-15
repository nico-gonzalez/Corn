package com.ng.tvshowsdb.shows.data

import com.ng.tvshowsdb.movies.data.remote.MoviesService
import com.ng.tvshowsdb.movies.data.remote.model.ApiMovie
import com.ng.tvshowsdb.movies.data.remote.model.ApiMovies
import com.ng.tvshowsdb.movies.data.movies.MovieMapper
import com.ng.tvshowsdb.movies.data.movies.MovieRepositoryImpl
import com.ng.tvshowsdb.movies.domain.model.Movie
import com.ng.tvshowsdb.movies.domain.model.Movies
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

class MovieRepositoryTest {

    private lateinit var repository: MovieRepositoryImpl

    private val service: MoviesService = mock()
    private val mapper: MovieMapper = mock()
    private val firstApiMovie: ApiMovie = mock {
        on { id }.doReturn(FIRST_SHOW_ID)
    }
    private val secondApiMovie: ApiMovie = mock {
        on { id }.doReturn(SECOND_SHOW_ID)
    }
    private val thirdApiMovie: ApiMovie = mock {
        on { id }.doReturn(THIRD_SHOW_ID)
    }
    private val firstTvShow: Movie = mock {
        on { id }.doReturn(FIRST_SHOW_ID)
    }
    private val secondTvShow: Movie = mock {
        on { id }.doReturn(SECOND_SHOW_ID)
    }
    private val thirdTvShow: Movie = mock {
        on { id }.doReturn(THIRD_SHOW_ID)
    }
    private val tvShowItems = ApiMovies(
        listOf(firstApiMovie, secondApiMovie, thirdApiMovie),
        FIRST_PAGE,
        TOTAL_PAGES
    )
    private val tvShows = Movies(
        listOf(firstTvShow, secondTvShow, thirdTvShow),
        FIRST_PAGE,
        TOTAL_PAGES
    )

    @Before
    fun setup() {
        repository = MovieRepositoryImpl(service, mapper)

        whenever(mapper.map(tvShowItems)) doReturn tvShows
    }

    @Test
    fun `Get shows gets most popular shows from remote and caches in memory cache`() {

        whenever(service.getMostPopularMovies(FIRST_PAGE)) doReturn Single.just(tvShowItems)

        repository.getMostPopularMovies(FIRST_PAGE)
            .test()

        verify(service).getMostPopularMovies(FIRST_PAGE)

        repository.movieCache.apply {
            assertThat(size, `is`(equalTo(3)))
            assertThat(get(SECOND_SHOW_ID), `is`(equalTo(secondTvShow)))
        }
    }

    @Test
    fun `Get show gets show from in memory cache`() {

        repository.movieCache[THIRD_SHOW_ID] = thirdTvShow

        repository.getMovie(THIRD_SHOW_ID)
            .test()
            .apply {
                assertValue(thirdTvShow)
            }
    }

    @Test
    fun `Get similar shows gets from remote and caches in memory cache`() {

        repository.movieCache[FIRST_SHOW_ID] = firstTvShow
        repository.movieCache[SECOND_SHOW_ID] = secondTvShow

        val similarShowItems = ApiMovies(listOf(thirdApiMovie), FIRST_PAGE, TOTAL_PAGES)
        whenever(mapper.map(similarShowItems)) doReturn
                Movies(listOf(thirdTvShow), FIRST_PAGE, TOTAL_PAGES)
        whenever(service.getSimilarMovies(FIRST_SHOW_ID, FIRST_PAGE)) doReturn
                Single.just(similarShowItems)

        repository.getSimilarMovies(FIRST_SHOW_ID, FIRST_PAGE)
            .test()

        verify(service).getSimilarMovies(FIRST_SHOW_ID, FIRST_PAGE)

        repository.movieCache.apply {
            assertThat(size, `is`(equalTo(3)))
            assertThat(get(THIRD_SHOW_ID), `is`(equalTo(thirdTvShow)))
        }
    }
}
