package com.pthw.data.feature.splashimage.entity

data class SplashPhotoEntity(
    val id: String,
    val description: String?,
    val urls: SplashPhotoUrls,
    val user: SplashUser
) {

    data class SplashPhotoUrls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String,
    )

    data class SplashUser(
        val name: String,
        val username: String
    ) {
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
    }
}