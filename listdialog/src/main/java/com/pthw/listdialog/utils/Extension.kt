package com.pthw.listdialog.utils

import android.content.Context
import android.os.Build
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun Context.inflater(): LayoutInflater {
    return LayoutInflater.from(this)
}

fun ViewGroup.inflater(): LayoutInflater {
    return this.context.inflater()
}

fun Context.useColor(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}


fun EditText.afterTextChangedDelayed(delay: Long = 50, afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        var timer: CountDownTimer? = null

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            timer?.cancel()
            timer = object : CountDownTimer(delay, 1000) {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    afterTextChanged.invoke(editable.toString())
                }
            }.start()
        }
    })
}

fun TextView.textAppearance(@StyleRes textStyle: Int) {
    if (Build.VERSION.SDK_INT < 23) {
        this.setTextAppearance(context, textStyle)
    } else {
        this.setTextAppearance(textStyle)
    }
}

fun Context.showShortToast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}

fun Context.showShortToast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}