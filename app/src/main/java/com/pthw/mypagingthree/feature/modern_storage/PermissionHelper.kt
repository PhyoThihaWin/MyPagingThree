package com.pthw.mypagingthree.feature.modern_storage

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.pthw.appbase.extension.showDialogWithOk

class PermissionHelper(
    private val activity: AppCompatActivity,
    private val perms: Array<String>,
    private val onGranted: () -> Unit,
    private val onRequest: () -> Unit,
) {

    init {
        initiatePermission()
    }

    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

    private fun initiatePermission() {
        if (hasPermissions(activity, perms)) {
            onGranted.invoke()
        } else {
            onRequest.invoke()
        }
    }

    fun loadPermission(permissions: Map<String, Boolean>) {
        val granted = permissions.all { it.value }
        val should = permissions.all { shouldShowRequestPermissionRationale(activity, it.key) }

        if (granted) {
            onGranted.invoke()
        } else if (should) {
            val title = "Permission Required"
            val message = "This permissions are need for application action, so that application can read photos and can take a photo camera."
            activity.showDialogWithOk(title, message, "OK") {
                onRequest.invoke()
            }
        }
    }

}