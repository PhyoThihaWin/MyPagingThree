package com.pthw.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor { override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalHeader = request.headers
        val headerBuilder = originalHeader.newBuilder()
        headerBuilder.add("Content-Type", "application/json")
        headerBuilder.add("X-Requested-With", "XMLHttpRequest")
        val newRequest = request.newBuilder().headers(headerBuilder.build()).build()
        return chain.proceed(newRequest)
    }
}
