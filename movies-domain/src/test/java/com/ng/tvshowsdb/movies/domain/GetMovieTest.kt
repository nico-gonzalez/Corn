package com.ng.tvshowsdb.movies.domain

import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import com.ng.tvshowsdb.movies.domain.common.ImmediateSchedulers
import com.ng.tvshowsdb.movies.domain.model.Movie
import com.ng.tvshowsdb.movies.domain.repository.MovieRepository
import com.ng.tvshowsdb.movies.domain.usecase.GetMovie
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Test

private const val SHOW_ID = 1L

class GetMovieTest {

    private lateinit var getTvShow: GetMovie

    private val movieRepository: MovieRepository = mock()
    private val schedulerProvider: SchedulerProvider = ImmediateSchedulers()
    private val movie: Movie = mock()

    @Before
    fun setup() {
        getTvShow = GetMovie(schedulerProvider, movieRepository)
    }

    @Test
    fun `Gets Show from Repository by it's Id`() {
        whenever(movieRepository.getMovie(SHOW_ID)) doReturn Maybe.just(movie)

        getTvShow(SHOW_ID)
            .test()
            .apply {
                assertValue {
                    it.value == movie
                }
            }
        verify(movieRepository).getMovie(SHOW_ID)
    }

    @Test
    fun `Gets Show and fails returning an error`() {
        val error: Throwable = mock()
        whenever(movieRepository.getMovie(SHOW_ID)) doReturn Maybe.error(error)

        getTvShow(SHOW_ID)
            .test()
            .assertError(error)
        verify(movieRepository).getMovie(SHOW_ID)
    }
}
