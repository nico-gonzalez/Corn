package com.ng.tvshowsdb.shared.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationDefaults
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ng.tvshowsdb.shared.app.theme.CornTheme
import com.ng.tvshowsdb.shared.movie.presentation.MovieScreen
import com.ng.tvshowsdb.shared.home.presentation.HomeScreen

@Composable
fun App() {
    CornTheme {
        CornApp()
    }
}


@Composable
private fun CornApp(
    navController: NavHostController = rememberNavController(),
) {

    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Movies", "Series")

    Scaffold(
        bottomBar = {
            BottomNavigation(
                modifier = Modifier.fillMaxWidth(),
                windowInsets = BottomNavigationDefaults.windowInsets
            ) {
                items.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = null
                            )
                        },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index })
                }
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Routes.Home,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable<Routes.Home> {
                HomeScreen(
                    navController = navController,
                    onNavigateToMovieDetail = { movie ->
                        navController.navigate(Routes.Movie(id = movie.id))
                    }
                )
            }
            composable<Routes.Movie> { backStackEntry ->
                val movie = backStackEntry.toRoute<Routes.Movie>()
                MovieScreen(
                    navController = navController,
                    movieId = movie.id
                )
            }
        }
    }

}

