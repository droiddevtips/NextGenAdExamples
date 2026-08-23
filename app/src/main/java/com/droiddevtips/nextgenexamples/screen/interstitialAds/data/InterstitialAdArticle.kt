package com.droiddevtips.nextgenexamples.screen.interstitialAds.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The interstitial ad example article data model
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Parcelize
data class InterstitialAdArticle(
    val key: Int,
    val featureImage: Int,
    val title: String,
    val description: String,
    val flag: Int
) : Parcelable