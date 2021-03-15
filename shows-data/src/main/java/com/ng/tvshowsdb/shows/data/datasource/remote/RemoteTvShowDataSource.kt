package com.ng.tvshowsdb.shows.data.datasource.remote

import com.ng.tvshowsdb.shows.data.datasource.remote.model.ApiTvShow
import com.ng.tvshowsdb.shows.data.datasource.remote.model.ApiTvShows
import com.ng.tvshowsdb.shows.data.shows.TvShowMapper
import com.ng.tvshowsdb.shows.domain.model.TvShow
import com.ng.tvshowsdb.shows.domain.model.TvShows
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class RemoteTvShowDataSource @Inject constructor(
    private val service: ShowsService,
    private val mapper: TvShowMapper,
) {
    fun getMostPopularTvShows(page: Int): Single<TvShows> = service.getMostPopularTvShows(page)
        .mapTvShows()

    fun getSimilarTvShows(id: Long, page: Int = 1): Single<TvShows> =
        service.getSimilarTvShows(id, page)
            .mapTvShows()

    fun getTopRatedTvShows(page: Int): Single<TvShows> =
        service.getTopRatedTvShows(page).mapTvShows()

    fun getLatest(): Single<TvShow> = service.getLatest().mapTvShow()

    private fun Single<ApiTvShows>.mapTvShows() =
        map(mapper::map)

    private fun Single<ApiTvShow>.mapTvShow() =
        flatMapMaybe {
            val tvShow = mapper.map(it)
            if (tvShow == null) {
                Maybe.empty()
            } else {
                Maybe.just(tvShow)
            }
        }.toSingle()
}
