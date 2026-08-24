package com.droiddevtips.nextgenexamples.screen.interstitialAds.ui

import android.app.Activity
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.droiddevtips.nextgenexamples.logging.data.LoggerImpl
import com.droiddevtips.nextgenexamples.logging.domain.LogLevel
import com.droiddevtips.nextgenexamples.logging.domain.Logger
import com.google.android.libraries.ads.mobile.sdk.common.FullScreenContentError
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAdEventCallback
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAdPreloader

object InterstitialLauncher:  Logger by LoggerImpl(className = InterstitialLauncher::class.java.simpleName) {

    fun launchInterstitial(activity: Activity?,onCompleted: () -> Unit) {

        if (activity == null)
            onCompleted()

        val interstitialAd = InterstitialAdPreloader.pollAd(AdUnit.InterstitialAd.key)

        if (interstitialAd == null)
            onCompleted()

        interstitialAd?.adEventCallback =
            object : InterstitialAdEventCallback {
                override fun onAdShowedFullScreenContent() {
                    log(level = LogLevel.Info,"Interstitial ad showed.")
                }

                override fun onAdDismissedFullScreenContent() {
                    log(level = LogLevel.Info,"Interstitial ad dismissed.")
                    onCompleted()
                }

                override fun onAdFailedToShowFullScreenContent(
                    fullScreenContentError: FullScreenContentError
                ) {
                    log(level = LogLevel.Info,"Interstitial ad failed to show.")
                    onCompleted()
                }

                override fun onAdImpression() {
                    log(level = LogLevel.Info,"Interstitial ad recorded an impression.")
                }

                override fun onAdClicked() {
                    log(level = LogLevel.Info,"Interstitial ad recorded a click.")
                }
            }

        activity?.let {
            interstitialAd?.show(activity)
        }
    }
}