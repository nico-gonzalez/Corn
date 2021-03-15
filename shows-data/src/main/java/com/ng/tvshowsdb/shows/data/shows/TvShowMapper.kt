package com.ng.tvshowsdb.shows.data.shows

import com.ng.tvshowsdb.shows.data.datasource.remote.model.ApiTvShow
import com.ng.tvshowsdb.shows.data.datasource.remote.model.ApiTvShows
import com.ng.tvshowsdb.shows.domain.model.TvShow
import com.ng.tvshowsdb.shows.domain.model.TvShows
import javax.inject.Inject

class TvShowMapper @Inject constructor() {

    fun map(apiTvShows: ApiTvShows): TvShows = with(apiTvShows) {
        TvShows(
            shows = results.mapNotNull(::map),
            page,
            total_pages
        )
    }

    fun map(apiTvShow: ApiTvShow): TvShow? = with(apiTvShow) {
        if (poster_path ?: backdrop_path == null) return null
        TvShow(
            id = id,
            title = name.orEmpty(),
            description = overview.orEmpty(),
            posterPath = poster_path.orEmpty(),
            backdropPath = backdrop_path.orEmpty(),
            firstAirDate = first_air_date.orEmpty(),
            rating = vote_average ?: 0.0
        )
    }
}
