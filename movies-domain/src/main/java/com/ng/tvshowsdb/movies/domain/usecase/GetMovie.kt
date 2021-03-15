package com.ng.tvshowsdb.movies.domain.usecase

import com.ng.tvshowsdb.core.domain.common.Result
import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import com.ng.tvshowsdb.core.domain.common.SingleUseCase
import com.ng.tvshowsdb.movies.domain.errors.MovieNotFound
import com.ng.tvshowsdb.movies.domain.model.Movie
import com.ng.tvshowsdb.movies.domain.repository.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

class GetMovie @Inject constructor(
    schedulers: SchedulerProvider,
    private val movieRepository: MovieRepository,
) : SingleUseCase<Long, Movie>(schedulers) {

    override fun execute(params: Long): Single<Result<Movie>> =
        movieRepository.getMovie(params)
            .map { Result.success(it) }
            .switchIfEmpty(Single.error(MovieNotFound(params)))
}
