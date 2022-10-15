package com.pthw.mypagingthree.config

import android.content.Context
import android.util.Base64
import androidx.core.content.edit
import com.pthw.mypagingthree.cache.SecureSharedPreference
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

/**
 * modified by pth.
 * didn't use android.security.crypto lib.
 * used simple just encode-decode for the reason of minSdk-21
 */
class AuthStoreProviderImpl @Inject constructor(
    @ApplicationContext context: Context
) : AuthStoreProvider {
    private val sharedPreference = SecureSharedPreference.get(context, PREF_NAME)

    companion object {
        private const val PREF_NAME = "AUTH_TOKEN"
        private const val KEY_AUTH_TOKEN = "auth_token"
    }

    override fun storeAuthToken(authToken: AuthToken) {
        val encodeToken = Base64.encodeToString((authToken.value).toByteArray(), Base64.DEFAULT)
        sharedPreference.edit {
            putString(KEY_AUTH_TOKEN, encodeToken)
        }
    }

    override fun getAuthToken(): AuthToken? {
        val value = sharedPreference.getString(KEY_AUTH_TOKEN, null) ?: return null
        val decodeToken = Base64.decode(value, Base64.DEFAULT)
        Timber.i("Encode Token: %s", value)
        Timber.i("Decode Token: %s", String(decodeToken))
        return AuthToken(String(decodeToken))
    }

    override fun clearAuthToken() {
        sharedPreference.edit {
            remove(KEY_AUTH_TOKEN)
        }
    }
}
