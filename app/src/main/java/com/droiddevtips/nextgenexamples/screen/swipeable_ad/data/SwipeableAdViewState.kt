package com.droiddevtips.nextgenexamples.screen.swipeable_ad.data

/**
 * The swipeable interstitial ad example view state
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
data class SwipeableAdViewState(
    val userScrollEnabled: Boolean = true,
    val showLoadingScreen: Boolean = true,
    val interceptOnBackPressed: Boolean = false,
    val items: List<GalleryItem> = emptyList()
)