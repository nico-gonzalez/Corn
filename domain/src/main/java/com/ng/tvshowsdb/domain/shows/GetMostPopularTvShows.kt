package com.ng.tvshowsdb.domain.shows

import com.ng.tvshowsdb.domain.common.Result
import com.ng.tvshowsdb.domain.common.SchedulerProvider
import com.ng.tvshowsdb.domain.common.UseCase
import com.ng.tvshowsdb.domain.model.TvShows
import com.ng.tvshowsdb.domain.repository.TvShowRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetMostPopularTvShows @Inject constructor(private val schedulers: SchedulerProvider,
    private val tvShowRepository: TvShowRepository) : UseCase<Int, TvShows> {

  override fun execute(page: Int): Flowable<Result<TvShows>> =
      tvShowRepository.getMostPopularShows(page)
          .map { Result.success(it) }
          .onErrorReturn { Result.error(it) }
          .subscribeOn(schedulers.io())
          .observeOn(schedulers.ui())
}