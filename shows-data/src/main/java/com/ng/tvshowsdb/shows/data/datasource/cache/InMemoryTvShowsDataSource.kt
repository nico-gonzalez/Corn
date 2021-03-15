package com.ng.tvshowsdb.shows.data.datasource.cache

import com.ng.tvshowsdb.shows.domain.model.TvShow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryTvShowsDataSource @Inject constructor() {

    internal val showCache: MutableMap<Long, TvShow> = mutableMapOf()

    operator fun get(id: Long): TvShow? = showCache[id]

    fun save(tvShow: TvShow) {
        showCache[tvShow.id] = tvShow
    }

    fun save(tvShows: List<TvShow>) {
        showCache.putAll(tvShows.associateBy { it.id })
    }
}
