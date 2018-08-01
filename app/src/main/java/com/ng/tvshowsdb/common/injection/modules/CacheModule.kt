package com.ng.tvshowsdb.common.injection.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.ng.tvshowsdb.data.common.cache.DB_NAME
import com.ng.tvshowsdb.data.common.cache.TvShowsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

  @Singleton
  @Provides
  fun provideTvShowsDatabase(context: Context): TvShowsDatabase = Room.databaseBuilder(
      context, TvShowsDatabase::class.java, DB_NAME
  ).build()

}