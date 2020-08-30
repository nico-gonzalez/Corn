package com.ng.tvshowsdb.common

import androidx.test.InstrumentationRegistry

open class AndroidTest {

    private fun application(): TestApplication = InstrumentationRegistry.getTargetContext()
        .applicationContext as TestApplication

    fun applicationComponent() = application().applicationComponent()

}