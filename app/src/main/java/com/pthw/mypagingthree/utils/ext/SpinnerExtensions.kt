package com.pthw.mypagingthree.utils.ext

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

/**
 * Created by Vincent on 2/13/20
 */

fun spinnerSafeSelectionErrorStub(error: Exception) {
    Log.e("SpinnerExtensions", Log.getStackTraceString(error))

}

fun spinnerSafeSelectionDoAfterSelectStub(position: Int) {
    // Do Nothing
}

fun Spinner.safeSelection(
    position: Int,
    doAfterSelect: ((Int) -> (Unit)) = ::spinnerSafeSelectionDoAfterSelectStub,
    onError: ((Exception) -> (Unit)) = ::spinnerSafeSelectionErrorStub
) {
    try {
        setSelection(position)
        doAfterSelect(position)
    } catch (e: Exception) {
        onError.invoke(e)
    }

}

fun Spinner.useSelectedItem(): String {
    return if (this.selectedItemId == 0L) {
        ""
    } else {
        this.selectedItem.toString()
    }
}

fun Spinner.onItemSelected(onItemSelected: (Int) -> Unit) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            //do sth
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            onItemSelected(position)
        }
    }
}
