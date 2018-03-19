package com.ng.tvshowsdb.domain.repository

import com.ng.tvshowsdb.domain.model.TvShow
import com.ng.tvshowsdb.domain.model.TvShows
import io.reactivex.Flowable

interface TvShowRepository {

  fun getMostPopularShows(page: Int): Flowable<TvShows>

  fun getShow(id: Long): Flowable<TvShow>

  fun getSimilarTvShows(id: Long, page: Int): Flowable<TvShows>
}