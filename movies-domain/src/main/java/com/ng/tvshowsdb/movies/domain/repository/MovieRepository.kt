package com.ng.tvshowsdb.movies.domain.repository

import com.ng.tvshowsdb.movies.domain.model.Movie
import com.ng.tvshowsdb.movies.domain.model.Movies
import io.reactivex.Maybe
import io.reactivex.Single

interface MovieRepository {

    fun getMostPopularMovies(page: Int): Single<Movies>

    fun getMovie(id: Long): Maybe<Movie>

    fun getSimilarMovies(id: Long, page: Int): Single<Movies>
}
