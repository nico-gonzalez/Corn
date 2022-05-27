package com.ng.tvshowsdb.shows.list.di

import com.ng.tvshowsdb.core.ui.common.di.scopes.PerActivity
import com.ng.tvshowsdb.shows.list.ShowsActivity
import com.ng.tvshowsdb.shows.list.ShowsView
import dagger.Module
import dagger.Provides

@Module
class TvShowsActivityModule {

    @PerActivity
    @Provides
    fun provideTvShowsView(showsActivity: ShowsActivity): ShowsView = showsActivity

}