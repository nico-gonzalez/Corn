package com.ng.tvshowsdb.shows.di

import com.ng.tvshowsdb.core.ui.common.di.scopes.PerActivity
import com.ng.tvshowsdb.shows.detail.ShowDetailActivity
import com.ng.tvshowsdb.shows.detail.di.ShowDetailActivityModule
import com.ng.tvshowsdb.shows.list.ShowsActivity
import com.ng.tvshowsdb.shows.list.di.TvShowsActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ShowsActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [TvShowsActivityModule::class])
    abstract fun bindTvShowsActivity(): ShowsActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [ShowDetailActivityModule::class])
    abstract fun bindShowDetailActivity(): ShowDetailActivity

}