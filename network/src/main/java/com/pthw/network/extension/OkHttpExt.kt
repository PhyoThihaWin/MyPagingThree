package com.pthw.network.extension

import okhttp3.Call
import okhttp3.Response
import okhttp3.ResponseBody


/**
 * Created by Vincent on 2019-10-21
 */
private const val KEY_NO_CONTENT = 204

fun Call.executeOrThrow(): ResponseBody {

    val response = this.execute()

    return response.getBodyOrThrowNetworkException()
}

fun Response.getBodyOrThrowNetworkException(): ResponseBody {

    if (this.isSuccessful.not()) {
        val errorString = this.body!!.byteStream().bufferedReader().use { it.readText() }
        throw com.pthw.network.exception.NetworkException(errorString, this.code)
    }
    if (this.code == KEY_NO_CONTENT) {
        throw com.pthw.network.exception.NoContentException()
    }
    val body = this.body ?: throw com.pthw.network.exception.NetworkException()

    return body
}
