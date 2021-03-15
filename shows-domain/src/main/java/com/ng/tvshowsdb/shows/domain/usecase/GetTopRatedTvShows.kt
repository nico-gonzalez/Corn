package com.ng.tvshowsdb.shows.domain.usecase

import com.ng.tvshowsdb.core.domain.common.Result
import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import com.ng.tvshowsdb.core.domain.common.SingleUseCase
import com.ng.tvshowsdb.shows.domain.model.TvShows
import com.ng.tvshowsdb.shows.domain.repository.TvShowRepository
import io.reactivex.Single
import javax.inject.Inject

class GetTopRatedTvShows @Inject constructor(
    schedulers: SchedulerProvider,
    private val tvShowRepository: TvShowRepository,
) : SingleUseCase<Int, TvShows>(schedulers) {

    override fun execute(params: Int): Single<Result<TvShows>> =
        tvShowRepository.getTopRated(params)
            .map { Result.success(it) }
            .onErrorReturn { Result.error(it) }
}
