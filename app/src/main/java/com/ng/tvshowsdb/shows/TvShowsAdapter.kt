package com.ng.tvshowsdb.shows

import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.NO_ID
import android.support.v7.widget.RecyclerView.OnScrollListener
import android.view.View
import android.view.ViewGroup
import com.ng.tvshowsdb.R
import com.ng.tvshowsdb.common.inflate
import com.ng.tvshowsdb.common.loadShowImage
import com.ng.tvshowsdb.presentation.shows.TvShowViewModel
import kotlinx.android.synthetic.main.show_card.view.*

sealed class ShowsViewHolder(view: View) : RecyclerView.ViewHolder(view)

class ShowViewHolder(view: View,
    private val onShowClickedListener: (position: Int) -> Unit) : ShowsViewHolder(
    view) {
  fun bind(show: TvShowViewModel) {
    itemView.apply {
      titleTv.text = show.title
      posterIv.loadShowImage(show.posterPath)
      ratingTv.text = show.rating
      showCard.setOnClickListener {
        onShowClickedListener(adapterPosition)
      }
    }
  }
}

class LoadingViewHolder(view: View) : ShowsViewHolder(view)

private const val SHOW_TYPE = 1
private const val LOADING_TYPE = 2

class ShowsAdapter constructor(@LayoutRes private val showItemLayoutId: Int,
    private val onShowClickedListener: (position: Int) -> Unit,
    private val onLoadMoreShowsListener: () -> Unit) : RecyclerView.Adapter<ShowsViewHolder>() {

  private val shows: MutableList<TvShowViewModel> = mutableListOf()
  private var isLoadingMore = false

  private lateinit var recyclerView: RecyclerView

  private val loadMoreScrollListener = object : OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
      recyclerView?.let {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition() + 1

        if (lastVisibleItemPosition == itemCount && !isLoadingMore) {
          it.removeOnScrollListener(this)
          onLoadMoreShowsListener()
        }
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup,
      viewType: Int): ShowsViewHolder = when (viewType) {
    SHOW_TYPE -> ShowViewHolder(parent.inflate(showItemLayoutId), onShowClickedListener)
    LOADING_TYPE -> LoadingViewHolder(parent.inflate(R.layout.loading_show_card))
    else -> throw IllegalStateException("Unknown ViewHolder type {$viewType}")
  }

  override fun onBindViewHolder(holder: ShowsViewHolder, position: Int) {
    shows[position].let {
      when (holder) {
        is ShowViewHolder -> holder.bind(it)
      }
    }
  }

  override fun getItemViewType(
      position: Int): Int = when (shows[position]) {
    is LoadingTvShowViewModel -> LOADING_TYPE
    else -> SHOW_TYPE
  }

  override fun getItemCount(): Int = shows.size

  override fun getItemId(position: Int): Long = shows[position].id

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    super.onAttachedToRecyclerView(recyclerView)
    this.recyclerView = recyclerView.apply {
      addOnScrollListener(loadMoreScrollListener)
    }
  }

  fun setShows(shows: List<TvShowViewModel>) {
    recyclerView.post {
      this.shows.clear()
      this.shows += shows
      notifyDataSetChanged()
    }
  }

  fun addLoadingMore() {
    recyclerView.post {
      isLoadingMore = true
      shows.add(LoadingTvShowViewModel())
      notifyItemInserted(shows.size - 1)
      recyclerView.removeOnScrollListener(loadMoreScrollListener)
    }
  }

  fun removeLoadingMore() {
    recyclerView.post {
      isLoadingMore = false
      val loadingMorePosition = shows.size - 1
      shows.removeAt(loadingMorePosition)
      notifyItemRemoved(loadingMorePosition)
      recyclerView.addOnScrollListener(loadMoreScrollListener)
    }
  }

  fun addShows(shows: List<TvShowViewModel>) {
    recyclerView.post {
      val oldCount = itemCount
      this.shows += shows
      notifyItemRangeInserted(oldCount, shows.size)
    }
  }

  internal class LoadingTvShowViewModel : TvShowViewModel(id = NO_ID)
}