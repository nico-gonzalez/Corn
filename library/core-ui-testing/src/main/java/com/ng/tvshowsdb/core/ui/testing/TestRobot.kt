package com.ng.tvshowsdb.core.ui.testing

import androidx.test.platform.app.InstrumentationRegistry

open class TestRobot {

    fun application(): TestApplication = InstrumentationRegistry.getInstrumentation().context
        .applicationContext as TestApplication
}