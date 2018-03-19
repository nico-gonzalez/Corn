package com.ng.tvshowsdb.data.shows

import com.ng.tvshowsdb.data.common.remote.model.TvShowItems
import com.ng.tvshowsdb.domain.model.TvShow
import com.ng.tvshowsdb.domain.model.TvShows
import javax.inject.Inject

class TvShowMapper @Inject constructor() {

  fun map(tvShows: TvShowItems): TvShows = with(tvShows) {
    TvShows(results
        .map {
          TvShow(it.id,
              it.name ?: "",
              it.overview ?: "",
              it.poster_path ?: "",
              it.backdrop_path ?: "",
              it.first_air_date ?: "",
              it.vote_average ?: 0.0)
        }, page, total_pages)

  }
}