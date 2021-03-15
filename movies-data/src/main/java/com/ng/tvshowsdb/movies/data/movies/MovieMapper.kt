package com.ng.tvshowsdb.movies.data.movies

import com.ng.tvshowsdb.movies.data.remote.model.ApiMovies
import com.ng.tvshowsdb.movies.domain.model.Movie
import com.ng.tvshowsdb.movies.domain.model.Movies
import javax.inject.Inject

class MovieMapper @Inject constructor() {

    fun map(apiMovies: ApiMovies): Movies = with(apiMovies) {
        Movies(
            movies = results.map { tvShow ->
                Movie(
                    id = tvShow.id,
                    title = tvShow.title.orEmpty(),
                    description = tvShow.overview.orEmpty(),
                    posterPath = tvShow.poster_path.orEmpty(),
                    backdropPath = tvShow.backdrop_path.orEmpty(),
                    releaseDate = tvShow.release_date.orEmpty(),
                    rating = tvShow.vote_average ?: 0.0
                )
            },
            page,
            total_pages
        )
    }
}
