package com.ng.tvshowsdb.common.injection.modules

import android.app.Application
import android.content.Context
import com.ng.tvshowsdb.common.SchedulerProviderImpl
import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import com.ng.tvshowsdb.core.ui.common.di.scopes.PerApplication
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {

    @PerApplication
    @Binds
    abstract fun provideContext(application: Application): Context

    @PerApplication
    @Binds
    abstract fun provideSchedulers(schedulerProvider: SchedulerProviderImpl): SchedulerProvider
}