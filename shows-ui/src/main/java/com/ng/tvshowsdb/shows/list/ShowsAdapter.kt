package com.ng.tvshowsdb.shows.list

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.ng.tvshowsdb.core.ui.common.extensions.inflate
import com.ng.tvshowsdb.core.ui.common.extensions.loadShowImage
import kotlinx.android.synthetic.main.show_card.view.*

sealed class ShowsViewHolder(view: View) : RecyclerView.ViewHolder(view)

class ShowViewHolder(
    view: View,
    private val onShowClickedListener: (position: Int) -> Unit
) : ShowsViewHolder(view) {

    fun bind(show: ShowUiModel) {
        itemView.apply {
            titleTv.text = show.title
            posterIv.loadShowImage(show.posterPath)
            ratingTv.text = show.rating
            showCard.setOnClickListener { onShowClickedListener(adapterPosition) }
        }
    }
}

class LoadingViewHolder(view: View) : ShowsViewHolder(view)

const val SHOW_TYPE = 1
const val LOADING_TYPE = 2

class ShowsAdapter constructor(
    @LayoutRes private val showItemLayoutId: Int,
    @LayoutRes private val loadingItemLayoutId: Int,
    private val onShowClickedListener: (position: Int) -> Unit,
    private val onLoadMoreShowsListener: () -> Unit
) : ListAdapter<ShowItem, ShowsViewHolder>(TvShowItemDiffCallback()) {

    private val loadMoreScrollListener = object : OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition() + 1

            if (lastVisibleItemPosition == itemCount) {
                onLoadMoreShowsListener()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowsViewHolder = when (viewType) {
        SHOW_TYPE -> ShowViewHolder(parent.inflate(showItemLayoutId), onShowClickedListener)
        LOADING_TYPE -> LoadingViewHolder(parent.inflate(loadingItemLayoutId))
        else -> throw IllegalStateException("Unknown ViewHolder type {$viewType}")
    }

    override fun onBindViewHolder(holder: ShowsViewHolder, position: Int) {
        getItem(position).let {
            when (holder) {
                is ShowViewHolder -> holder.bind(it as ShowUiModel)
                is LoadingViewHolder -> Unit
            }
        }
    }

    override fun getItemViewType(
        position: Int
    ): Int = when (getItem(position)) {
        LoadingShowUiModel -> LOADING_TYPE
        else -> SHOW_TYPE
    }

    override fun getItemId(position: Int): Long = getItem(position).id

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.apply {
            clearOnScrollListeners()
            addOnScrollListener(loadMoreScrollListener)
        }
    }

    private class TvShowItemDiffCallback : DiffUtil.ItemCallback<ShowItem>() {

        override fun areItemsTheSame(
            oldItem: ShowItem,
            newItem: ShowItem
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ShowItem,
            newItem: ShowItem
        ): Boolean = oldItem == newItem
    }
}