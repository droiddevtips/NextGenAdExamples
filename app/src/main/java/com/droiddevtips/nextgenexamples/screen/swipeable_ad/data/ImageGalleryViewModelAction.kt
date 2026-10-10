@file:OptIn(ExperimentalApi::class)

package com.droiddevtips.nextgenexamples.screen.swipeable_ad.data

import androidx.lifecycle.Lifecycle
import com.droiddevtips.nextgenexamples.logging.domain.LogLevel
import com.google.android.libraries.ads.mobile.sdk.common.ExperimentalApi
import com.google.android.libraries.ads.mobile.sdk.swipeableinterstitial.SwipeableInterstitialAd

/**
 * The available image gallery view model actions
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed interface ImageGalleryViewModelAction {
    data class LifeCycle(val event: Lifecycle.Event) : ImageGalleryViewModelAction
    data class Log(val level: LogLevel, val message: String) : ImageGalleryViewModelAction
    data class HoldSwipeableAdOnScreen(val swipeableInterstitialAd: SwipeableInterstitialAd) :
        ImageGalleryViewModelAction

    data class OnPageIndexChanged(val index: Int) : ImageGalleryViewModelAction
    data object OnCleared : ImageGalleryViewModelAction
}