package com.ng.tvshowsdb.shows.data.repository

import com.ng.tvshowsdb.shows.data.datasource.cache.InMemoryTvShowsDataSource
import com.ng.tvshowsdb.shows.data.datasource.remote.RemoteTvShowDataSource
import com.ng.tvshowsdb.shows.domain.model.TvShow
import com.ng.tvshowsdb.shows.domain.model.TvShows
import com.ng.tvshowsdb.shows.domain.repository.TvShowRepository
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
    private val inMemoryDataSource: InMemoryTvShowsDataSource,
    private val remoteDataSource: RemoteTvShowDataSource,
) : TvShowRepository {

    override fun getMostPopularShows(page: Int): Single<TvShows> =
        remoteDataSource.getMostPopularTvShows(page)
            .cacheTvShows()

    override fun getShow(id: Long): Maybe<TvShow> = Maybe.fromCallable { inMemoryDataSource[id] }

    override fun getSimilarTvShows(
        id: Long,
        page: Int,
    ): Single<TvShows> = remoteDataSource.getSimilarTvShows(id, page)
        .cacheTvShows()

    override fun getTopRated(page: Int): Single<TvShows> =
        remoteDataSource.getTopRatedTvShows(page)
            .cacheTvShows()

    override fun getLatest(): Single<TvShow> = remoteDataSource.getLatest()
        .cacheTvShow()

    private fun Single<TvShows>.cacheTvShows() = doOnSuccess { inMemoryDataSource.save(it.shows) }

    private fun Single<TvShow>.cacheTvShow() = doOnSuccess { inMemoryDataSource.save(it) }

}
