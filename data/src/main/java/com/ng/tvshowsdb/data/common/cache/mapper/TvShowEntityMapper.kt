package com.ng.tvshowsdb.data.common.cache.mapper

import com.ng.tvshowsdb.data.common.cache.entity.TvShowEntity
import com.ng.tvshowsdb.domain.model.TvShow
import javax.inject.Inject

class TvShowEntityMapper @Inject constructor() {

  fun map(tvShow: TvShowEntity): TvShow = with(tvShow) {
    TvShow(
      id = id,
      title = title,
      description = description,
      posterPath = posterPath,
      backdropPath = backdropPath,
      firstAirDate = firstAirDate,
      rating = rating
    )
  }

  fun map(tvShow: TvShow): TvShowEntity = with(tvShow) {
    TvShowEntity(
      id = id,
      title = title,
      description = description,
      posterPath = posterPath,
      backdropPath = backdropPath,
      firstAirDate = firstAirDate,
      rating = rating
    )
  }
}