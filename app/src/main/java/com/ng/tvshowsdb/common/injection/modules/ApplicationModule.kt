package com.ng.tvshowsdb.common.injection.modules

import android.app.Application
import android.content.Context
import com.ng.tvshowsdb.common.SchedulerProviderImpl
import com.ng.tvshowsdb.common.injection.scopes.PerApplication
import com.ng.tvshowsdb.domain.common.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @PerApplication
    @Provides
    fun provideContext(application: Application): Context = application

    @PerApplication
    @Provides
    fun provideSchedulers(): SchedulerProvider = SchedulerProviderImpl()

}