package com.droiddevtips.nextgenexamples.application

import android.app.Application
import android.util.Log
import com.droiddevtips.nextgenexamples.ads.adManager.AppAdManager
import com.droiddevtips.nextgenexamples.googleAdsConsentManager.GoogleAdsConsentManager
import com.google.android.libraries.ads.mobile.sdk.MobileAds
import com.google.android.libraries.ads.mobile.sdk.initialization.InitializationConfig

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        GoogleAdsConsentManager.init(applicationContext)
        MobileAds.initialize(
            applicationContext,
            InitializationConfig.Builder(AppAdManager.APP_ID).build()
        ) { test ->
            Log.i("TAG12","Mobile Ads init status -> $test")
        }

    }

}