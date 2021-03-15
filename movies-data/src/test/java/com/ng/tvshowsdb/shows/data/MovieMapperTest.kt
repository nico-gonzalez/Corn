package com.ng.tvshowsdb.shows.data

import com.ng.tvshowsdb.movies.data.remote.model.ApiMovie
import com.ng.tvshowsdb.movies.data.remote.model.ApiMovies
import com.ng.tvshowsdb.movies.data.movies.MovieMapper
import com.ng.tvshowsdb.movies.domain.model.Movie
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private const val PAGE = 1
private const val TOTAL_PAGES = 5

class MovieMapperTest {

    private lateinit var mapper: MovieMapper

    @Before
    fun setup() {
        mapper = MovieMapper()
    }

    @Test
    fun `TvShowItem remote is mapped to TvShow entity`() {
        val result = mapper.map(
            ApiMovies(
                listOf(
                    ApiMovie(
                        1,
                        "La casa de papel",
                        "Money Heist",
                        "poster",
                        "backdrop",
                        8.9,
                        "2017"
                    )
                ), PAGE, TOTAL_PAGES
            )
        )
        val tvShow = Movie(
            1,
            "La casa de papel",
            "Money Heist",
            "poster",
            "backdrop",
            "2017",
            8.9
        )
        assertTrue(result.currentPage == 1)
        assertEquals(result.totalPages, TOTAL_PAGES)
        assertEquals(result.movies.first(), tvShow)
    }

    @Test
    fun `TvShowItem remote with just id is mapped to TvShow entity with defaults`() {
        val result = mapper.map(
            ApiMovies(
                listOf(
                    ApiMovie(
                        1,
                        null, null, null,
                        null, null, null
                    )
                ), PAGE, TOTAL_PAGES
            )
        )
        val tvShow = Movie(
            1, "",
            "", "", "",
            "", 0.0
        )
        assertTrue(result.currentPage == 1)
        assertEquals(result.totalPages, TOTAL_PAGES)
        assertEquals(result.movies.first(), tvShow)
    }
}
