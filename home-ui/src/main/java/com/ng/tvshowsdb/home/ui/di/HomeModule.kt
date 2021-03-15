package com.ng.tvshowsdb.home.ui.di

import com.ng.tvshowsdb.shows.data.di.RepositoryModule
import dagger.Module

@Module(
    includes = [RepositoryModule::class]
)
internal object HomeModule
