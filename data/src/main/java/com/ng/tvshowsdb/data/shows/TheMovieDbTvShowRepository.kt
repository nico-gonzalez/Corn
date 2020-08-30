package com.ng.tvshowsdb.data.shows

import com.ng.tvshowsdb.data.common.remote.TheMovieDBService
import com.ng.tvshowsdb.domain.model.TvShow
import com.ng.tvshowsdb.domain.model.TvShows
import com.ng.tvshowsdb.domain.repository.TvShowRepository
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class TheMovieDbTvShowRepository @Inject constructor(
    private val service: TheMovieDBService,
    private val mapper: TvShowMapper
) : TvShowRepository {

    internal val showCache: MutableMap<Long, TvShow> = mutableMapOf()

    override fun getMostPopularShows(page: Int): Single<TvShows> =
        service.getMostPopularTvShows(page)
            .map { mapper.map(it) }
            .doOnSuccess {
                it.shows.forEach {
                    showCache[it.id] = it
                }
            }

    override fun getShow(id: Long): Maybe<TvShow> = Maybe.fromCallable { showCache[id] }

    override fun getSimilarTvShows(
        id: Long,
        page: Int
    ): Single<TvShows> =
        service.getSimilarTvShows(id, page)
            .map { mapper.map(it) }
            .doOnSuccess {
                it.shows.forEach {
                    showCache[it.id] = it
                }
            }
}