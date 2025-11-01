package com.ng.tvshowsdb.shared.movies.data

import com.ng.tvshowsdb.shared.movies.domain.model.Movie

internal class MovieRepository {

    private val movieCache = mutableMapOf<Long, Movie>()

    fun getMovie(id: Long): Movie? = movieCache[id]

    fun saveMovie(movie: Movie) {
        movieCache[movie.id] = movie
    }
}