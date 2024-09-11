package com.ng.tvshowsdb.shows.api.data

import com.ng.tvshowsdb.shows.api.data.remote.model.ApiTvShow
import com.ng.tvshowsdb.shows.api.data.remote.model.ApiTvShows
import com.ng.tvshowsdb.shows.api.data.shows.TvShowMapper
import com.ng.tvshowsdb.shows.api.domain.model.TvShow
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private const val PAGE = 1
private const val TOTAL_PAGES = 5

class TvShowMapperTest {

    private lateinit var mapper: TvShowMapper

    @Before
    fun setup() {
        mapper = TvShowMapper()
    }

    @Test
    fun `TvShowItem remote is mapped to TvShow entity`() {
        val result = mapper.map(
            ApiTvShows(
                listOf(
                    ApiTvShow(
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
        val tvShow = TvShow(
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
        assertEquals(result.shows.first(), tvShow)
    }

    @Test
    fun `TvShowItem remote with just id is mapped to TvShow entity with defaults`() {
        val result = mapper.map(
            ApiTvShows(
                listOf(
                    ApiTvShow(
                        1,
                        null, null, null,
                        null, null, null
                    )
                ), PAGE, TOTAL_PAGES
            )
        )
        val tvShow = TvShow(
            1, "",
            "", "", "",
            "", 0.0
        )
        assertTrue(result.currentPage == 1)
        assertEquals(result.totalPages, TOTAL_PAGES)
        assertEquals(result.shows.first(), tvShow)
    }
}