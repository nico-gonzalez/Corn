package com.ng.tvshowsdb.common.injection.modules

import com.ng.tvshowsdb.data.shows.TheMovieDbTvShowRepository
import com.ng.tvshowsdb.domain.repository.TvShowRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class
    ]
)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTvShowRepository(tvShowRepository: TheMovieDbTvShowRepository): TvShowRepository
}