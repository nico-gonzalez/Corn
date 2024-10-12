package com.ng.tvshowsdb.shared.movies.domain.action

import com.ng.tvshowsdb.shared.movies.data.mapper.MovieMapper
import com.ng.tvshowsdb.shared.movies.data.remote.MoviesService
import com.ng.tvshowsdb.shared.movies.domain.model.Movie

class GetMostPopularMovies internal constructor(
    private val moviesService: MoviesService,
    private val mapper: MovieMapper,
) {

    suspend operator fun invoke(): List<Movie> {
        return moviesService.getMostPopularMovies().map(mapper::map)
    }
}