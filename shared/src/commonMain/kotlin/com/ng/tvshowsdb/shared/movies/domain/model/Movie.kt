package com.ng.tvshowsdb.shared.movies.domain.model

import com.ng.tvshowsdb.shared.ktx.format

data class Movie(
    val isAdult: Boolean,
    val imageUrl: String,
    val genreIds: List<Int>,
    val id: Long,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Float,
    val posterImageUrl: String,
    val releaseDate: String,
    val title: String,
    val hasVideo: Boolean,
    val voteAverage: Float,
    val voteCount: Int
) {

    val displayRating: String
        get() = voteAverage.format(1)
}
