package com.example.casinoslots.application

import android.app.Application
import com.onesignal.OneSignal

class OneSignalApplication:Application() {

    val ONESIGNAL_APP_ID = "7bce86e0-994f-4b4c-bdbd-72f451b82c3a"

    override fun onCreate() {
        super.onCreate()
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }

}