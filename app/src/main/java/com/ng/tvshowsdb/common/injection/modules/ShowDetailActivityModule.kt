package com.ng.tvshowsdb.common.injection.modules

import com.ng.tvshowsdb.common.injection.scopes.PerActivity
import com.ng.tvshowsdb.detail.ShowDetailActivity
import com.ng.tvshowsdb.presentation.detail.ShowDetailView
import dagger.Module
import dagger.Provides

@Module
class ShowDetailActivityModule {

    @PerActivity
    @Provides
    fun provideShowDetailView(showDetailActivity: ShowDetailActivity): ShowDetailView =
        showDetailActivity


}