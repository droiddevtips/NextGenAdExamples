package com.droiddevtips.nextgenexamples.ads.data.provider

import android.content.Context
import com.droiddevtips.nextgenexamples.ads.domain.BannerAdProvider
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.droiddevtips.nextgenexamples.logging.data.LoggerImpl
import com.droiddevtips.nextgenexamples.logging.domain.LogLevel
import com.droiddevtips.nextgenexamples.logging.domain.Logger
import com.google.android.libraries.ads.mobile.sdk.banner.AdSize
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdPreloader
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdRequest
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import com.google.android.libraries.ads.mobile.sdk.common.PreloadCallback
import com.google.android.libraries.ads.mobile.sdk.common.PreloadConfiguration
import com.google.android.libraries.ads.mobile.sdk.common.ResponseInfo

/**
 * Default implementation of [BannerAdProvider] responsible for loading banner ads
 * through the underlying ad SDK.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class BannerAdProviderImpl : BannerAdProvider, Logger by LoggerImpl(BannerAdProviderImpl::class.java.simpleName) {

    override fun preLoadBannerAd(
        context: Context,
        adUnit: AdUnit
    ) {
        log(message = "loading banner ad unit '${adUnit.adUnit}'........")

        val preloadCallback = object : PreloadCallback {

            override fun onAdFailedToPreload(preloadId: String, adError: LoadAdError) {
                super.onAdFailedToPreload(preloadId, adError)
                log(
                    level = LogLevel.Error,
                    message = "Unable to preloading banner ad '$preloadId' with error: ${adError.message}"
                )
            }

            override fun onAdsExhausted(preloadId: String) {
                super.onAdsExhausted(preloadId)
                log(
                    level = LogLevel.Warning,
                    message = "No banner ad available for banner ad '$preloadId'!"
                )
            }

            override fun onAdPreloaded(preloadId: String, responseInfo: ResponseInfo) {
                super.onAdPreloaded(preloadId, responseInfo)
                log(message = "Banner ad with ID '$preloadId' successfully preloaded")
            }
        }

        val adSize = AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(context, 320)
        val adRequest = BannerAdRequest.Builder(adUnitId = adUnit.adUnit, adSize).build()
        val preload = PreloadConfiguration(request = adRequest, bufferSize = 1)
        BannerAdPreloader.start(preloadId = adUnit.key, preloadConfiguration = preload, preloadCallback = preloadCallback)
    }

    override fun isAvailable(preLoaderID: String): Boolean {
        val isAvailable = BannerAdPreloader.isAdAvailable(preloadId = preLoaderID)
        log(message = "Banner ad availability: ${if (isAvailable) "'Yes'" else "'No'"} for banner ad with preload ID: '$preLoaderID' number of ads in buffer pool: ${BannerAdPreloader.getNumAdsAvailable(preloadId = preLoaderID)}")
        return isAvailable
    }

    override fun pollBannerAd(preLoaderID: String): BannerAd? {
        val bannerAd = BannerAdPreloader.pollAd(preloadId = preLoaderID)
        log(message = "Banner ad '$preLoaderID' from the pre loader queue: $bannerAd")
        return bannerAd
    }
}