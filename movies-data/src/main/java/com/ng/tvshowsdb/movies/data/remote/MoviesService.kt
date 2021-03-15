package com.ng.tvshowsdb.movies.data.remote

import com.ng.tvshowsdb.movies.data.remote.model.ApiMovies
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    fun getMostPopularMovies(@Query("page") page: Int): Single<ApiMovies>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovies(
        @Path("movie_id") id: Long,
        @Query("page") page: Int = 1,
    ): Single<ApiMovies>
}
