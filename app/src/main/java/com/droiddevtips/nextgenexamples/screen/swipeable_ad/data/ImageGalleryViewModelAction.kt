@file:OptIn(ExperimentalApi::class)

package com.droiddevtips.nextgenexamples.screen.swipeable_ad.data

import com.google.android.libraries.ads.mobile.sdk.common.ExperimentalApi
import com.google.android.libraries.ads.mobile.sdk.swipeableinterstitial.SwipeableInterstitialAd

sealed interface ImageGalleryViewModelAction {
    data class HoldSwipeableAdOnScreen(val swipeableInterstitialAd: SwipeableInterstitialAd): ImageGalleryViewModelAction
}