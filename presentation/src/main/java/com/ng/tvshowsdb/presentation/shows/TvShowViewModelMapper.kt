package com.ng.tvshowsdb.presentation.shows

import com.ng.tvshowsdb.domain.model.TvShow
import javax.inject.Inject

class TvShowViewModelMapper @Inject constructor() {

    fun map(tvShow: TvShow): TvShowUiModel = with(tvShow) {
        TvShowUiModel(
            id,
            title,
            posterPath,
            rating.toString()
        )
    }
}