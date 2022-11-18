package com.pthw.network.feature.samplepost.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentSource(
    @Json(name = "postId") val postId: Int?,
    @Json(name = "id") val id: Int?,
    @Json(name = "name") val name: String?,
    @Json(name = "email") val email: String?,
    @Json(name = "body") val body: String?
)