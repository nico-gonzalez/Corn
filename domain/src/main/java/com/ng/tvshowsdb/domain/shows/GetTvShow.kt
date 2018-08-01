package com.ng.tvshowsdb.domain.shows

import com.ng.tvshowsdb.domain.common.Result
import com.ng.tvshowsdb.domain.common.SchedulerProvider
import com.ng.tvshowsdb.domain.common.UseCase
import com.ng.tvshowsdb.domain.model.TvShow
import com.ng.tvshowsdb.domain.repository.TvShowRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class GetTvShow @Inject constructor(
  private val schedulers: SchedulerProvider,
  private val tvShowRepository: TvShowRepository
) : UseCase<Long, TvShow> {

  override fun execute(id: Long): Flowable<Result<TvShow>> =
    tvShowRepository.getShow(id)
      .map { Result.success(it) }
      .switchIfEmpty(Single.error(Throwable("Unable to find TvShow with Id $id")))
      .toFlowable()
      .subscribeOn(schedulers.io())
      .observeOn(schedulers.ui())
}