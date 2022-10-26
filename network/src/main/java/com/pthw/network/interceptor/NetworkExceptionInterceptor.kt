package com.pthw.network.interceptor

import com.pthw.network.exception.NetworkException
import com.pthw.network.exception.NoContentException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


/**
 * Created by PTH on 16/10/2022
 */

private const val KEY_CONTENT = 204
private const val ERROR_401 = 401

class NetworkExceptionInterceptor @Inject constructor() : Interceptor {

    @Throws()
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        when (response.isSuccessful) {
            true -> return response
            false -> {
                when (response.code) {
                    ERROR_401 -> {
                        throw NetworkException(
                            response.body?.string(),
                            response.code
                        )
                    }
                    KEY_CONTENT -> {
                        throw NoContentException()
                    }
                    else -> {
                        throw NetworkException(
                            response.body?.string(),
                            response.code
                        )
                    }
                }
            }
        }

    }
}