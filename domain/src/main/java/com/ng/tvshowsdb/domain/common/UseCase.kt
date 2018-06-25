package com.ng.tvshowsdb.domain.common

import io.reactivex.Single

interface UseCase<in Params, T> {

  fun execute(params: Params): Single<Result<T>>
}