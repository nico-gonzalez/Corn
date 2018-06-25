package com.ng.tvshowsdb.domain.shows

import com.ng.tvshowsdb.domain.common.Result
import com.ng.tvshowsdb.domain.common.SchedulerProvider
import com.ng.tvshowsdb.domain.common.UseCase
import com.ng.tvshowsdb.domain.model.TvShows
import com.ng.tvshowsdb.domain.repository.TvShowRepository
import com.ng.tvshowsdb.domain.shows.GetSimilarTvShows.Params
import io.reactivex.Single
import javax.inject.Inject

class GetSimilarTvShows @Inject constructor(private val schedulers: SchedulerProvider,
    private val tvShowRepository: TvShowRepository) : UseCase<Params, TvShows> {

  override fun execute(params: Params): Single<Result<TvShows>> =
      tvShowRepository.getSimilarTvShows(params.showId, params.page)
          .map { Result.success(it) }
          .onErrorReturn { Result.error(it) }
          .subscribeOn(schedulers.io())
          .observeOn(schedulers.ui())

  data class Params(val showId: Long, val page: Int)
}