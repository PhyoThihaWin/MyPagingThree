package com.pthw.network.feature.githubrepo.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoSearchResponse(
    @Json(name = "total_count") val total: Int = 0,
    @Json(name = "items") val items: List<RepoSource> = emptyList(),
    val nextPage: Int? = null
)
