package com.ng.tvshowsdb.core.ui.testing

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class CustomAndroidJUnitTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application = super.newApplication(cl, TestApplication::class.java.name, context)
}