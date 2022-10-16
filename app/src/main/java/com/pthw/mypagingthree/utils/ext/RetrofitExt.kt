package com.pthw.mypagingthree.utils.ext

import com.pthw.mypagingthree.utils.exception.NetworkException
import com.pthw.mypagingthree.utils.exception.NoContentException
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

/**
 * Created by Vincent on 2019-10-21
 *
 * Modified by PTH
 */
private const val KEY_CONTENT = 204
private const val ERROR_401 = 401

fun <T> Call<T>.executeOrThrow(): T {

    val response = this.execute()

    return response.getBodyOrThrowNetworkException()
}

fun <T> Response<T>.getBodyOrThrowNetworkException(): T {
    if (this.isSuccessful.not()) {
        val errorString = this.errorBody()!!.byteStream().bufferedReader().use { it.readText() }
        if (this.raw().code == ERROR_401) {
            throw NetworkException(errorString, this.raw().code)
        } else {
            throw NetworkException(errorString, this.code())
        }
    }
    if (this.code() == KEY_CONTENT) {
        throw NoContentException()
    }
    val body = this.body() ?: throw NetworkException()

    return body
}

fun <T> Response<T>.getBody(): T = body() ?: throw NetworkException()


