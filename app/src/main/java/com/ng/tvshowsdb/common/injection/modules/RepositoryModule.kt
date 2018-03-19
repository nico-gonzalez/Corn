package com.ng.tvshowsdb.common.injection.modules

import com.ng.tvshowsdb.data.common.remote.TheMovieDBService
import com.ng.tvshowsdb.data.shows.TheMovieDbTvShowRepository
import com.ng.tvshowsdb.data.shows.TvShowMapper
import com.ng.tvshowsdb.domain.repository.TvShowRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
  NetworkModule::class
])
class RepositoryModule {

  @Singleton
  @Provides
  fun provideTvShowRepository(movieDBService: TheMovieDBService,
      tvShowMapper: TvShowMapper): TvShowRepository = TheMovieDbTvShowRepository(movieDBService,
      tvShowMapper)
}