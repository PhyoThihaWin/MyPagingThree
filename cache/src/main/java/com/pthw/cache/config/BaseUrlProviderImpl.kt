package com.pthw.cache.config

import android.content.Context
import androidx.core.content.edit
import com.pthw.cache.BuildConfig
import com.pthw.cache.preference.SecureSharedPreference
import com.pthw.data.config.BaseUrlProvider
import com.pthw.data.config.UrlStringProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BaseUrlProviderImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val urlProvider: UrlStringProvider
) : BaseUrlProvider {

    companion object {
        private const val KEY_BASE_URL = "base_url"
        private const val NAME = "network_config"
    }

    private val sharedPreferences = SecureSharedPreference.get(context, NAME)

    override fun updateBaseUrl(baseUrl: String) {
        if (BuildConfig.DEBUG.not()) {
            throw IllegalStateException("Base url cannot be changed for release builds")
        }
        sharedPreferences.edit {
            putString(KEY_BASE_URL, baseUrl)
        }
    }

    override fun getBaseUrl(): String {
        if (BuildConfig.DEBUG.not()) {
            return urlProvider.getBaseUrl()
        }
        return sharedPreferences.getString(KEY_BASE_URL, urlProvider.getBaseUrl())
            ?: urlProvider.getBaseUrl()
    }

}
