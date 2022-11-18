package com.pthw.network.feature.samplepost.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostSource(
    @Json(name = "userId") val userId: Int?,
    @Json(name = "id") val id: Int?,
    @Json(name = "title") val title: String?,
    @Json(name = "body") val body: String?
)