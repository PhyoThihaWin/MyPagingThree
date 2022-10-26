package com.pthw.appbase.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import com.google.android.material.snackbar.BaseTransientBottomBar.Duration
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Vincent on 2/13/20
 * Edited by OneNex
 */
fun Activity.showSnackbar(text: CharSequence, @Duration duration: Int) {
  val contentView = findViewById<View>(android.R.id.content)
  val snackbar = Snackbar.make(contentView, text, duration)
  snackbar.show()
}

fun Activity.showSnackbar(@StringRes stringResId: Int, @Duration duration: Int) {
  val contentView = findViewById<View>(android.R.id.content)
  val snackbar = Snackbar.make(contentView, stringResId, duration)
  snackbar.show()
}

fun Activity.hideKeyboard() {
  val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
  //Find the currently focused view, so we can grab the correct window token from it.
  var view = currentFocus
  //If no view currently has focus, create a new one, just so we can grab a window token from it
  if (view == null) {
    view = View(this)
  }
  imm.hideSoftInputFromWindow(view.windowToken, 0)
  view.clearFocus()
}

fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
  val intent = Intent(this, it)
  intent.putExtras(Bundle().apply(extras))
  startActivity(intent)
}
