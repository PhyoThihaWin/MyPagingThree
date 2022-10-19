package com.pthw.mypagingthree.data.model.response

import com.pthw.mypagingthree.data.model.Repo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoSearchResponse(
    @Json(name = "total_count") val total: Int = 0,
    @Json(name = "items") val items: List<Repo> = emptyList(),
    val nextPage: Int? = null
)
