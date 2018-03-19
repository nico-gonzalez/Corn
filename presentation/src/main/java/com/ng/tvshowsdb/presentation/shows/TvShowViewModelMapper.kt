package com.ng.tvshowsdb.presentation.shows

import com.ng.tvshowsdb.domain.model.TvShow
import javax.inject.Inject

class TvShowViewModelMapper @Inject constructor() {

  fun map(tvShow: TvShow): TvShowViewModel = with(tvShow) {
    TvShowViewModel(tvShow.id, tvShow.title, tvShow.posterPath, tvShow.rating.toString())
  }
}