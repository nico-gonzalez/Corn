package com.ng.tvshowsdb.common.injection.modules

import com.ng.tvshowsdb.common.injection.scopes.PerActivity
import com.ng.tvshowsdb.presentation.shows.TvShowsView
import com.ng.tvshowsdb.shows.TvShowsActivity
import dagger.Module
import dagger.Provides

@Module
class TvShowsActivityModule {

    @PerActivity
    @Provides
    fun provideTvShowsView(tvShowsActivity: TvShowsActivity): TvShowsView = tvShowsActivity

}