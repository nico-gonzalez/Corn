package com.ng.tvshowsdb.presentation.detail

import com.ng.tvshowsdb.domain.model.TvShow
import javax.inject.Inject

class TvShowDetailsViewModelMapper @Inject constructor() {

  fun map(tvShow: TvShow): TvShowDetailsViewModel = with(tvShow) {
    TvShowDetailsViewModel(tvShow.id, tvShow.title, tvShow.description, tvShow.posterPath,
        tvShow.backdropPath, tvShow.firstAirDate, tvShow.rating.toString())
  }
}