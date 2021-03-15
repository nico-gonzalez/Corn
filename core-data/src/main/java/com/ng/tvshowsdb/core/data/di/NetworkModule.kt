package com.ng.tvshowsdb.core.data.di

import com.ng.tvshowsdb.core.data.BuildConfig
import com.ng.tvshowsdb.core.data.remote.ApiKeyInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
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
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(BODY))
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        @Named("baseUrl") baseUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}
