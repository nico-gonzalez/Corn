package com.ng.tvshowsdb.presentation.detail

import com.ng.tvshowsdb.domain.model.TvShow
import javax.inject.Inject

class TvShowDetailsViewModelMapper @Inject constructor() {

    fun map(tvShow: TvShow): TvShowDetailsUiModel = with(tvShow) {
        TvShowDetailsUiModel(
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