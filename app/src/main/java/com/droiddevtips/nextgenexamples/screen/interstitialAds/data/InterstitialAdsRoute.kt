package com.droiddevtips.nextgenexamples.screen.interstitialAds.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * The available routes within the interstitial ads example nav host.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
sealed interface InterstitialAdsRoute {
    @Serializable
    data object List : InterstitialAdsRoute

    @Serializable
    data class Detail(val articleKey: Int) : InterstitialAdsRoute
}