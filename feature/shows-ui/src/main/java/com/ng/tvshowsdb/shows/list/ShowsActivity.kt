package com.ng.tvshowsdb.shows.list

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ng.tvshowsdb.core.ui.common.view.GridSpacingItemDecoration
import com.ng.tvshowsdb.shows.R
import com.ng.tvshowsdb.shows.databinding.ActivityShowsBinding
import com.ng.tvshowsdb.shows.detail.ShowDetailActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

private const val GRID_COLUMNS = 2

class ShowsActivity : DaggerAppCompatActivity(), ShowsView {

    @Inject
    lateinit var showsPresenter: ShowsPresenter

    private lateinit var binding: ActivityShowsBinding
    private lateinit var showsAdapter: ShowsAdapter
    private lateinit var showsLayoutManager: GridLayoutManager

    override fun setPresenter(presenter: ShowsPresenter) {
        this.showsPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        showsAdapter =
            ShowsAdapter(R.layout.show_grid_item, R.layout.loading_show_card, { position ->
                showsPresenter.onShowSelected(position, showsAdapter.getItemId(position))
            }, {
                showsPresenter.onShowMoreShows()
            })

        showsLayoutManager = GridLayoutManager(this, GRID_COLUMNS)
        showsLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (showsAdapter.getItemViewType(position) == LOADING_TYPE) 2 else 1
            }
        }

        binding.showsRv.apply {
            adapter = showsAdapter
            layoutManager = showsLayoutManager
            setHasFixedSize(true)
            val divider = resources.getDimensionPixelSize(com.ng.tvshowsdb.core.ui.common.R.dimen.divider_size)
            addItemDecoration(GridSpacingItemDecoration(GRID_COLUMNS, divider, false, 0))
        }

        showsPresenter.onShowMostPopularTvShows()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        showsLayoutManager.spanCount = when (newConfig.orientation) {
            ORIENTATION_LANDSCAPE -> GRID_COLUMNS + 1
            else -> GRID_COLUMNS
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        showsPresenter.detach()
    }

    override fun showShows(shows: List<ShowItem>) {
        showsAdapter.submitList(ArrayList(shows))
    }

    override fun showLoading() {
        binding.progressBar.visibility = VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = GONE
    }

    override fun showError() {
        Snackbar.make(binding.showsRv, com.ng.tvshowsdb.core.ui.common.R.string.error_loading_popular_shows, Snackbar.LENGTH_LONG)
            .setAction(com.ng.tvshowsdb.core.ui.common.R.string.retry) {
                showsPresenter.onShowMostPopularTvShows()
            }
            .show()
    }

    override fun navigateToShowDetails(position: Int, showId: Long) {
        val view = showsLayoutManager.findViewByPosition(position)
        val intent = ShowDetailActivity.startIntent(this, showId)
        startActivity(
            intent,
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, view!!,
                getString(com.ng.tvshowsdb.core.ui.common.R.string.shared_show_poster_transition)
            )
                .toBundle()
        )
    }
}
