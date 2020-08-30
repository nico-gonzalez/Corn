package com.ng.tvshowsdb.common.injection

import com.ng.tvshowsdb.common.injection.components.ApplicationComponent
import com.ng.tvshowsdb.common.injection.modules.ActivityBindingModule
import com.ng.tvshowsdb.details.ShowDetailsActivityTest
import com.ng.tvshowsdb.shows.TvShowsActivityTest
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    MockApplicationModule::class
  ]
)
interface TestComponent : ApplicationComponent {

    fun inject(tvShowsActivityTest: TvShowsActivityTest)

    fun inject(showDetailsActivityTest: ShowDetailsActivityTest)

}