package com.pthw.network.feature.splashimage.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SplashPhotoSource(
    val id: String,
    val description: String?,
    val urls: SplashPhotoUrls,
    val user: SplashUser
) {

    @JsonClass(generateAdapter = true)
    data class SplashPhotoUrls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String,
    )

    @JsonClass(generateAdapter = true)
    data class SplashUser(
        val name: String,
        val username: String
    ) {
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
    }
}