package com.ng.tvshowsdb.shows.data.di

import com.ng.tvshowsdb.shows.data.repository.TvShowRepositoryImpl
import com.ng.tvshowsdb.shows.domain.repository.TvShowRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(
    includes = [
        ShowsNetworkModule::class
    ]
)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTvShowRepository(tvShowRepository: TvShowRepositoryImpl): TvShowRepository
}
