package com.pthw.appbase.extension

import android.content.Context
import android.content.DialogInterface
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

fun Context.showAlertDialog(theme: Int = 0, dialogBuilder: AlertDialog.Builder.() -> Unit) {
    val builder = if (theme != 0) AlertDialog.Builder(this, theme) else AlertDialog.Builder(this)
    builder.dialogBuilder()
    val dialog = builder.create()
    dialog.show()
}

fun AlertDialog.Builder.positiveButton(
    text: String = "Ok",
    handleClick: (which: Int, DialogInterface) -> Unit = { _, _ -> }
) {
    this.setPositiveButton(text) { dialogInterface, which -> handleClick(which, dialogInterface) }
}


inline fun <reified T : Any> AlertDialog.Builder.adapterForSelectedItem(
    adapter: ArrayAdapter<T>,
    crossinline handleResult: (dialog: DialogInterface, selectedItem: T) -> Unit = { _, _ -> }
) {
    this.setAdapter(adapter) { dialogInterface, i ->
        adapter.getItem(i)?.apply { handleResult(dialogInterface, this) }
    }
}

fun AlertDialog.Builder.negativeButton(
    text: String = "Cancel",
    handleClick: (which: Int, DialogInterface) -> Unit = { _, _ -> }
) {
    this.setNegativeButton(text) { dialogInterface, which -> handleClick(which, dialogInterface) }
}

fun AlertDialog.Builder.negativeButtonAutoDismiss(text: String = "Cancel") {
    this.setNegativeButton(text) { dialogInterface, _ -> dialogInterface.dismiss() }
}

fun AlertDialog.Builder.positiveButtonAutoDismiss(text: String = "Ok") {
    this.setPositiveButton(text) { dialogInterface, _ -> dialogInterface.dismiss() }
}

fun AppCompatActivity.showDialogWithOk(
    title: String,
    message: String,
    okButtonText: String,
    okButton: (DialogInterface) -> Unit
) {

    val dialogBuilder = AlertDialog.Builder(this)


    dialogBuilder.setMessage(message)
        .setCancelable(false)
        .setPositiveButton(
            okButtonText,
            DialogInterface.OnClickListener { dialog, id ->
                okButton(dialog)
            })

    val alert = dialogBuilder.create()
    alert.setTitle(title)
    alert.show()

}