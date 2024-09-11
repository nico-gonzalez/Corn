package com.ng.tvshowsdb.shows.di

import com.ng.tvshowsdb.shows.api.data.di.RepositoryModule
import dagger.Module

@Module(
    includes = [RepositoryModule::class]
)
internal object ShowsModule