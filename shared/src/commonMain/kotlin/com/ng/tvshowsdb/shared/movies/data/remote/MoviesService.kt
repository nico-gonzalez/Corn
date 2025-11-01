package com.ng.tvshowsdb.shared.movies.data.remote

import com.ng.tvshowsdb.shared.movies.data.remote.model.MovieResponse
import com.ng.tvshowsdb.shared.network.client.BASE_URL
import com.ng.tvshowsdb.shared.network.client.response.ApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class MoviesService(
    private val httpClient: HttpClient
) {

    suspend fun getMostPopularMovies(): ApiResponse<List<MovieResponse>> {
        return httpClient.get("$BASE_URL/movie/popular") {
            parameter("api_key", "509988efd574a450a18d2b779f3ed6a7")
        }.body()
    }

    suspend fun getTopRatedMovies(): ApiResponse<List<MovieResponse>> {
        return httpClient.get("$BASE_URL/movie/top_rated") {
            parameter("api_key", "509988efd574a450a18d2b779f3ed6a7")
        }.body()
    }
}