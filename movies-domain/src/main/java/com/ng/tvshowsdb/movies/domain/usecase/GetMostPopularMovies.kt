package com.ng.tvshowsdb.movies.domain.usecase

import com.ng.tvshowsdb.core.domain.common.Result
import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import com.ng.tvshowsdb.core.domain.common.SingleUseCase
import com.ng.tvshowsdb.movies.domain.model.Movies
import com.ng.tvshowsdb.movies.domain.repository.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

class GetMostPopularMovies @Inject constructor(
    schedulers: SchedulerProvider,
    private val movieRepository: MovieRepository
) : SingleUseCase<Int, Movies>(schedulers) {

    override fun execute(params: Int): Single<Result<Movies>> =
        movieRepository.getMostPopularMovies(params)
            .map { Result.success(it) }
            .onErrorReturn { Result.error(it) }
}
