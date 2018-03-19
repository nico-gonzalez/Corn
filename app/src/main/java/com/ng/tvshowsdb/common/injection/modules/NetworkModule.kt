package com.ng.tvshowsdb.common.injection.modules

import com.ng.tvshowsdb.data.BuildConfig
import com.ng.tvshowsdb.data.common.remote.ApiKeyInterceptor
import com.ng.tvshowsdb.data.common.remote.TheMovieDBService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

  @Provides
  @Named("baseUrl")
  fun provideBaseUrl(): String = BuildConfig.BASE_URL

  @Singleton
  @Provides
  fun provideHttpClient(apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)

    if (BuildConfig.DEBUG) {
      builder.addInterceptor(HttpLoggingInterceptor().setLevel(BASIC))
    }
    return builder.build()
  }

  @Singleton
  @Provides
  fun provideRetrofit(@Named("baseUrl") baseUrl: String,
      okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(okHttpClient)
      .addConverterFactory(MoshiConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build()

  @Singleton
  @Provides
  fun provideFoursquareService(retrofit: Retrofit): TheMovieDBService = retrofit
      .create(TheMovieDBService::class.java)
}