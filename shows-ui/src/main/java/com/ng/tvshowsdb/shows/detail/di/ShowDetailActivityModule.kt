package com.ng.tvshowsdb.shows.detail.di

import com.ng.tvshowsdb.core.ui.common.di.scopes.PerActivity
import com.ng.tvshowsdb.shows.detail.ShowDetailActivity
import com.ng.tvshowsdb.shows.detail.ShowDetailView
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
abstract class ShowDetailActivityModule {

    @PerActivity
    @Binds
    abstract fun provideShowDetailView(showDetailActivity: ShowDetailActivity): ShowDetailView

    companion object {
        @PerActivity
        @Provides
        @Named("showId")
        fun provideShowId(showDetailActivity: ShowDetailActivity): Long =
            showDetailActivity.extractShowIdExtra()
    }
}
