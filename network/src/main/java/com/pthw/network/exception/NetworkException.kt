package com.pthw.network.exception

import java.io.IOException

/**
 * Created by Vincent on 2/23/19
 */
data class NetworkException constructor(
    val errorBody: String? = null,
    var errorCode: Int = 0
) : IOException()
