package com.pthw.appbase.extension

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by Vincent on 2/13/20
 */
fun View.setVisible(isVisible: Boolean) {
    if (isVisible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}


fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun Array<View>.setVisible(isVisible: Boolean) {

    val visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }

    this.forEach {
        it.visibility = visibility
    }
}


fun TextView.hideAsTextPassword() {
    transformationMethod = PasswordTransformationMethod.getInstance()
}

fun TextView.showAsTextPassword() {
    transformationMethod = HideReturnsTransformationMethod.getInstance()
}

fun ViewGroup.inflater(): LayoutInflater {
    return this.context.inflater()
}

