package com.ng.tvshowsdb.shows.domain.usecase

import com.ng.tvshowsdb.core.domain.common.Result
import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import com.ng.tvshowsdb.core.domain.common.UseCase
import com.ng.tvshowsdb.shows.domain.model.TvShow
import com.ng.tvshowsdb.shows.domain.repository.TvShowRepository
import io.reactivex.Single
import javax.inject.Inject

class GetTvShow @Inject constructor(
    private val schedulers: SchedulerProvider,
    private val tvShowRepository: TvShowRepository
) : UseCase<Long, TvShow> {

    override fun execute(params: Long): Single<Result<TvShow>> =
        tvShowRepository.getShow(params)
            .map { Result.success(it) }
            .switchIfEmpty(Single.error(Throwable("Unable to find TvShow with Id $params")))
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
}