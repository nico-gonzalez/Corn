package com.ng.tvshowsdb.shows.data.shows

import com.ng.tvshowsdb.shows.data.remote.ShowsService
import com.ng.tvshowsdb.shows.domain.model.TvShow
import com.ng.tvshowsdb.shows.domain.model.TvShows
import com.ng.tvshowsdb.shows.domain.repository.TvShowRepository
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
    private val service: ShowsService,
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