@file:OptIn(ExperimentalApi::class)

package com.droiddevtips.nextgenexamples.ads.data.provider

import com.droiddevtips.nextgenexamples.ads.domain.SwipeableInterstitialAdProvider
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.droiddevtips.nextgenexamples.logging.data.LoggerImpl
import com.droiddevtips.nextgenexamples.logging.domain.LogLevel
import com.droiddevtips.nextgenexamples.logging.domain.Logger
import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback
import com.google.android.libraries.ads.mobile.sdk.common.ExperimentalApi
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import com.google.android.libraries.ads.mobile.sdk.swipeableinterstitial.SwipeableInterstitialAd
import com.google.android.libraries.ads.mobile.sdk.swipeableinterstitial.SwipeableInterstitialAdRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class SwipeableInterstitialAdProviderImpl: SwipeableInterstitialAdProvider, Logger by LoggerImpl(SwipeableInterstitialAdProviderImpl::class.java.simpleName)  {

    companion object {
        const val SCREEN_HOLD_DURATION_IN_SECONDS = 3
    }

    override suspend fun loadSwipeableAd(adUnit: AdUnit): SwipeableInterstitialAd? {

        return suspendCancellableCoroutine { continuation ->

            val requestBuilder = SwipeableInterstitialAdRequest.Builder(adUnit.adUnit)
                .setMaxScreenHoldDurationSeconds(SCREEN_HOLD_DURATION_IN_SECONDS)
            SwipeableInterstitialAd.load(
                requestBuilder.build(),
                object : AdLoadCallback<SwipeableInterstitialAd> {
                    override fun onAdLoaded(ad: SwipeableInterstitialAd) {
                        super.onAdLoaded(ad)
                        log(level = LogLevel.Info, message = "Swipeable ad (${ad.adUnitId}) loaded!")
                        if (continuation.isActive) {
                            continuation.resume(ad)
                        }
                    }

                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        super.onAdFailedToLoad(adError)
                        log(level = LogLevel.Info, message = "Error swipeable ad, cause: ${adError.message}")
                        if (continuation.isActive) {
                            continuation.resume(null)
                        }
                    }
                }
            )
        }
    }
}