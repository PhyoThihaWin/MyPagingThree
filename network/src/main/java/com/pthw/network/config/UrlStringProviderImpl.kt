package com.pthw.network.config

import com.pthw.data.config.UrlStringProvider
import com.pthw.network.BuildConfig
import javax.inject.Inject

class UrlStringProviderImpl @Inject constructor() : UrlStringProvider {
    override fun getBaseUrl(): String {
        return BuildConfig.BASE_URL
    }
}