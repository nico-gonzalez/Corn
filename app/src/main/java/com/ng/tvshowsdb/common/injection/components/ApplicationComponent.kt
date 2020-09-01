package com.ng.tvshowsdb.common.injection.components

import android.app.Application
import com.ng.tvshowsdb.common.TvShowsDbApplication
import com.ng.tvshowsdb.common.injection.modules.ApplicationModule
import com.ng.tvshowsdb.core.ui.common.di.scopes.PerApplication
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
        ShowsActivityBindingModule::class
    ],
    dependencies = [
        ShowsComponent::class
    ]
)
interface ApplicationComponent : AndroidInjector<TvShowsDbApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun showsComponent(showsComponent: ShowsComponent): Builder

        fun build(): ApplicationComponent
    }
}