package com.ng.tvshowsdb.shows.di

import com.ng.tvshowsdb.shows.api.domain.repository.TvShowRepository
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

@Module
object MockRepositoryModule {
    @Singleton
    @Provides
    fun provideTvShowRepository(): TvShowRepository = mockk()
}