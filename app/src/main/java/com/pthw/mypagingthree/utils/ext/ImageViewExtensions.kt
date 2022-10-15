package com.pthw.mypagingthree.utils.ext

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget


/**
 * Loads URL into an ImageView using Glide
 *
 * @param url URL to be loaded
 */
private const val THUMBNAIL_SIZE = 0.1f
private const val CORNER_RADIUS = 18f
fun ImageView.loadFromUrl(url: String?) {
    val pureBase64Encoded = url?.substring(url.indexOf(",") + 1)
    val decodedString = Base64.decode(pureBase64Encoded, Base64.DEFAULT)
    val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.count())
    if (!url.isNullOrEmpty()) {
        Glide.with(this)
            .load(bitmap)
            .thumbnail(THUMBNAIL_SIZE)
            .apply(
                RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            )
            .into(this)
    }
}

fun ImageView.loadFromUrl(url: String?, @DrawableRes placeHolder: Int) {
    val pureBase64Encoded = url?.substring(url.indexOf(",") + 1)
    val decodedString = Base64.decode(pureBase64Encoded, Base64.DEFAULT)
    val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.count())
    if (!url.isNullOrEmpty()) {
        Glide.with(this)
            .load(bitmap)
            .thumbnail(THUMBNAIL_SIZE)
            .apply(
                RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(placeHolder)
//                    .error()
            )
            .into(this)
    } else {
        loadFromUrl(placeHolder)
    }
}


//fun ImageView.loadFromUrl(url: String, error: Int) {
//    Glide.with(this)
//        .load(url)
//        .thumbnail(THUMBNAIL_SIZE)
//        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).error(error))
//        .into(this)
//}

fun ImageView.loadFromUrl(url: String, placeHolder: Int, error: Int) {
    Glide.with(this)
        .load(url)
        .thumbnail(THUMBNAIL_SIZE)
        .apply(
            RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(placeHolder)
                .error(error)
        )
        .into(this)
}


fun ImageView.loadFromUrl(resource: Int) {
    Glide.with(this)
        .load(ContextCompat.getDrawable(this.context, resource))
        .thumbnail(THUMBNAIL_SIZE)
        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
        .into(this)
}


fun ImageView.loadFromUrl(url: String, resources: Resources) {
    Glide.with(this)
        .setDefaultRequestOptions(
            RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .placeholder(R.drawable.pre_article)
//                    .error(R.drawable.pre_article)
                .centerCrop()
        )
        .asBitmap()
        .load(url)
        .into(object : BitmapImageViewTarget(this) {
            override fun setResource(res: Bitmap?) {
                val bitmapDrawable = RoundedBitmapDrawableFactory.create(resources, res)
                bitmapDrawable.cornerRadius = CORNER_RADIUS
                setImageDrawable(bitmapDrawable)
            }
        })
}
