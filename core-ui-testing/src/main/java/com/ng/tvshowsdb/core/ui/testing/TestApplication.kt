package com.ng.tvshowsdb.core.ui.testing

import android.app.Application
import com.ng.tvshowsdb.core.ui.testing.injection.BaseApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector

class TestApplication : Application(), HasAndroidInjector {

    var testComponent: BaseApplicationComponent? = null

    fun applicationComponent(): BaseApplicationComponent = testComponent!!

    override fun androidInjector(): AndroidInjector<Any> = testComponent!!
}