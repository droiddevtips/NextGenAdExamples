package com.droiddevtips.nextgenexamples.ads.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents a distinct type of ad that can be requested and displayed within the app.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Parcelize
sealed class AdUnit(val key: String, val adUnit: String): Parcelable {

    data class BannerAd(private val _key: String) : AdUnit(key = "banner_ad_$_key", adUnit = "ca-app-pub-3940256099942544/9214589741")
    data object InterstitialAd: AdUnit(key = "interstitial_ad_1", adUnit = "ca-app-pub-3940256099942544/1033173712")
}