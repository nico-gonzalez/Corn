package com.ng.tvshowsdb.shared.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ng.tvshowsdb.shared.app.CornAppBar
import com.ng.tvshowsdb.shared.di.DependencyGraph
import com.ng.tvshowsdb.shared.home.domain.model.Home
import com.ng.tvshowsdb.shared.movie.presentation.MovieCardItem
import com.ng.tvshowsdb.shared.movies.domain.model.Movie
import kotlin.reflect.KClass

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = viewModel<HomeScreenViewModel>(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: KClass<T>,
                extras: CreationExtras
            ): T {
                return DependencyGraph.provideMostPopularMoviesViewModel() as T
            }
        }
    ),
    onNavigateToMovieDetail: (Movie) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CornAppBar(
                title = "Discover",
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when {
                state.isLoading -> LoadingScreen()
                state.error != null -> ErrorScreen(requireNotNull(state.error?.message))
                state.home != null -> HomeScreenContent(
                    home = requireNotNull(state.home),
                    isRefreshing = state.isRefreshing,
                    onRefresh = viewModel::onRefresh,
                    onNavigateToMovieDetail = onNavigateToMovieDetail
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeScreenContent(
    home: Home,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onNavigateToMovieDetail: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {

    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh)

    Box(modifier = modifier.pullRefresh(pullRefreshState)) {
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {

            home.mostPopularMovies?.let { movies ->
                HomeScreenSection(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight(),
                    title = "Most popular movies",
                    items = movies,
                    key = { it.id },
                    itemContent = { movie ->
                        MovieCardItem(
                            movie = movie,
                            onNavigateToMovieDetail = onNavigateToMovieDetail,
                        )
                    },
                )
            }

            home.topRatedMovies?.let { movies ->
                HomeScreenSection(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight(),
                    title = "Top rated movies",
                    items = movies,
                    key = { it.id },
                    itemContent = { movie ->
                        MovieCardItem(
                            movie = movie,
                            onNavigateToMovieDetail = onNavigateToMovieDetail,
                        )
                    },
                )
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun ErrorScreen(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message)
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
