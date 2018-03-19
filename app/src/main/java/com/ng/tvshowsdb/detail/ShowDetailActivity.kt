package com.ng.tvshowsdb.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_LONG
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.HORIZONTAL
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View.GONE
import com.ng.tvshowsdb.R
import com.ng.tvshowsdb.common.loadShowImage
import com.ng.tvshowsdb.presentation.detail.ShowDetailView
import com.ng.tvshowsdb.presentation.detail.TvShowDetailPresenter
import com.ng.tvshowsdb.presentation.detail.TvShowDetailsViewModel
import com.ng.tvshowsdb.presentation.shows.TvShowViewModel
import com.ng.tvshowsdb.shows.ShowsAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_show_detail.backdropIv
import kotlinx.android.synthetic.main.activity_show_detail.descriptionTv
import kotlinx.android.synthetic.main.activity_show_detail.firstAiredDateTv
import kotlinx.android.synthetic.main.activity_show_detail.posterIv
import kotlinx.android.synthetic.main.activity_show_detail.ratingTv
import kotlinx.android.synthetic.main.activity_show_detail.similarShowsProgress
import kotlinx.android.synthetic.main.activity_show_detail.similarShowsRv
import kotlinx.android.synthetic.main.activity_show_detail.titleTv
import kotlinx.android.synthetic.main.activity_show_detail.toolbar
import javax.inject.Inject

const val EXTRA_SHOW_ID = "Extra:ShowId:Long"

class ShowDetailActivity : DaggerAppCompatActivity(), ShowDetailView {

  @Inject
  lateinit var showDetailPresenter: TvShowDetailPresenter

  private var showId: Long = -1

  lateinit var similarShowsAdapter: ShowsAdapter

  lateinit var similarShowsLayoutManager: LinearLayoutManager

  companion object {

    fun startIntent(context: Context, showId: Long): Intent =
        Intent(context, ShowDetailActivity::class.java)
            .putExtra(EXTRA_SHOW_ID, showId)

  }

  override fun setPresenter(presenter: TvShowDetailPresenter) {
    this.showDetailPresenter = presenter
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    supportPostponeEnterTransition()
    setContentView(R.layout.activity_show_detail)

    showId = intent.extras.getLong(EXTRA_SHOW_ID, -1)

    setSupportActionBar(toolbar)
    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
      title = ""
    }

    similarShowsAdapter = ShowsAdapter(R.layout.show_list_item, { position ->
      showDetailPresenter.onSimilarShowSelected(position, similarShowsAdapter.getItemId(position))
    }, {
      showDetailPresenter.onShowMoreSimilarShows()
    })

    similarShowsLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    similarShowsRv.apply {
      adapter = similarShowsAdapter
      layoutManager = similarShowsLayoutManager
      setHasFixedSize(true)
      addItemDecoration(DividerItemDecoration(this@ShowDetailActivity, HORIZONTAL))
      isNestedScrollingEnabled = false
    }

    showDetailPresenter.onShowDetails(showId)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
    android.R.id.home -> {
      onBackPressed()
      true
    }
    else -> false
  }

  override fun onDestroy() {
    super.onDestroy()
    showDetailPresenter.detach()
  }

  override fun showError(message: String) {
    Snackbar.make(toolbar, message, LENGTH_LONG)
        .show()
  }

  override fun showDetails(show: TvShowDetailsViewModel) {

    posterIv.loadShowImage(show.posterPath) {
      supportStartPostponedEnterTransition()
    }
    backdropIv.loadShowImage(show.backdropPath)

    titleTv.text = show.title
    descriptionTv.text = show.description
    firstAiredDateTv.text = show.firstAirDate
    ratingTv.text = show.rating
  }

  override fun showLoadingMoreSimilarShows() {
    similarShowsAdapter.addLoadingMore()
  }

  override fun hideLoadingMoreSimilarShows() {
    similarShowsAdapter.removeLoadingMore()
  }

  override fun showSimilarShows(shows: List<TvShowViewModel>) {
    similarShowsProgress.visibility = GONE
    similarShowsAdapter.setShows(shows)
  }

  override fun showMoreSimilarShows(shows: List<TvShowViewModel>) {
    similarShowsAdapter.addShows(shows)
  }

  override fun navigateToShowDetails(position: Int, showId: Long) {
    val view = similarShowsLayoutManager.findViewByPosition(position)
    val intent = ShowDetailActivity.startIntent(this, showId)
    startActivity(intent,
        ActivityOptionsCompat.makeSceneTransitionAnimation(this, view,
            getString(R.string.shared_show_poster_transition))
            .toBundle()
    )
  }
}
