package com.ng.tvshowsdb.domain.common

import io.reactivex.Flowable

interface UseCase<in Params, T> {

  fun execute(params: Params): Flowable<Result<T>>
}