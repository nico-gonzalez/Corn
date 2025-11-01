package com.ng.tvshowsdb.shared.movies.data.mapper

import com.ng.tvshowsdb.shared.movies.data.remote.model.MovieResponse
import com.ng.tvshowsdb.shared.movies.domain.model.Movie

internal class MovieMapper {
    fun map(response: MovieResponse): Movie = with(response) {
        Movie(
            id = id,
            title = title,
            originalLanguage = originalLanguage,
            overview = overview,
            releaseDate = releaseDate,
            imageUrl = "https://image.tmdb.org/t/p/w500/$backdropPath",
            posterImageUrl = "https://image.tmdb.org/t/p/w500/$posterPath",
            voteAverage = voteAverage,
            voteCount = voteCount,
            genreIds = genreIds,
            popularity = popularity,
            isAdult = adult,
            originalTitle = originalTitle,
            hasVideo = video,
        )
    }
}