package com.ng.tvshowsdb.shows.fixtures

import com.ng.tvshowsdb.shows.api.domain.model.TvShow
import com.ng.tvshowsdb.shows.api.domain.model.TvShows
import com.ng.tvshowsdb.shows.api.domain.repository.TvShowRepository
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

internal class FakeTvShowRepository @Inject constructor(): TvShowRepository {

    private val tvShows = TvShows(buildTvShows(), currentPage = 1, totalPages = 5)
    private val similarTvShows = TvShows(buildTvShows(), currentPage = 1, totalPages = 5)


    override fun getMostPopularShows(page: Int): Single<TvShows> =
        Single.just(tvShows)

    override fun getShow(id: Long): Maybe<TvShow> =
        tvShows.shows.firstOrNull { it.id == id }?.let {
            Maybe.just(it)
        } ?: Maybe.empty()

    override fun getSimilarTvShows(id: Long, page: Int): Single<TvShows> =
        Single.just(similarTvShows.copy(currentPage = page))

}