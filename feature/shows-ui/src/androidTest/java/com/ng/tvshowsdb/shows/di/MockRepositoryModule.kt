package com.ng.tvshowsdb.shows.di

import com.ng.tvshowsdb.shows.api.domain.repository.TvShowRepository
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object MockRepositoryModule {
    @Singleton
    @Provides
    fun provideTvShowRepository(): TvShowRepository = mock()
}