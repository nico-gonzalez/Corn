package com.ng.tvshowsdb.home.ui.di

import android.content.Context
import com.ng.tvshowsdb.shows.domain.repository.TvShowRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        HomeViewModelModule::class
    ]
)
interface HomeComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): HomeComponent
    }
}
