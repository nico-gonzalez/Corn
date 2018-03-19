package com.ng.tvshowsdb.common.injection.components

import android.content.Context
import com.ng.tvshowsdb.common.injection.modules.NetworkModule
import com.ng.tvshowsdb.common.injection.modules.RepositoryModule
import com.ng.tvshowsdb.domain.repository.TvShowRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
  RepositoryModule::class, NetworkModule::class
])
interface DataComponent {

  fun tvShowRepository(): TvShowRepository

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun context(context: Context): Builder

    fun build(): DataComponent
  }
}