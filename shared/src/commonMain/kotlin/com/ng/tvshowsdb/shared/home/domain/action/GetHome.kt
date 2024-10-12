package com.ng.tvshowsdb.shared.home.domain.action

import com.ng.tvshowsdb.shared.executor.Executor
import com.ng.tvshowsdb.shared.home.domain.model.Home
import com.ng.tvshowsdb.shared.movies.domain.action.GetMostPopularMovies
import com.ng.tvshowsdb.shared.movies.domain.action.GetTopRatedMovies

class GetHome internal constructor(
    private val executor: Executor,
    private val getTopRatedMovies: GetTopRatedMovies,
    private val getMostPopularMovies: GetMostPopularMovies
) {

    suspend operator fun invoke(): Result<Home> = executor {
        Home(
            topRatedMovies = getTopRatedMovies().getOrNull(),
            mostPopularMovies = getMostPopularMovies().getOrNull()
        )
    }
}