package com.ng.tvshowsdb.shows.list

import com.ng.tvshowsdb.shows.api.domain.model.TvShow
import javax.inject.Inject

class ShowViewModelMapper @Inject constructor() {

    fun map(tvShow: TvShow): ShowUiModel = with(tvShow) {
        ShowUiModel(
            id,
            title,
            posterPath,
            rating.toString()
        )
    }
}