package com.ng.tvshowsdb.shows.domain.repository

import com.ng.tvshowsdb.shows.domain.model.TvShow
import com.ng.tvshowsdb.shows.domain.model.TvShows
import io.reactivex.Maybe
import io.reactivex.Single

interface TvShowRepository {

    fun getMostPopularShows(page: Int): Single<TvShows>

    fun getShow(id: Long): Maybe<TvShow>

    fun getSimilarTvShows(id: Long, page: Int): Single<TvShows>

    fun getTopRated(page: Int): Single<TvShows>

    fun getLatest(): Single<TvShow>
}
