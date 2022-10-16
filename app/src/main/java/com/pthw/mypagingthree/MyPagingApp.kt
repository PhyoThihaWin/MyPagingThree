package com.pthw.mypagingthree

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyPagingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return String.format(
                        "C:%s, L:%s",
                        super.createStackElementTag(element)?.substringBefore("$") ?: "Timber",
                        element.lineNumber,
//                        element.methodName
                    )
                }
            })

        }
    }
}