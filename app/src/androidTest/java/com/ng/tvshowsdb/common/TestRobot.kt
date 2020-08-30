package com.ng.tvshowsdb.common

import androidx.test.InstrumentationRegistry

open class TestRobot {

    fun application(): TestApplication = InstrumentationRegistry.getTargetContext()
        .applicationContext as TestApplication
}