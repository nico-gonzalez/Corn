package com.ng.tvshowsdb.core.ui.testing

import androidx.test.InstrumentationRegistry

open class TestRobot {

    fun application(): TestApplication = InstrumentationRegistry.getTargetContext()
        .applicationContext as TestApplication
}