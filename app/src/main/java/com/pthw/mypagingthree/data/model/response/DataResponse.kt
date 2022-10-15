package com.pthw.mypagingthree.data.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class DataResponse<T>(

    @Json(name = "data")
    val data: T?,

    @Json(name = "message")
    val errorMessage: String?,

    @Json(name = "success")
    val success: Boolean?
)

data class DataEmptyResponse(

    @Json(name = "message")
    val errorMessage: String?
)

@JsonClass(generateAdapter = true)
data class DataPageResponse<T>(
    @Json(name = "results")
    val data: List<T>?,

    @Json(name = "message")
    val errorMessage: String?,

    @Json(name = "page")
    val currentPage: Int?,

    @Json(name = "total_pages")
    val totalPage: Int
)