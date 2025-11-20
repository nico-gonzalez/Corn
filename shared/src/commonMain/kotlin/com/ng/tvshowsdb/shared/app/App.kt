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
import com.ng.tvshowsdb.shared.home.presentation.HomeScreen
import com.ng.tvshowsdb.shared.movie.presentation.MovieScreen
import org.jetbrains.compose.resources.painterResource
import corn.shared.generated.resources.Res
import corn.shared.generated.resources.arrow_back
import corn.shared.generated.resources.favorite
import corn.shared.generated.resources.favorite_filled
import corn.shared.generated.resources.star
import corn.shared.generated.resources.star_filled
import org.jetbrains.compose.resources.DrawableResource

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
                    val isSelected = selectedItem == index
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(resolveIcon(index, selectedItem)),
                                contentDescription = null
                            )
                        },
                        label = { Text(item) },
                        selected = isSelected,
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

private fun resolveIcon(index: Int, selectedItem: Int): DrawableResource =
    when(index) {
        0 -> if (selectedItem == index) Res.drawable.star_filled else Res.drawable.star
        1 -> if (selectedItem == index) Res.drawable.favorite_filled else Res.drawable.favorite
        2 -> if(selectedItem == index) Res.drawable.arrow_back else Res.drawable.arrow_back
        else -> throw IllegalArgumentException("Unknown index $index")
    }
