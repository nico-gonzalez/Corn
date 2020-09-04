package com.ng.tvshowsdb.core.data.remote

import com.ng.tvshowsdb.core.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.THE_MOVIE_DB_API_KEY)
            .build()

        val requestBuilder = original.newBuilder()
            .url(url)

        return chain.proceed(requestBuilder.build())
    }
}