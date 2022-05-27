package com.ng.tvshowsdb.core.domain.common

import io.reactivex.Single

abstract class SingleUseCase<in Params, R : Any>(
    private val schedulers: SchedulerProvider
) {

    operator fun invoke(params: Params): Single<Result<R>> =
        execute(params)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())

    protected abstract fun execute(params: Params): Single<Result<R>>
}