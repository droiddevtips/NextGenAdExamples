@file:OptIn(ExperimentalApi::class)

package com.droiddevtips.nextgenexamples.screen.swipeable_ad

import com.google.android.libraries.ads.mobile.sdk.common.ExperimentalApi
import com.google.android.libraries.ads.mobile.sdk.swipeableinterstitial.SwipeableInterstitialAd

sealed class GalleryItem {
    data class Image(val image: Int, val credit: String) : GalleryItem()
    data class Ad(val swipeAd: SwipeableInterstitialAd, val image: Int, val credit: String) : GalleryItem()
}