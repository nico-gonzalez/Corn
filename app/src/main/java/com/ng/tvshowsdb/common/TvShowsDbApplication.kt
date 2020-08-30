package com.ng.tvshowsdb.common

import androidx.appcompat.app.AppCompatDelegate
import com.ng.tvshowsdb.common.injection.components.DaggerApplicationComponent
import com.ng.tvshowsdb.common.injection.components.DaggerDataComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class TvShowsDbApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
        val daggerDataComponent = DaggerDataComponent.builder()
            .context(this)
            .build()
        val applicationComponent = DaggerApplicationComponent.builder()
            .application(this)
            .dataComponent(daggerDataComponent)
            .build()
        applicationComponent.inject(this)
        return applicationComponent
    }
}