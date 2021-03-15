package com.ng.tvshowsdb.common.injection.components

import android.app.Application
import com.ng.tvshowsdb.common.TvShowsDbApplication
import com.ng.tvshowsdb.common.injection.modules.ApplicationModule
import com.ng.tvshowsdb.core.ui.common.di.module.ViewModelFactoryModule
import com.ng.tvshowsdb.core.ui.common.di.scopes.PerApplication
import com.ng.tvshowsdb.home.ui.di.HomeActivityBindingModule
import com.ng.tvshowsdb.home.ui.di.HomeComponent
import com.ng.tvshowsdb.home.ui.di.HomeViewModelModule
import com.ng.tvshowsdb.shows.di.ShowsActivityBindingModule
import com.ng.tvshowsdb.shows.di.ShowsComponent
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        ShowsActivityBindingModule::class,
        ViewModelFactoryModule::class,
        HomeActivityBindingModule::class
    ],
    dependencies = [
        HomeComponent::class,
        ShowsComponent::class,
    ]
)
interface ApplicationComponent : AndroidInjector<TvShowsDbApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun homeComponent(homeComponent: HomeComponent): Builder
        fun showsComponent(showsComponent: ShowsComponent): Builder

        fun build(): ApplicationComponent
    }
}
