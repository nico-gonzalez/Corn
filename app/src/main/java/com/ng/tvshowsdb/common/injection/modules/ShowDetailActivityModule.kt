package com.ng.tvshowsdb.common.injection.modules

import com.ng.tvshowsdb.common.injection.scopes.PerActivity
import com.ng.tvshowsdb.detail.ShowDetailActivity
import com.ng.tvshowsdb.presentation.detail.ShowDetailView
import dagger.Binds
import dagger.Module

@Module
abstract class ShowDetailActivityModule {

    @PerActivity
    @Binds
    abstract fun provideShowDetailView(showDetailActivity: ShowDetailActivity): ShowDetailView
}