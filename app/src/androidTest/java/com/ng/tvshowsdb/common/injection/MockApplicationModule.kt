package com.ng.tvshowsdb.common.injection

import com.ng.tvshowsdb.domain.common.SchedulerProvider
import com.ng.tvshowsdb.domain.repository.TvShowRepository
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class MockApplicationModule {

    @Singleton
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = object : SchedulerProvider {
        override fun io(): Scheduler = Schedulers.trampoline()

        override fun ui(): Scheduler = AndroidSchedulers.mainThread()
    }

    @Singleton
    @Provides
    fun providesTvShowsRepository(): TvShowRepository = mock()
}