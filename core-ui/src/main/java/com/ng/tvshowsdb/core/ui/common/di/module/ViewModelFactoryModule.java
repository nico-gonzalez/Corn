package com.ng.tvshowsdb.core.ui.common.di.module;

import androidx.lifecycle.ViewModelProvider;

import com.ng.tvshowsdb.core.ui.common.di.provider.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(
            ViewModelProviderFactory viewModelProviderFactory
    );

}
