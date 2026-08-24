package com.droiddevtips.nextgenexamples.application

import android.app.Application
import com.droiddevtips.nextgenexamples.ads.data.loader.AdLoaderImpl
import com.droiddevtips.nextgenexamples.ads.data.manager.AppAdManager
import com.droiddevtips.nextgenexamples.ads.data.provider.BannerAdProviderImpl
import com.droiddevtips.nextgenexamples.ads.data.provider.InterstitialAdProviderImpl
import com.droiddevtips.nextgenexamples.googleAdsConsentManager.GoogleAdsConsentManager
import com.droiddevtips.nextgenexamples.logging.data.LoggerImpl
import com.droiddevtips.nextgenexamples.logging.domain.LogLevel
import com.droiddevtips.nextgenexamples.logging.domain.Logger
import com.google.android.libraries.ads.mobile.sdk.MobileAds
import com.google.android.libraries.ads.mobile.sdk.initialization.InitializationConfig

/**
 * Application-level entry point for the app.
 *
 * Responsible for initializing app-wide dependencies and components that must be
 * available before any Activity is created.
 * This class is instantiated once per process lifecycle and lives for the entire
 * duration of the app's execution.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class App : Application(), Logger by LoggerImpl(className = App::class.java.simpleName) {

    override fun onCreate() {
        super.onCreate()

        GoogleAdsConsentManager.init(applicationContext)
        MobileAds.initialize(
            applicationContext,
            InitializationConfig.Builder(AppAdManager.APP_ID).build()
        ) { status ->
            log(level = LogLevel.Info, message = "Mobile Ads init status -> $status")
        }

        AppAdManager.init(adLoader = AdLoaderImpl.also {
            it.init(
                context = applicationContext,
                bannerAdProvider = BannerAdProviderImpl(),
                interstitialAdProvider = InterstitialAdProviderImpl()
            )
        })
    }
}