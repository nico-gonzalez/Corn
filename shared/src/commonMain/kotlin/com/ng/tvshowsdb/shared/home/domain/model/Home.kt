package com.ng.tvshowsdb.shared.home.domain.model

import com.ng.tvshowsdb.shared.movies.domain.model.Movie

data class Home(
    val mostPopularMovies: List<Movie>? = null,
    val topRatedMovies: List<Movie>? = null
)
