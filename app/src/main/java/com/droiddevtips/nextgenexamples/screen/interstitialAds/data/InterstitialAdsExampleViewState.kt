package com.droiddevtips.nextgenexamples.screen.interstitialAds.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The interstitial ad example view state data model
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Parcelize
data class InterstitialAdsExampleViewState(
    val isLoading: Boolean = true,
    val articles: List<InterstitialAdArticle> = emptyList(),
    val interstitialAvailable: Int = 0
) : Parcelable