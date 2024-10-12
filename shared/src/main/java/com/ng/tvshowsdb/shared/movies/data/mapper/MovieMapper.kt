package com.ng.tvshowsdb.shared.movies.data.mapper

import com.ng.tvshowsdb.shared.movies.data.remote.model.MovieResponse
import com.ng.tvshowsdb.shared.movies.domain.model.Movie

internal class MovieMapper {
    fun map(response: MovieResponse): Movie = with(response) {
        Movie(
            id = id,
            name = name
        )
    }
}