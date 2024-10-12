package com.ng.tvshowsdb.shared.di

import com.ng.tvshowsdb.shared.app.AppBarStateDelegate
import com.ng.tvshowsdb.shared.executor.Executor
import com.ng.tvshowsdb.shared.home.domain.action.GetHome
import com.ng.tvshowsdb.shared.home.presentation.HomeScreenViewModel
import com.ng.tvshowsdb.shared.movie.presentation.MovieViewModel
import com.ng.tvshowsdb.shared.movies.data.MovieRepository
import com.ng.tvshowsdb.shared.movies.data.mapper.MovieMapper
import com.ng.tvshowsdb.shared.movies.data.remote.MoviesService
import com.ng.tvshowsdb.shared.movies.domain.action.GetMostPopularMovies
import com.ng.tvshowsdb.shared.movies.domain.action.GetTopRatedMovies
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json

internal object DependencyGraph {

    var movieRepository: MovieRepository? = null
        private set
    var appBarStateDelegate: AppBarStateDelegate = AppBarStateDelegate()
        private set

    fun provideMostPopularMoviesViewModel() = HomeScreenViewModel(
        getHome = provideGetHome()
    )

    fun provideMovieViewModel(movieId: Long) = MovieViewModel(
        movieId = movieId,
        appBarStateDelegate = appBarStateDelegate,
        movieRepository = provideMovieRepository()
    )

    private fun provideGetMostPopularMovies() = GetMostPopularMovies(
        executor = provideExecutor(),
        moviesService = provideMoviesService(),
        mapper = provideMovieMapper(),
        movieRepository = provideMovieRepository()
    )

    private fun provideGetTopRatedMovies() = GetTopRatedMovies(
        executor = provideExecutor(),
        moviesService = provideMoviesService(),
        mapper = provideMovieMapper(),
        movieRepository = provideMovieRepository()
    )

    private fun provideGetHome() = GetHome(
        executor = provideExecutor(),
        getMostPopularMovies = provideGetMostPopularMovies(),
        getTopRatedMovies = provideGetTopRatedMovies(),
    )

    private fun provideMovieRepository() = movieRepository ?: MovieRepository().also {
        movieRepository = it
    }

    private fun provideMovieMapper() = MovieMapper()

    private fun provideMoviesService() = MoviesService(httpClient = httpClient)

    private fun provideExecutor() = Executor(dispatcher = Dispatchers.Default)

    private val httpClient = HttpClient {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL

            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        defaultRequest {
            parameters {
                append("api_key", "509988efd574a450a18d2b779f3ed6a7")
            }
        }
    }

}