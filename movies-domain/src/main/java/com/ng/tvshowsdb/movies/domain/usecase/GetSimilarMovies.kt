package com.ng.tvshowsdb.movies.domain.usecase

import com.ng.tvshowsdb.core.domain.common.Result
import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import com.ng.tvshowsdb.core.domain.common.SingleUseCase
import com.ng.tvshowsdb.movies.domain.model.Movies
import com.ng.tvshowsdb.movies.domain.repository.MovieRepository
import com.ng.tvshowsdb.movies.domain.usecase.GetSimilarMovies.Params
import io.reactivex.Single
import javax.inject.Inject

class GetSimilarMovies @Inject constructor(
    schedulers: SchedulerProvider,
    private val movieRepository: MovieRepository,
) : SingleUseCase<Params, Movies>(schedulers) {

    override fun execute(params: Params): Single<Result<Movies>> =
        movieRepository.getSimilarMovies(params.movieId, params.page)
            .map { Result.success(it) }
            .onErrorReturn { Result.error(it) }

    data class Params(val movieId: Long, val page: Int)
}
