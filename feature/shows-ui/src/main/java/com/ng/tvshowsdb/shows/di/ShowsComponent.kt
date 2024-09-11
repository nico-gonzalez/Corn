package com.ng.tvshowsdb.shows.di

import android.content.Context
import com.ng.tvshowsdb.shows.api.domain.repository.TvShowRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ShowsModule::class
    ]
)
interface ShowsComponent {

    fun tvShowRepository(): TvShowRepository

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): ShowsComponent
    }
}