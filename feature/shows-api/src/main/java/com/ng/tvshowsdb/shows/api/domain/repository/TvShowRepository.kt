package com.ng.tvshowsdb.shows.api.domain.repository

import com.ng.tvshowsdb.shows.api.domain.model.TvShow
import com.ng.tvshowsdb.shows.api.domain.model.TvShows
import io.reactivex.Maybe
import io.reactivex.Single

interface TvShowRepository {

    fun getMostPopularShows(page: Int): Single<TvShows>

    fun getShow(id: Long): Maybe<TvShow>

    fun getSimilarTvShows(id: Long, page: Int): Single<TvShows>
}