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

class BannerAdProviderImpl : BannerAdProvider, Logger by LoggerImpl() {

    override fun loadBannerAd(
        context: Context,
        adUnit: AdUnit,
        bannerAd: (BannerAd?) -> Unit
    ) {
        log(message = "loading banner ad unit '${adUnit.adUnit}'........")

        val preloadCallback = object : PreloadCallback {

            override fun onAdFailedToPreload(preloadId: String, adError: LoadAdError) {
                super.onAdFailedToPreload(preloadId, adError)
                log(
                    level = LogLevel.Error,
                    message = "Unable to preloading banner ad '$preloadId' with error: ${adError.message}"
                )
                bannerAd(null)
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
                bannerAd(BannerAdPreloader.pollAd(preloadId))
            }
        }

        val adSize = AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(context, 320)
        val adRequest = BannerAdRequest.Builder(adUnitId = adUnit.adUnit, adSize).build()
        val preload = PreloadConfiguration(adRequest)
        BannerAdPreloader.start(preloadId = adUnit.key, preloadConfiguration = preload, preloadCallback = preloadCallback)
    }

    override fun isAvailable(adUnit: AdUnit): Boolean {
        val isAvailable = BannerAdPreloader.isAdAvailable(adUnit.key)
        log(message = "Banner ad availability: $isAvailable")
        return isAvailable
    }

    override fun pollBannerAd(adUnit: AdUnit): BannerAd? {
        val bannerAd = BannerAdPreloader.pollAd(adUnit.key)
        log(message = "Banner ad '${adUnit.key}' from the pre loader queue: $bannerAd")
        return bannerAd
    }
}