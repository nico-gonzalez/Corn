package com.ng.tvshowsdb.shows.detail

import com.ng.tvshowsdb.shows.api.domain.model.TvShow
import javax.inject.Inject

class ShowDetailsViewModelMapper @Inject constructor() {

    fun map(tvShow: TvShow): ShowDetailsUiModel = with(tvShow) {
        ShowDetailsUiModel(
            id,
            title,
            description,
            posterPath,
            backdropPath,
            firstAirDate,
            rating.toString()
        )
    }
}