package com.ng.tvshowsdb.data.shows

import com.ng.tvshowsdb.data.common.remote.model.TvShowItems
import com.ng.tvshowsdb.domain.model.TvShow
import com.ng.tvshowsdb.domain.model.TvShows
import javax.inject.Inject

class TvShowMapper @Inject constructor() {

    fun map(tvShows: TvShowItems): TvShows = with(tvShows) {
        TvShows(
            shows = results.map { tvShow ->
                TvShow(
                    id = tvShow.id,
                    title = tvShow.name.orEmpty(),
                    description = tvShow.overview.orEmpty(),
                    posterPath = tvShow.poster_path.orEmpty(),
                    backdropPath = tvShow.backdrop_path.orEmpty(),
                    firstAirDate = tvShow.first_air_date.orEmpty(),
                    rating = tvShow.vote_average ?: 0.0
                )
            },
            page,
            total_pages
        )
    }
}