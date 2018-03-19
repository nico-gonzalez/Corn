package com.ng.tvshowsdb.common

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Ignore
import org.junit.runner.RunWith

open class AndroidTest {

  private fun application(): TestApplication = InstrumentationRegistry.getTargetContext()
      .applicationContext as TestApplication

  fun applicationComponent() = application().applicationComponent()

}