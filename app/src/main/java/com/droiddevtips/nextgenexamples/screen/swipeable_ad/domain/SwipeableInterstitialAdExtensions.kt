package com.droiddevtips.nextgenexamples.screen.swipeable_ad.domain

import com.droiddevtips.nextgenexamples.logging.domain.LogLevel
import com.droiddevtips.nextgenexamples.screen.swipeable_ad.data.ImageGalleryViewModelAction
import com.google.android.libraries.ads.mobile.sdk.common.ExperimentalApi
import com.google.android.libraries.ads.mobile.sdk.common.FullScreenContentError
import com.google.android.libraries.ads.mobile.sdk.swipeableinterstitial.SwipeableInterstitialAd
import com.google.android.libraries.ads.mobile.sdk.swipeableinterstitial.SwipeableInterstitialAdEventCallback

/**
 * The swipeable interstitial ad extensions
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@OptIn(ExperimentalApi::class)
fun SwipeableInterstitialAd.addEventCallback(action: (ImageGalleryViewModelAction) -> Unit) {

    adEventCallback = object: SwipeableInterstitialAdEventCallback {

        override fun onAdClicked() {
            super.onAdClicked()
            action(ImageGalleryViewModelAction.Log(level = LogLevel.Info, message = "Ad Clicked"))
        }

        override fun onAdDismissedFullScreenContent() {
            super.onAdDismissedFullScreenContent()
            action(ImageGalleryViewModelAction.Log(level = LogLevel.Info, message = "Ad dismissed full screen content"))
        }

        override fun onAdFailedToShowFullScreenContent(fullScreenContentError: FullScreenContentError) {
            super.onAdFailedToShowFullScreenContent(fullScreenContentError)
            action(ImageGalleryViewModelAction.Log(level = LogLevel.Info, message = "Ad failed to show full screen content"))
        }

        override fun onAdImpression() {
            super.onAdImpression()
            action(ImageGalleryViewModelAction.Log(level = LogLevel.Info, message = "Ad impression!"))
        }
    }
}