package com.ng.tvshowsdb.shows.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import com.ng.tvshowsdb.core.ui.common.extensions.loadShowImage
import com.ng.tvshowsdb.shows.R
import com.ng.tvshowsdb.shows.databinding.ActivityShowDetailBinding
import com.ng.tvshowsdb.shows.list.ShowItem
import com.ng.tvshowsdb.shows.list.ShowsAdapter
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

const val EXTRA_SHOW_ID = "Extra:ShowId:Long"

class ShowDetailActivity : DaggerAppCompatActivity(), ShowDetailView {

    @Inject
    lateinit var showDetailPresenter: ShowDetailPresenter

    private lateinit var binding: ActivityShowDetailBinding
    private lateinit var similarShowsAdapter: ShowsAdapter
    private lateinit var similarShowsLayoutManager: LinearLayoutManager

    companion object {
        fun startIntent(context: Context, showId: Long): Intent =
            Intent(context, ShowDetailActivity::class.java)
                .putExtra(EXTRA_SHOW_ID, showId)
    }

    override fun setPresenter(presenter: ShowDetailPresenter) {
        this.showDetailPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportPostponeEnterTransition()
        binding = ActivityShowDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
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

        binding.similarShowsRv.apply {
            adapter = similarShowsAdapter
            layoutManager = similarShowsLayoutManager
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this@ShowDetailActivity, HORIZONTAL))
            isNestedScrollingEnabled = false
        }

        showDetailPresenter.onShowDetails()
    }

    internal fun extractShowIdExtra() = intent.extras?.getLong(EXTRA_SHOW_ID) ?: -1

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
        Snackbar.make(binding.root, message, LENGTH_LONG)
            .show()
    }

    override fun showDetails(show: ShowDetailsUiModel) {
        binding.posterIv.loadShowImage(show.posterPath) { supportStartPostponedEnterTransition() }
        binding.backdropIv.loadShowImage(show.backdropPath)
        binding.titleTv.text = show.title
        binding.descriptionTv.text = show.description
        binding.firstAiredDateTv.text = show.firstAirDate
        binding.ratingTv.text = show.rating
    }


    override fun showSimilarShows(shows: List<ShowItem>) {
        similarShowsAdapter.submitList(ArrayList(shows))
    }

    override fun showLoadingSimilarShows() {
        binding.similarShowsProgress.visibility = VISIBLE
    }

    override fun hideLoadingSimilarShows() {
        binding.similarShowsProgress.visibility = GONE
    }

    override fun navigateToShowDetails(position: Int, showId: Long) {
        val view = similarShowsLayoutManager.findViewByPosition(position)
        val intent = startIntent(this, showId)
        startActivity(
            intent,
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, view!!, getString(com.ng.tvshowsdb.core.ui.common.R.string.shared_show_poster_transition)
            ).toBundle()
        )
    }
}
