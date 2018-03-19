package com.ng.tvshowsdb.common.injection.modules

import com.ng.tvshowsdb.common.injection.scopes.PerActivity
import com.ng.tvshowsdb.detail.ShowDetailActivity
import com.ng.tvshowsdb.domain.shows.GetSimilarTvShows
import com.ng.tvshowsdb.domain.shows.GetTvShow
import com.ng.tvshowsdb.presentation.detail.ShowDetailView
import com.ng.tvshowsdb.presentation.detail.TvShowDetailPresenter
import com.ng.tvshowsdb.presentation.detail.TvShowDetailsViewModelMapper
import com.ng.tvshowsdb.presentation.shows.TvShowViewModelMapper
import dagger.Module
import dagger.Provides

@Module
class ShowDetailActivityModule {

  @PerActivity
  @Provides
  fun provideShowDetailView(
      showDetailActivity: ShowDetailActivity): ShowDetailView = showDetailActivity

  @PerActivity
  @Provides
  fun provideShowDetailPresenter(tvShowsView: ShowDetailView, getTvShow: GetTvShow,
      getSimilarTvShows: GetSimilarTvShows,
      tvShowViewModelMapper: TvShowViewModelMapper,
      tvShowDetailsViewModelMapper: TvShowDetailsViewModelMapper) = TvShowDetailPresenter(
      tvShowsView, getTvShow, getSimilarTvShows, tvShowViewModelMapper,
      tvShowDetailsViewModelMapper)

}