package com.pthw.network.interceptor

import com.pthw.data.config.AuthStoreProvider
import com.pthw.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthTokenInterceptor @Inject constructor(private val authStore: AuthStoreProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
//        val token = authStore.getAuthToken() ?: return chain.proceed(chain.request())
        val token = BuildConfig.CLIENT_ID
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Authorization", "Client-ID ${token}")
        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }
}
