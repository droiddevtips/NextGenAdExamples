@file:OptIn(ExperimentalApi::class)

package com.droiddevtips.nextgenexamples.screen.swipeable_ad.data

import com.google.android.libraries.ads.mobile.sdk.common.ExperimentalApi
import com.google.android.libraries.ads.mobile.sdk.swipeableinterstitial.SwipeableInterstitialAd

/**
 * The gallery item data model
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed class GalleryItem {
    data class Image(val image: Int, val credit: String) : GalleryItem()
    data class Ad(val swipeableInterstitialAd: SwipeableInterstitialAd) : GalleryItem()
}