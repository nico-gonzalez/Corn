package com.ng.tvshowsdb.shared.movies.data.remote

import com.ng.tvshowsdb.shared.movies.data.remote.model.MovieResponse
import com.ng.tvshowsdb.shared.network.client.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class MoviesService(
    private val httpClient: HttpClient
) {

    suspend fun getMostPopularMovies(): List<MovieResponse> {
        return httpClient.get("$BASE_URL/movies/popular").body()
    }
}