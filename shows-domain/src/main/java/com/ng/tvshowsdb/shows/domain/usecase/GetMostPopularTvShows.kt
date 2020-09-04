package com.ng.tvshowsdb.shows.domain.usecase

import com.ng.tvshowsdb.core.domain.common.Result
import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import com.ng.tvshowsdb.core.domain.common.UseCase
import com.ng.tvshowsdb.shows.domain.model.TvShows
import com.ng.tvshowsdb.shows.domain.repository.TvShowRepository
import io.reactivex.Single
import javax.inject.Inject

class GetMostPopularTvShows @Inject constructor(
    private val schedulers: SchedulerProvider,
    private val tvShowRepository: TvShowRepository
) : UseCase<Int, TvShows> {

    override fun execute(params: Int): Single<Result<TvShows>> =
        tvShowRepository.getMostPopularShows(params)
            .map { Result.success(it) }
            .onErrorReturn { Result.error(it) }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
}