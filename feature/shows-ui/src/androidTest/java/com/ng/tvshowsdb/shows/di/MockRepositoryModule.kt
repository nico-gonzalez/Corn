package com.ng.tvshowsdb.shows.di

import com.ng.tvshowsdb.shows.api.domain.repository.TvShowRepository
import com.ng.tvshowsdb.shows.fixtures.FakeTvShowRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface MockRepositoryModule {
    @Singleton
    @Binds
    fun provideTvShowRepository(repository: FakeTvShowRepository): TvShowRepository
}