package com.pthw.mypagingthree.feature.modern_storage

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.pthw.listdialog.utils.showShortToast
import com.pthw.mypagingthree.R

class ModernStorageActivity : AppCompatActivity() {

    private val PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private val permissionRequest by lazy {
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            permissionHelper.loadPermission(it)
        }
    }

    private lateinit var permissionHelper: PermissionHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modern_storage)

        permissionHelper = PermissionHelper(
            this, PERMISSIONS,
            onGranted = {
                showShortToast("Permission are already granted.")
            },
            onRequest = {
                permissionRequest.launch(PERMISSIONS)
                showShortToast("Permission is requested.")
            }
        )
    }
}
