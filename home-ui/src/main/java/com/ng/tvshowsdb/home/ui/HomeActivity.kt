package com.ng.tvshowsdb.home.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.ng.tvshowsdb.core.ui.BuildConfig
import com.ng.tvshowsdb.home.ui.theme.CornTheme
import com.ng.tvshowsdb.shows.detail.ShowDetailActivity
import com.ng.tvshowsdb.shows.domain.model.TvShow
import dagger.android.AndroidInjection
import dev.chrisbanes.accompanist.glide.GlideImage
import javax.inject.Inject

enum class Tabs(val tabName: String) {
    SHOWS("TvShows"),
    MOVIES("Movies")
}

class HomeActivity : ComponentActivity() {

    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory

    private val homeViewModel by viewModels<HomeViewModel> { providerFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            CornTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold {
                        HomeContent(
                            homeViewModel = homeViewModel,
                            onNavigateToShowDetail = { navigateToShowDetail(it.id) }
                        )
                    }
                }
            }
        }
    }

    private fun navigateToShowDetail(showId: Long) {
        startActivity(ShowDetailActivity.startIntent(this, showId))
    }
}

@Composable
fun HomeContent(
    homeViewModel: HomeViewModel,
    onNavigateToShowDetail: (TvShow) -> Unit = {},
) {
    Box {
        var selectedTab by remember { mutableStateOf(Tabs.SHOWS) }
        val viewState by homeViewModel.viewState.observeAsState(initial = HomeViewModel.ViewState.Loading)
        when (val state = viewState) {
            is HomeViewModel.ViewState.GetTvShowsError -> Text(text = "Oops, something went wrong")
            is HomeViewModel.ViewState.TvShows -> {
                Column {
                    if (state.latest != null) {
                        val latestShow = state.latest
                        LatestShowPoster(latestShow) { onNavigateToShowDetail(it) }
                    }

                    if (state.mostPopular.isNotEmpty()) {
                        Text(text = "Most popular", modifier = Modifier.padding(16.dp))
                        HorizontalShowsList(
                            shows = state.mostPopular,
                            onShowSelected = { onNavigateToShowDetail(it) }
                        )
                    }

                    if (state.topRated.isNotEmpty()) {
                        Text(text = "Top rated", modifier = Modifier.padding(16.dp))
                        HorizontalShowsList(
                            shows = state.topRated,
                            onShowSelected = { onNavigateToShowDetail(it) }
                        )
                    }
                }
            }

            HomeViewModel.ViewState.Loading ->
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        HomeTabs(selectedTab) { selectedTab = it }
    }
}

@Composable
fun LatestShowPoster(
    show: TvShow,
    onShowSelected: (TvShow) -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.4f)
        .clickable { onShowSelected(show) }
    ) {
        GlideImage(
            modifier = Modifier.fillMaxSize(),
            data = "${BuildConfig.IMAGE_BASE_URL}/w500/${show.backdropPath}",
            contentDescription = show.description,
            contentScale = ContentScale.Crop,
            loading = {
                Box(Modifier.matchParentSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            },
            error = { /*TODO*/ }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Black, Color.Transparent, Color.Black),
                        startY = 1f
                    )
                )
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
        ) {
            Text(text = show.title, modifier = Modifier.padding(end = 4.dp))
            Icon(Icons.Default.Info,
                contentDescription = "Show details",
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .align(Alignment.CenterVertically))
        }

    }
}

@Composable
fun HorizontalShowsList(
    shows: List<TvShow>,
    onShowSelected: (TvShow) -> Unit,
) {
    Column {
        LazyRow(contentPadding = PaddingValues(start = 16.dp)) {
            items(count = shows.size) { index ->
                val show = shows[index]
                ShowCard(show, onShowSelected)
            }
        }
    }
}

@Composable
private fun ShowCard(
    show: TvShow,
    onShowSelected: (TvShow) -> Unit,
) {
    Box(modifier = Modifier
        .padding(end = 8.dp)
        .size(100.dp, 180.dp)
    ) {
        Card(
            elevation = 4.dp,
            modifier = Modifier.clickable { onShowSelected(show) }
        ) {
            GlideImage(
                modifier = Modifier.fillMaxSize(),
                data = "${BuildConfig.IMAGE_BASE_URL}/w500/${show.posterPath}",
                contentDescription = show.description,
                contentScale = ContentScale.Crop,
                loading = {
                    Box(Modifier.matchParentSize()) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                },
                error = { /*TODO*/ }
            )
        }
    }
}

@Composable
fun HomeTabs(selectedTab: Tabs, onTabSelected: (tab: Tabs) -> Unit) {
    TabRow(
        selectedTabIndex = selectedTab.ordinal,
        backgroundColor = Color.Transparent,
        indicator = {},
        divider = {}
    ) {
        Tabs.values().forEachIndexed { index, tab ->
            val selected = index == selectedTab.ordinal
            Tab(
                selected = selected,
                text = { Text(text = tab.tabName) },
                onClick = { onTabSelected(tab) }
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    CornTheme {
//        HomeContent(homeViewModel) {}
//    }
//}
