package com.ng.tvshowsdb.shows

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.GridLayoutManager
import android.view.View.GONE
import android.view.View.VISIBLE
import com.ng.tvshowsdb.R
import com.ng.tvshowsdb.common.view.GridSpacingItemDecoration
import com.ng.tvshowsdb.detail.ShowDetailActivity
import com.ng.tvshowsdb.presentation.shows.TvShowViewModel
import com.ng.tvshowsdb.presentation.shows.TvShowsPresenter
import com.ng.tvshowsdb.presentation.shows.TvShowsView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_shows.progressBar
import kotlinx.android.synthetic.main.activity_shows.showsRv
import kotlinx.android.synthetic.main.activity_shows.toolbar
import javax.inject.Inject

private const val GRID_COLUMNS = 2

class TvShowsActivity : DaggerAppCompatActivity(), TvShowsView {

  @Inject
  lateinit var showsPresenter: TvShowsPresenter

  lateinit var showsAdapter: ShowsAdapter

  lateinit var showsLayoutManager: GridLayoutManager

  override fun setPresenter(presenter: TvShowsPresenter) {
    this.showsPresenter = presenter
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_shows)

    setSupportActionBar(toolbar)

    showsAdapter = ShowsAdapter(R.layout.show_grid_item, { position ->
      showsPresenter.onShowSelected(position, showsAdapter.getItemId(position))
    }, {
      showsPresenter.onShowMoreShows()
    })

    showsLayoutManager = GridLayoutManager(this, GRID_COLUMNS)

    showsRv.apply {
      adapter = showsAdapter
      layoutManager = showsLayoutManager
      setHasFixedSize(true)
      val divider = resources.getDimensionPixelSize(R.dimen.divider_size)
      addItemDecoration(GridSpacingItemDecoration(GRID_COLUMNS, divider, false, 0))
    }

    showsPresenter.onShowMostPopularTvShows()
  }

  override fun onConfigurationChanged(newConfig: Configuration?) {
    super.onConfigurationChanged(newConfig)
    showsLayoutManager.spanCount = when (newConfig?.orientation) {
      ORIENTATION_LANDSCAPE -> GRID_COLUMNS + 1
      else -> GRID_COLUMNS
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    showsPresenter.detach()
  }

  override fun showShows(shows: List<TvShowViewModel>) {
    showsAdapter.setShows(shows)
  }

  override fun showLoading() {
    progressBar.visibility = VISIBLE
  }

  override fun hideLoading() {
    progressBar.visibility = GONE
  }

  override fun showError() {
    Snackbar.make(showsRv, R.string.error_loading_popular_shows, Snackbar.LENGTH_LONG)
        .setAction(R.string.retry, {
          showsPresenter.onShowMostPopularTvShows()
        })
        .show()
  }

  override fun showLoadingMoreShows() {
    showsAdapter.addLoadingMore()
  }

  override fun hideLoadingMoreShows() {
    showsAdapter.removeLoadingMore()
  }

  override fun showMoreShows(shows: List<TvShowViewModel>) {
    showsAdapter.addShows(shows)
  }

  override fun navigateToShowDetails(position: Int, showId: Long) {
    val view = showsLayoutManager.findViewByPosition(position)
    val intent = ShowDetailActivity.startIntent(this, showId)
    startActivity(intent,
        ActivityOptionsCompat.makeSceneTransitionAnimation(this, view,
            getString(R.string.shared_show_poster_transition))
            .toBundle()
    )
  }
}
