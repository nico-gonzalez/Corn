package com.ng.tvshowsdb.shows.api.domain.usecase

import com.ng.tvshowsdb.core.domain.common.Result
import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import com.ng.tvshowsdb.core.domain.common.SingleUseCase
import com.ng.tvshowsdb.shows.api.domain.model.TvShow
import com.ng.tvshowsdb.shows.api.domain.repository.TvShowRepository
import io.reactivex.Single
import javax.inject.Inject

class GetTvShow @Inject internal constructor(
    schedulers: SchedulerProvider,
    private val tvShowRepository: TvShowRepository
) : SingleUseCase<Long, TvShow>(schedulers) {

    override fun execute(params: Long): Single<Result<TvShow>> =
        tvShowRepository.getShow(params)
            .map { Result.success(it) }
            .switchIfEmpty(Single.error(Throwable("Unable to find TvShow with Id $params")))
}