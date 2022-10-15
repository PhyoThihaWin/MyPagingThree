package com.pthw.mypagingthree.data.model.response

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class SplashPhoto(
    val id: String,
    val description: String?,
    val urls: SplashPhotoUrls,
    val user: SplashUser
) : Parcelable {

    @JsonClass(generateAdapter = true)
    @Parcelize
    data class SplashPhotoUrls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String,
    ) : Parcelable

    @JsonClass(generateAdapter = true)
    @Parcelize
    data class SplashUser(
        val name: String,
        val username: String
    ) : Parcelable {
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
    }
}