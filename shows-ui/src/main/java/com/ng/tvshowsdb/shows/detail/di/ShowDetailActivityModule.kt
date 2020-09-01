package com.ng.tvshowsdb.shows.detail.di

import com.ng.tvshowsdb.core.ui.common.di.scopes.PerActivity
import com.ng.tvshowsdb.shows.detail.ShowDetailActivity
import com.ng.tvshowsdb.shows.detail.ShowDetailView
import dagger.Binds
import dagger.Module

@Module
abstract class ShowDetailActivityModule {

    @PerActivity
    @Binds
    abstract fun provideShowDetailView(showDetailActivity: ShowDetailActivity): ShowDetailView
}