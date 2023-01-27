package com.pthw.mypagingthree.helper

import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class PermissionRequestHelper(private val activity: AppCompatActivity) {

    private lateinit var grantedCallBack: (Boolean) -> Unit
    private var permissions: Array<String> = emptyArray()

    fun launch(permissions: Array<String>, onGranted: (Boolean) -> Unit) {
        this.grantedCallBack = onGranted
        this.permissions = permissions
        launchRequestPermission()
    }

    private val permissionRequestLauncher =
        activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all { it.value }
            grantedCallBack.invoke(granted)
        }

    private fun launchRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            grantedCallBack.invoke(true)
        } else {
            if (hasPermissions()) grantedCallBack.invoke(true)
            else permissionRequestLauncher.launch(permissions)
        }
    }

    private fun hasPermissions(): Boolean {
        return permissions.all {
            ActivityCompat.checkSelfPermission(activity, it) == PackageManager.PERMISSION_GRANTED
        }
    }

}