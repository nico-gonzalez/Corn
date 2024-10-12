package com.ng.tvshowsdb.core.ui.testing

import androidx.test.platform.app.InstrumentationRegistry
import com.ng.tvshowsdb.core.ui.testing.injection.TestApplicationComponent

open class AndroidTest<T : TestApplicationComponent> {

    fun application(): TestApplication = InstrumentationRegistry.getInstrumentation().context
        .applicationContext as TestApplication

    @Suppress("UNCHECKED_CAST")
    fun applicationComponent(): T = application().applicationComponent() as T

}