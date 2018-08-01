package com.ng.tvshowsdb.data.common.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.ng.tvshowsdb.data.common.cache.dao.TvShowsDao
import com.ng.tvshowsdb.data.common.cache.entity.TvShowEntity

@Database(
    entities = [TvShowEntity::class],
    version = DB_VERSION
)
abstract class TvShowsDatabase : RoomDatabase() {

  abstract fun tvShowsDao(): TvShowsDao
}