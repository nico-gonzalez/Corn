package com.ng.tvshowsdb.core.ui.testing.injection

import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import com.ng.tvshowsdb.core.ui.testing.ImmediateSchedulers
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class MockApplicationModule {

    @Singleton
    @Binds
    abstract fun provideSchedulerProvider(immediateSchedulers: ImmediateSchedulers): SchedulerProvider
}