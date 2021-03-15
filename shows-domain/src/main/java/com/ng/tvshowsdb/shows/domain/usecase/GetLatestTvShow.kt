package com.ng.tvshowsdb.shows.domain.usecase

import com.ng.tvshowsdb.core.domain.common.Result
import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import com.ng.tvshowsdb.core.domain.common.SingleUseCase
import com.ng.tvshowsdb.shows.domain.model.TvShow
import com.ng.tvshowsdb.shows.domain.repository.TvShowRepository
import io.reactivex.Single
import javax.inject.Inject

class GetLatestTvShow @Inject constructor(
    schedulers: SchedulerProvider,
    private val tvShowRepository: TvShowRepository,
) : SingleUseCase<Unit, TvShow>(schedulers) {

    override fun execute(params: Unit): Single<Result<TvShow>> =
        tvShowRepository.getLatest()
            .map { Result.success(it) }
            .onErrorReturn { Result.error(Throwable("Unable to get latest tv show")) }
}
