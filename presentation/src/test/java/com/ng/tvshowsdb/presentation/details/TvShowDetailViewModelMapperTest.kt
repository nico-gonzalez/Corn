package com.ng.tvshowsdb.presentation.details

import com.ng.tvshowsdb.domain.model.TvShow
import com.ng.tvshowsdb.presentation.detail.TvShowDetailsViewModel
import com.ng.tvshowsdb.presentation.detail.TvShowDetailsViewModelMapper
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class TvShowDetailViewModelMapperTest {

  private lateinit var mapper: TvShowDetailsViewModelMapper

  private val tvShow = TvShow(1, "La casa de papel",
      "Money Heist", "poster", "backdrop", "2017", 8.9)

  @Before
  fun setup() {
    mapper = TvShowDetailsViewModelMapper()
  }

  @Test
  fun `TvShow entity is formatted to a TvShow detail view model`() {
    val result = mapper.map(tvShow)
    val viewModel = TvShowDetailsViewModel(1, "La casa de papel",
        "Money Heist", "poster", "backdrop",
        "2017", "8.9")

    assertThat(result.id, `is`(equalTo(viewModel.id)))
    assertThat(result.title, `is`(equalTo(viewModel.title)))
    assertThat(result.description, `is`(equalTo(viewModel.description)))
    assertThat(result.posterPath, `is`(equalTo(viewModel.posterPath)))
    assertThat(result.backdropPath, `is`(equalTo(viewModel.backdropPath)))
    assertThat(result.firstAirDate, `is`(equalTo(viewModel.firstAirDate)))
    assertThat(result.rating, `is`(equalTo(viewModel.rating)))
  }
}