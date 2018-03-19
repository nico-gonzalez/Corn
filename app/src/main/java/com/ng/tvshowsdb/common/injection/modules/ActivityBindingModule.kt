package com.ng.tvshowsdb.common.injection.modules

import com.ng.tvshowsdb.common.injection.scopes.PerActivity
import com.ng.tvshowsdb.detail.ShowDetailActivity
import com.ng.tvshowsdb.shows.TvShowsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

  @PerActivity
  @ContributesAndroidInjector(modules = [TvShowsActivityModule::class])
  abstract fun bindTvShowsActivity(): TvShowsActivity

  @PerActivity
  @ContributesAndroidInjector(modules = [ShowDetailActivityModule::class])
  abstract fun bindShowDetailActivity(): ShowDetailActivity

}