@file:OptIn(ExperimentalApi::class)

package com.droiddevtips.nextgenexamples.ads.data.provider

import com.droiddevtips.nextgenexamples.ads.domain.PictureInPictureAdProvider
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.droiddevtips.nextgenexamples.logging.data.LoggerImpl
import com.droiddevtips.nextgenexamples.logging.domain.LogLevel
import com.droiddevtips.nextgenexamples.logging.domain.Logger
import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback
import com.google.android.libraries.ads.mobile.sdk.common.ExperimentalApi
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import com.google.android.libraries.ads.mobile.sdk.pip.PictureInPictureAd
import com.google.android.libraries.ads.mobile.sdk.pip.PictureInPictureAdRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Default 'PictureInPictureAdProvider' implementation that loads a 'PictureInPictureAd' for the given ad unit,
 * returning `null` if the ad fails to load.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class PictureInPictureAdProviderImpl : PictureInPictureAdProvider, Logger by LoggerImpl(PictureInPictureAdProviderImpl::class.java.simpleName) {

    override suspend fun loadPictureInPictureAd(adUnit: AdUnit): PictureInPictureAd? =
        suspendCancellableCoroutine { continuation ->

            try {

                val request = PictureInPictureAdRequest.Builder(adUnitId = adUnit.adUnit).build()

                PictureInPictureAd.load(
                    adRequest = request,
                    adLoadCallback = object : AdLoadCallback<PictureInPictureAd> {
                        override fun onAdLoaded(ad: PictureInPictureAd) {
                            super.onAdLoaded(ad)
                            log(level = LogLevel.Info, message = "Picture in Picture ad successfully loaded")
                            continuation.resume(ad)
                        }

                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            super.onAdFailedToLoad(adError)
                            log(level = LogLevel.Info, message = "Failed to load Picture in Picture ad, cause: ${adError.message}")
                            continuation.resume(null)
                        }
                    })

            } catch (e: Exception) {
                log(level = LogLevel.Info, message = "Failed to load Picture in Picture ad, cause: ${e.message}")
                continuation.resume(null)
            }
        }
}