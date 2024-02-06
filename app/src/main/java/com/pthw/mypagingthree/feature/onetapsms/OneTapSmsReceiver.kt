package com.pthw.mypagingthree.feature.onetapsms

import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.Status
import com.google.android.gms.common.api.CommonStatusCodes
import timber.log.Timber

class OneTapSmsReceiver : BroadcastReceiver() {

    private var otpListener: OTPReceiveListener? = null

    fun setOTPListener(otpListener: OTPReceiveListener?) {
        this.otpListener = otpListener
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.i("OneTapSmsReceiver: ${intent.toString()}")

        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {
            val extras = intent.extras
            val smsRetrieverStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as Status
            Timber.i("OneTapSmsReceiver: SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action")

            when (smsRetrieverStatus.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    Timber.i("OneTapSmsReceiver: smsRetrieverStatus.statusCode")
                    // Get consent intent
                    val consentIntent = extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                    try {
                        // Start activity to show consent dialog to user, activity must be started in
                        // 5 minutes, otherwise you'll receive another TIMEOUT intent
                        if (otpListener != null && consentIntent != null) {
                            otpListener!!.onSMSReceivedIntent(consentIntent)
                        }
                    } catch (e: ActivityNotFoundException) {
                        // Handle the exception ...
                    }
                }
                CommonStatusCodes.TIMEOUT -> {
                    // Time out occurred, handle the error.
                    Timber.i("OneTapSmsReceiver: CommonStatusCodes.TIMEOUT")
                }
            }
        }
    }


    interface OTPReceiveListener {
        fun onOTPReceived(otp: String?)
        fun onSMSReceivedIntent(intent: Intent)
    }
}