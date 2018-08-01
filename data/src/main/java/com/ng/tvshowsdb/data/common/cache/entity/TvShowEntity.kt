package com.ng.tvshowsdb.data.common.cache.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "tv_shows")
data class TvShowEntity(
  @PrimaryKey(autoGenerate = true) val id: Long,
  val title: String,
  val description: String,
  @ColumnInfo(name = "poster_path") val posterPath: String,
  @ColumnInfo(name = "backdrop_path") val backdropPath: String,
  @ColumnInfo(name = "first_air_date") val firstAirDate: String,
  val rating: Double
)