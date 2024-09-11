package com.ng.tvshowsdb.core.ui.common.extensions

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.ng.tvshowsdb.core.ui.common.BuildConfig
import com.ng.tvshowsdb.core.ui.common.R

fun ViewGroup.inflate(
    @LayoutRes layoutResId: Int,
    attachToRoot: Boolean = false
): View = LayoutInflater.from(context).inflate(
    layoutResId, this, attachToRoot
)

fun ImageView.loadShowImage(posterPath: String, onLoaded: (() -> Unit)? = null) = Glide.with(
    context
)
    .applyDefaultRequestOptions(RequestOptions.placeholderOf(R.mipmap.ic_launcher_round))
    .load("${BuildConfig.IMAGE_BASE_URL}/w500/$posterPath")
    .listener(object : RequestListener<Drawable> {

        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>,
            isFirstResource: Boolean
        ): Boolean {
            onLoaded?.invoke()
            return false
        }

        override fun onResourceReady(
            resource: Drawable,
            model: Any,
            target: Target<Drawable>?,
            dataSource: DataSource,
            isFirstResource: Boolean
        ): Boolean {
            onLoaded?.invoke()
            return false
        }

    })
    .into(this)