package com.ng.tvshowsdb.movies.data.movies

import com.ng.tvshowsdb.movies.data.remote.MoviesService
import com.ng.tvshowsdb.movies.domain.model.Movie
import com.ng.tvshowsdb.movies.domain.model.Movies
import com.ng.tvshowsdb.movies.domain.repository.MovieRepository
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: MoviesService,
    private val mapper: MovieMapper,
) : MovieRepository {

    internal val movieCache: MutableMap<Long, Movie> = mutableMapOf()

    override fun getMostPopularMovies(page: Int): Single<Movies> =
        service.getMostPopularMovies(page)
            .map { mapper.map(it) }
            .doOnSuccess {
                it.movies.forEach {
                    movieCache[it.id] = it
                }
            }

    override fun getMovie(id: Long): Maybe<Movie> = Maybe.fromCallable { movieCache[id] }

    override fun getSimilarMovies(
        id: Long,
        page: Int,
    ): Single<Movies> =
        service.getSimilarMovies(id, page)
            .map { mapper.map(it) }
            .doOnSuccess {
                it.movies.forEach {
                    movieCache[it.id] = it
                }
            }
}
