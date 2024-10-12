package com.ng.tvshowsdb.shared.app

import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CornAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navigationIcon = @Composable {
        IconButton(onClick = navigateUp) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Navigate Up"
            )
        }
    }
    TopAppBar(
        windowInsets = AppBarDefaults.topAppBarWindowInsets,
        title = { Text(text = title) },
        modifier = modifier,
        navigationIcon = navigationIcon.takeIf { canNavigateBack }
    )
}