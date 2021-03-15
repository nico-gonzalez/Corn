package com.ng.tvshowsdb.home.ui.di

import androidx.lifecycle.ViewModel
import com.ng.tvshowsdb.core.ui.common.di.module.ViewModelKey
import com.ng.tvshowsdb.home.ui.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface HomeViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
}
