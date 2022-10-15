package com.pthw.mypagingthree.utils.ext

import com.pthw.mypagingthree.utils.exception.NetworkException
import com.pthw.mypagingthree.utils.exception.NoContentException
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
        throw NetworkException(errorString, this.code)
    }
    if (this.code == KEY_NO_CONTENT) {
        throw NoContentException()
    }
    val body = this.body ?: throw NetworkException()

    return body
}
