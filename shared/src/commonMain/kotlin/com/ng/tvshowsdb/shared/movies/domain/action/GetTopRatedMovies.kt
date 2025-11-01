package com.ng.tvshowsdb.shared.movies.domain.action

import com.ng.tvshowsdb.shared.executor.Executor
import com.ng.tvshowsdb.shared.movies.data.MovieRepository
import com.ng.tvshowsdb.shared.movies.data.mapper.MovieMapper
import com.ng.tvshowsdb.shared.movies.data.remote.MoviesService
import com.ng.tvshowsdb.shared.movies.domain.model.Movie

class GetTopRatedMovies internal constructor(
    private val executor: Executor,
    private val moviesService: MoviesService,
    private val mapper: MovieMapper,
    private val movieRepository: MovieRepository,
) {

    suspend operator fun invoke(): Result<List<Movie>> = executor {
        moviesService.getTopRatedMovies().results?.map(mapper::map).orEmpty()
            .onEach { movie ->
                movieRepository.saveMovie(movie)
            }
    }
}