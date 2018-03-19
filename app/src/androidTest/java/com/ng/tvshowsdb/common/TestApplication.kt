package com.ng.tvshowsdb.common

import com.ng.tvshowsdb.common.injection.DaggerTestComponent
import com.ng.tvshowsdb.common.injection.MockApplicationModule
import com.ng.tvshowsdb.common.injection.TestComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class TestApplication : TvShowsDbApplication() {

  private val testComponent by lazy {
    DaggerTestComponent.builder()
        .mockApplicationModule(MockApplicationModule())
        .build()
  }

  fun applicationComponent(): TestComponent = testComponent

  override fun applicationInjector(): AndroidInjector<out DaggerApplication>? = testComponent

}