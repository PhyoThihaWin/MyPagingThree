package com.pthw.mypagingthree.config

interface AuthStoreProvider {
    fun storeAuthToken(authToken: AuthToken)

    fun getAuthToken(): AuthToken?

    fun clearAuthToken()
}

@JvmInline
value class AuthToken(val value: String)
