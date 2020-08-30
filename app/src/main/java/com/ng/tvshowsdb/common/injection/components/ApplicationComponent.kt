package com.ng.tvshowsdb.common.injection.components

import android.app.Application
import com.ng.tvshowsdb.common.TvShowsDbApplication
import com.ng.tvshowsdb.common.injection.modules.ActivityBindingModule
import com.ng.tvshowsdb.common.injection.modules.ApplicationModule
import com.ng.tvshowsdb.common.injection.scopes.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(
    modules = [
        ActivityBindingModule::class, AndroidSupportInjectionModule::class, ApplicationModule::class
    ],
    dependencies = [
        DataComponent::class
    ]
)
interface ApplicationComponent : AndroidInjector<TvShowsDbApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun dataComponent(dataComponent: DataComponent): Builder

        fun build(): ApplicationComponent
    }
}