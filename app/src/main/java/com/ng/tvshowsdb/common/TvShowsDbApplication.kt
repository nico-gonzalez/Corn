package com.ng.tvshowsdb.common

import androidx.appcompat.app.AppCompatDelegate
import com.ng.tvshowsdb.common.injection.components.DaggerApplicationComponent
import com.ng.tvshowsdb.core.ui.common.BaseApplication
import com.ng.tvshowsdb.shows.di.DaggerShowsComponent
import dagger.android.AndroidInjector

open class TvShowsDbApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    override fun applicationInjector(): AndroidInjector<out BaseApplication>? {
        val showsComponent = DaggerShowsComponent.builder()
            .context(this)
            .build()
        return DaggerApplicationComponent.builder()
            .application(this)
            .showsComponent(showsComponent)
            .build()
            .also { it.inject(this) }
    }
}