package com.pthw.network.config

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataResponse<T>(

    @Json(name = "data")
    val data: T?,

    @Json(name = "message")
    val errorMessage: String?,

    @Json(name = "success")
    val success: Boolean?
)
@JsonClass(generateAdapter = true)
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