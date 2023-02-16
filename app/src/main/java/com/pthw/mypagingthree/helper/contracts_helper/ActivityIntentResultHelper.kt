package com.pthw.mypagingthree.helper.contracts_helper

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class ActivityIntentResultHelper(activity: AppCompatActivity) {
    private lateinit var resultCallBack: (ActivityResult) -> Unit

    private val activityIntentForResult =
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            resultCallBack.invoke(result)
        }

    fun launch(intent: Intent, onResult: (ActivityResult) -> Unit) {
        resultCallBack = onResult
        activityIntentForResult.launch(intent)
    }
}