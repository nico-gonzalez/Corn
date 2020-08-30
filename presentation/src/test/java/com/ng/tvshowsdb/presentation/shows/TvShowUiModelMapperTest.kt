package com.ng.tvshowsdb.presentation.shows

import com.ng.tvshowsdb.domain.model.TvShow
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class TvShowUiModelMapperTest {

    private lateinit var mapper: TvShowViewModelMapper

    private val tvShow = TvShow(
        1,
        "La casa de papel",
        "Money Heist",
        "poster",
        "backdrop",
        "2017",
        8.9
    )

    @Before
    fun setup() {
        mapper = TvShowViewModelMapper()
    }

    @Test
    fun `TvShow entity is formatted to a TvShow view model`() {
        val result = mapper.map(tvShow)
        val viewModel = TvShowUiModel(
            1,
            "La casa de papel",
            "poster",
            "8.9"
        )

        assertThat(result.id, `is`(equalTo(viewModel.id)))
        assertThat(result.title, `is`(equalTo(viewModel.title)))
        assertThat(result.posterPath, `is`(equalTo(viewModel.posterPath)))
        assertThat(result.rating, `is`(equalTo(viewModel.rating)))
    }

}