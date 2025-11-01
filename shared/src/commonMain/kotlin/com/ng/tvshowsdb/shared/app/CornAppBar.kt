package com.ng.tvshowsdb.shared.app

import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import corn.shared.generated.resources.Res
import corn.shared.generated.resources.arrow_back
import org.jetbrains.compose.resources.painterResource

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
                painter = painterResource(Res.drawable.arrow_back),
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