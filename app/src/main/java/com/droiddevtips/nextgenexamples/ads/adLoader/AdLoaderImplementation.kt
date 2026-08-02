package com.droiddevtips.nextgenexamples.ads.adLoader

import android.content.Context
import com.google.android.libraries.ads.mobile.sdk.banner.AdSize
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdPreloader
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdRequest
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import com.google.android.libraries.ads.mobile.sdk.common.PreloadCallback
import com.google.android.libraries.ads.mobile.sdk.common.PreloadConfiguration
import com.google.android.libraries.ads.mobile.sdk.common.ResponseInfo
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

object AdLoaderImplementation: AdLoader {

    override suspend fun loadBannerAd(context: Context, adUnit: String): BannerAd? = suspendCancellableCoroutine { continuation ->

        val preloadCallback = object: PreloadCallback {

            override fun onAdFailedToPreload(preloadId: String, adError: LoadAdError) {
                super.onAdFailedToPreload(preloadId, adError)
                println("Banner ad preload failed to load, cause: ${adError.message}")
                continuation.resume(null)
            }

            override fun onAdsExhausted(preloadId: String) {
                super.onAdsExhausted(preloadId)
                println("Banner ad preload is not available for ID: $preloadId")
                continuation.resume(null)
            }

            override fun onAdPreloaded(preloadId: String, responseInfo: ResponseInfo) {
                super.onAdPreloaded(preloadId, responseInfo)
                continuation.resume(BannerAdPreloader.pollAd(preloadId = adUnit))
            }
        }

        val adSize = AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(context, 320)
        val adRequest = BannerAdRequest.Builder(adUnitId = adUnit, adSize).build()
        val preload = PreloadConfiguration(adRequest)
        BannerAdPreloader.start(preloadId = adUnit, preloadConfiguration = preload, preloadCallback = preloadCallback)
    }

}