package com.ng.tvshowsdb.data.shows

import com.ng.tvshowsdb.data.common.cache.TvShowsDatabase
import com.ng.tvshowsdb.data.common.cache.mapper.TvShowEntityMapper
import com.ng.tvshowsdb.data.common.remote.TheMovieDBService
import com.ng.tvshowsdb.domain.model.TvShow
import com.ng.tvshowsdb.domain.model.TvShows
import com.ng.tvshowsdb.domain.repository.TvShowRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

private const val DEFAULT_TOTAL_PAGES = 50
private const val DEFAULT_PAGE_SIZE = 20

class TheMovieDbTvShowRepository @Inject constructor(
  private val tvShowsDatabase: TvShowsDatabase,
  private val service: TheMovieDBService,
  private val tvShowMapper: TvShowMapper,
  private val tvShowEntityMapper: TvShowEntityMapper
) : TvShowRepository {

  private val tvShowsDao by lazy { tvShowsDatabase.tvShowsDao() }

  override fun getMostPopularShows(page: Int): Flowable<TvShows> = Flowable.concatArrayEager(
    fetchCachedTvShows(page).take(1),
    fetchRemoteTvShows(page)
  )
    .filter { it.shows.isNotEmpty() }

  private fun fetchCachedTvShows(page: Int) =
    tvShowsDao.getTvShows(DEFAULT_PAGE_SIZE, page * DEFAULT_PAGE_SIZE)
      .flatMap { shows ->
        Flowable.fromIterable(shows)
          .map { tvShowEntityMapper.map(it) }
          .toList()
          .toFlowable()
      }
      .map { shows -> TvShows(shows = shows, currentPage = page, totalPages = DEFAULT_TOTAL_PAGES) }

  private fun fetchRemoteTvShows(page: Int) = service.getMostPopularTvShows(page)
    .map(tvShowMapper::map)
    .flatMap { tvShows ->
      Completable.fromCallable {
        tvShowsDatabase.runInTransaction {
          tvShowsDao.delete()
          tvShowsDao.insert(tvShows.shows.map(tvShowEntityMapper::map))
        }
      }.andThen(Single.just(tvShows))
    }.toFlowable()

  override fun getShow(id: Long): Maybe<TvShow> =
    tvShowsDao.findTvShow(id).map(tvShowEntityMapper::map)

  override fun getSimilarTvShows(
    id: Long,
    page: Int
  ): Single<TvShows> =
    service.getSimilarTvShows(id, page)
      .map { tvShowMapper.map(it) }
}