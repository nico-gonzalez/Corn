package com.ng.tvshowsdb.movies.domain

import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import com.ng.tvshowsdb.movies.domain.common.ImmediateSchedulers
import com.ng.tvshowsdb.movies.domain.model.Movie
import com.ng.tvshowsdb.movies.domain.model.Movies
import com.ng.tvshowsdb.movies.domain.repository.MovieRepository
import com.ng.tvshowsdb.movies.domain.usecase.GetMostPopularMovies
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

private const val PAGE = 1

class GetMostPopularMoviesTest {

    private lateinit var getMovies: GetMostPopularMovies

    private val movieRepository: MovieRepository = mock()
    private val schedulerProvider: SchedulerProvider = ImmediateSchedulers()
    private val movie: Movie = mock()

    @Before
    fun setup() {
        getMovies = GetMostPopularMovies(schedulerProvider, movieRepository)
    }

    @Test
    fun `Gets Shows from Repository for given page`() {
        val shows = Movies(listOf(movie, movie, movie), 1, 5)
        whenever(movieRepository.getMostPopularMovies(PAGE)) doReturn Single.just(shows)

        getMovies(PAGE)
            .test()
            .apply {
                assertValue {
                    it.value == shows
                }
            }
        verify(movieRepository).getMostPopularMovies(PAGE)
    }

    @Test
    fun `Gets Shows and fails returning an error`() {
        val error: Throwable = mock()
        whenever(movieRepository.getMostPopularMovies(PAGE)) doReturn Single.error(error)

        getMovies(PAGE)
            .test()
            .apply {
                assertValue {
                    it.error === error
                }
            }
        verify(movieRepository).getMostPopularMovies(PAGE)
    }
}
