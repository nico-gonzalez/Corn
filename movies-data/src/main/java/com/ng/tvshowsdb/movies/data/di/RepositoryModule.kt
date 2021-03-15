package com.ng.tvshowsdb.movies.data.di

import com.ng.tvshowsdb.movies.data.movies.MovieRepositoryImpl
import com.ng.tvshowsdb.movies.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(
    includes = [
        MovieNetworkModule::class
    ]
)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindMovieRepository(movieRepository: MovieRepositoryImpl): MovieRepository
}
