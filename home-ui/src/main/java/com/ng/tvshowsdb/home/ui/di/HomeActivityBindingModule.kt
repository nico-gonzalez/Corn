package com.ng.tvshowsdb.home.ui.di

import com.ng.tvshowsdb.core.ui.common.di.scopes.PerActivity
import com.ng.tvshowsdb.home.ui.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [HomeViewModelModule::class])
abstract class HomeActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector
    abstract fun bindHomeActivity(): HomeActivity
}
