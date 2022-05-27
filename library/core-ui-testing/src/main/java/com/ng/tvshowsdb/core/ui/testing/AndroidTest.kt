package com.ng.tvshowsdb.core.ui.testing

import androidx.test.InstrumentationRegistry
import com.ng.tvshowsdb.core.ui.testing.injection.TestApplicationComponent

open class AndroidTest<T : TestApplicationComponent> {

    fun application(): TestApplication = InstrumentationRegistry.getTargetContext()
        .applicationContext as TestApplication

    fun applicationComponent(): T = application().applicationComponent() as T

}