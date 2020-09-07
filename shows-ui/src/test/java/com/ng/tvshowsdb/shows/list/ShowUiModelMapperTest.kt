package com.ng.tvshowsdb.shows.list

import com.ng.tvshowsdb.shows.domain.model.TvShow
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class ShowUiModelMapperTest {

    private lateinit var mapper: ShowViewModelMapper

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
        mapper = ShowViewModelMapper()
    }

    @Test
    fun `TvShow entity is formatted to a TvShow view model`() {
        val result = mapper.map(tvShow)
        val viewModel = ShowUiModel(
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