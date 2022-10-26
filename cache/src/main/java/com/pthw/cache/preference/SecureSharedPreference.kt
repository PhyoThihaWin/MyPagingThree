package com.pthw.cache.preference

import android.content.Context
import android.content.SharedPreferences
import android.os.Build

//import androidx.security.crypto.EncryptedSharedPreferences
//import androidx.security.cryptoz.MasterKey

object SecureSharedPreference {
    fun get(context: Context, fileName: String): SharedPreferences {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

//        return if (Build.VERSION.SDK_INT < 23) {
//        } else {
//            val masterKey = MasterKey.Builder(context)
//                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
//                .build()
//            EncryptedSharedPreferences.create(
//                context, fileName, masterKey,
//                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//            )
//        }
    }
}