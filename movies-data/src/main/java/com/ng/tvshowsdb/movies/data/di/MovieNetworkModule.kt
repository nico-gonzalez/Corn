package com.ng.tvshowsdb.movies.data.di

import com.ng.tvshowsdb.core.data.di.NetworkModule
import com.ng.tvshowsdb.movies.data.remote.MoviesService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
internal class MovieNetworkModule {

    @Singleton
    @Provides
    fun provideMoviesService(retrofit: Retrofit): MoviesService =
        retrofit.create(MoviesService::class.java)
}
