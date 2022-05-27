package com.ng.tvshowsdb.core.ui.testing

import android.app.Application
import com.ng.tvshowsdb.core.ui.testing.injection.TestApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class TestApplication : Application(), HasAndroidInjector {

    @Inject
    @Volatile
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    var testComponent: TestApplicationComponent? = null
        set(value) {
            field = field ?: value.apply { this?.inject(this@TestApplication) }
        }

    fun applicationComponent(): TestApplicationComponent = testComponent!!

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}