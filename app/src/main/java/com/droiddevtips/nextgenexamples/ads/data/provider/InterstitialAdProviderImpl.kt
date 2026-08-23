package com.droiddevtips.nextgenexamples.ads.data.provider

import android.content.Context
import com.droiddevtips.nextgenexamples.ads.domain.InterstitialAdProvider
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.droiddevtips.nextgenexamples.logging.data.LoggerImpl
import com.droiddevtips.nextgenexamples.logging.domain.LogLevel
import com.droiddevtips.nextgenexamples.logging.domain.Logger
import com.google.android.libraries.ads.mobile.sdk.common.AdRequest
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import com.google.android.libraries.ads.mobile.sdk.common.PreloadCallback
import com.google.android.libraries.ads.mobile.sdk.common.PreloadConfiguration
import com.google.android.libraries.ads.mobile.sdk.common.ResponseInfo
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAd
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAdPreloader

/**
 * Default implementation of [InterstitialAdProvider] responsible for loading interstitial ads
 * through the underlying ad SDK.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class InterstitialAdProviderImpl : InterstitialAdProvider, Logger by LoggerImpl(InterstitialAdProviderImpl::class.java.simpleName) {

    override fun preLoadInterstitialAd(
        context: Context,
        adUnit: AdUnit,
        interstitialAdCount: (Int) -> Unit
    ) {
        log(message = "loading interstitial ad unit '${adUnit.adUnit}'........")

        val preloadCallback = object : PreloadCallback {

            override fun onAdFailedToPreload(preloadId: String, adError: LoadAdError) {
                super.onAdFailedToPreload(preloadId, adError)
                interstitialAdCount(getNumAvailableInterstitialAds(preloadID = preloadId))
                log(
                    level = LogLevel.Error,
                    message = "Unable to preloading interstitial ad '$preloadId' with error: ${adError.message}"
                )
            }

            override fun onAdsExhausted(preloadId: String) {
                super.onAdsExhausted(preloadId)
                interstitialAdCount(getNumAvailableInterstitialAds(preloadID = preloadId))
                log(
                    level = LogLevel.Warning,
                    message = "No interstitial ad available for interstitial ad '$preloadId'!"
                )
            }

            override fun onAdPreloaded(preloadId: String, responseInfo: ResponseInfo) {
                super.onAdPreloaded(preloadId, responseInfo)
                interstitialAdCount(getNumAvailableInterstitialAds(preloadID = preloadId))
                log(message = "Interstitial ad with ID '$preloadId' successfully preloaded")
            }
        }

        val adRequest = AdRequest.Builder(adUnitId = adUnit.adUnit).build()
        val preload = PreloadConfiguration(request = adRequest, bufferSize = 1)
        InterstitialAdPreloader.start(preloadId = adUnit.key, preloadConfiguration = preload, preloadCallback = preloadCallback)
    }

    override fun isInterstitialAdAvailable(preLoaderID: String): Boolean {
        val isAvailable = InterstitialAdPreloader.isAdAvailable(preloadId = preLoaderID)
        log(message = "Interstitial ad availability: ${if (isAvailable) "'Yes'" else "'No'"} for interstitial ad with preload ID: '$preLoaderID' number of ads in buffer pool: ${InterstitialAdPreloader.getNumAdsAvailable(preloadId = preLoaderID)}")
        return isAvailable
    }

    override fun pollInterstitialAd(preLoaderID: String): InterstitialAd? {
        val interstitialAd = InterstitialAdPreloader.pollAd(preloadId = preLoaderID)
        log(message = "Interstitial ad '$preLoaderID' from the pre loader queue: $interstitialAd")
        return interstitialAd
    }

    private fun getNumAvailableInterstitialAds(preloadID:String): Int = InterstitialAdPreloader.getNumAdsAvailable(preloadId = preloadID)
}
