package com.ng.tvshowsdb.shared.app.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Composable
fun CornTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content,
        colors = if (isSystemInDarkTheme()) {
            darkColors()
        } else {
            lightColors()
        }
    )
}