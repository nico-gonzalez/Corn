package com.ng.tvshowsdb.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import com.ng.tvshowsdb.R
import com.ng.tvshowsdb.common.loadShowImage
import com.ng.tvshowsdb.presentation.detail.ShowDetailView
import com.ng.tvshowsdb.presentation.detail.TvShowDetailPresenter
import com.ng.tvshowsdb.presentation.detail.TvShowDetailsUiModel
import com.ng.tvshowsdb.presentation.shows.TvShowItem
import com.ng.tvshowsdb.shows.ShowsAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_show_detail.*
import javax.inject.Inject

const val EXTRA_SHOW_ID = "Extra:ShowId:Long"

class ShowDetailActivity : DaggerAppCompatActivity(), ShowDetailView {

    @Inject
    lateinit var showDetailPresenter: TvShowDetailPresenter

    private var showId: Long = -1

    private lateinit var similarShowsAdapter: ShowsAdapter

    private lateinit var similarShowsLayoutManager: LinearLayoutManager

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

        showId = intent.extras?.getLong(EXTRA_SHOW_ID) ?: -1

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }

        similarShowsAdapter = ShowsAdapter(
            showItemLayoutId = R.layout.show_list_item,
            loadingItemLayoutId = R.layout.loading_show_card_horizontal,
            onShowClickedListener = { position ->
                showDetailPresenter.onSimilarShowSelected(
                    position,
                    similarShowsAdapter.getItemId(position)
                )
            },
            onLoadMoreShowsListener = { showDetailPresenter.onShowMoreSimilarShows() })

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
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

    override fun showDetails(show: TvShowDetailsUiModel) {

        posterIv.loadShowImage(show.posterPath) {
            supportStartPostponedEnterTransition()
        }
        backdropIv.loadShowImage(show.backdropPath)

        titleTv.text = show.title
        descriptionTv.text = show.description
        firstAiredDateTv.text = show.firstAirDate
        ratingTv.text = show.rating
    }


    override fun showSimilarShows(shows: List<TvShowItem>) {
        similarShowsProgress.visibility = GONE
        similarShowsAdapter.submitList(ArrayList(shows))
    }

    override fun navigateToShowDetails(position: Int, showId: Long) {
        val view = similarShowsLayoutManager.findViewByPosition(position)
        val intent = startIntent(this, showId)
        startActivity(
            intent,
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, view!!,
                getString(R.string.shared_show_poster_transition)
            )
                .toBundle()
        )
    }
}
