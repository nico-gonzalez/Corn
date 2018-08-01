package com.ng.tvshowsdb.data.common.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.ng.tvshowsdb.data.common.cache.entity.TvShowEntity
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface TvShowsDao {

  @Query("SELECT * FROM tv_shows ORDER BY id DESC LIMIT :limit OFFSET :offset")
  fun getTvShows(limit: Int, offset: Int): Flowable<List<TvShowEntity>>

  @Query("SELECT * FROM tv_shows WHERE id == :showId")
  fun findTvShow(showId: Long): Maybe<TvShowEntity>

  @Insert
  fun insert(tvShowEntity: TvShowEntity): Long

  @Insert
  fun insert(tvShows: List<TvShowEntity>): List<Long>

  @Delete
  fun delete(tvShowEntity: TvShowEntity): Int

  @Delete
  fun delete(tvShows: List<TvShowEntity>): Int

  @Query("DELETE FROM tv_shows")
  fun delete()

}