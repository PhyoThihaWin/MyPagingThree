package com.pthw.network.interceptor

import com.pthw.data.config.LanguageCacheDataSource
import com.pthw.data.model.Language
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class LanguageInterceptor @Inject constructor(private val languageCacheDataSource: LanguageCacheDataSource) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalUrlBuilder = request.url.newBuilder()
        val deviceLanguage = languageCacheDataSource.getLanguageNormal()
        val deviceLanguageString = when (deviceLanguage) {
            Language.MYANMAR_UNICODE -> {
                "uni"
            }
            Language.ENGLISH -> {
                "en"
            }
        }
        val newUrl = originalUrlBuilder.addQueryParameter("lang", deviceLanguageString)
        val newRequest = request
            .newBuilder()
            .url(newUrl.build())
            .build()

        return chain.proceed(newRequest)
    }
}
