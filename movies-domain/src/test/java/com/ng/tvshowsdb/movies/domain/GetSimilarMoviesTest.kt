package com.ng.tvshowsdb.movies.domain

import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import com.ng.tvshowsdb.movies.domain.common.ImmediateSchedulers
import com.ng.tvshowsdb.movies.domain.model.Movie
import com.ng.tvshowsdb.movies.domain.model.Movies
import com.ng.tvshowsdb.movies.domain.repository.MovieRepository
import com.ng.tvshowsdb.movies.domain.usecase.GetSimilarMovies
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

private const val SHOW_ID = 1L
private const val PAGE = 1

class GetSimilarMoviesTest {

    private lateinit var getSimilarMovies: GetSimilarMovies

    private val movieRepository: MovieRepository = mock()
    private val schedulerProvider: SchedulerProvider = ImmediateSchedulers()
    private val movie: Movie = mock()

    @Before
    fun setup() {
        getSimilarMovies = GetSimilarMovies(schedulerProvider, movieRepository)
    }

    @Test
    fun `Gets similar Shows from Repository for given show and page`() {
        val shows = Movies(listOf(movie, movie, movie), 1, 5)
        whenever(movieRepository.getSimilarMovies(SHOW_ID, PAGE)) doReturn Single.just(shows)

        getSimilarMovies(GetSimilarMovies.Params(SHOW_ID, PAGE))
            .test()
            .apply {
                assertValue {
                    it.value == shows
                }
            }
        verify(movieRepository).getSimilarMovies(SHOW_ID, PAGE)
    }

    @Test
    fun `Gets similar shows and fails returning an error`() {
        val error: Throwable = mock()
        whenever(movieRepository.getSimilarMovies(SHOW_ID, PAGE)) doReturn Single.error(error)

        getSimilarMovies(GetSimilarMovies.Params(SHOW_ID, PAGE))
            .test()
            .apply {
                assertValue {
                    it.error === error
                }
            }
        verify(movieRepository).getSimilarMovies(SHOW_ID, PAGE)
    }
}
