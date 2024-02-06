package com.pthw.mypagingthree.feature.onetapsms

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.pthw.appbase.extension.showShortToast
import com.pthw.mypagingthree.R

class OneTapSmsVerificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_tap_sms_verification)


        //
        initSmsListener()
        initBroadCast()
        //

    }

    private var intentFilter: IntentFilter? = null
    private var smsReceiver: OneTapSmsReceiver? = null

    val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        // Handle the returned Uri
        if (it.resultCode == Activity.RESULT_OK && it.data != null) {
            // Get SMS message content
            val message = it.data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
            // Extract one-time code from the message and complete verification
            // `message` contains the entire text of the SMS message, so you will need
            // to parse the string.
            val oneTimeCode = parseOneTimeCode(message.toString()) // define this function
            showShortToast("OTP Received: $oneTimeCode")
            initSmsListener()
            // send one time code to the server
        } else {
            // Consent denied. User can type OTC manually.
        }
    }

    private fun initBroadCast() {
        intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        smsReceiver = OneTapSmsReceiver()
        smsReceiver?.setOTPListener(object : OneTapSmsReceiver.OTPReceiveListener {
            override fun onOTPReceived(otp: String?) {
                showShortToast("OTP Received: $otp")
            }

            override fun onSMSReceivedIntent(intent: Intent) {
                showShortToast("OTP Received: intent")
                getContent.launch(intent)
            }
        })
    }

    fun parseOneTimeCode(message: String): String? {
        val regex = Regex("(\\d{6})") // Assuming the one-time code consists of 6 digits
        val matchResult = regex.find(message)
        return matchResult?.value
    }


    private fun initSmsListener() {
        val client = SmsRetriever.getClient(this)
        client.startSmsUserConsent(null)
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(smsReceiver, intentFilter, SmsRetriever.SEND_PERMISSION, null)
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(smsReceiver)
    }
}