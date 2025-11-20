package com.ng.tvshowsdb.shared

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.R
import androidx.core.content.ContextCompat
import com.ng.tvshowsdb.shared.app.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                darkScrim = ContextCompat.getColor(this, android.R.color.black),
                lightScrim = ContextCompat.getColor(this, android.R.color.white),
            )
        )
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}