package com.ng.tvshowsdb.shows.data.shows

import com.ng.tvshowsdb.shows.data.remote.model.ApiTvShows
import com.ng.tvshowsdb.shows.domain.model.TvShow
import com.ng.tvshowsdb.shows.domain.model.TvShows
import javax.inject.Inject

class TvShowMapper @Inject constructor() {

    fun map(apiTvShows: ApiTvShows): TvShows = with(apiTvShows) {
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