package com.droiddevtips.nextgenexamples.ads.domain

import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAd
import kotlinx.coroutines.flow.StateFlow

/**
 * Central entry point for loading, showing, and managing ads across the application.
 *
 * [AdManager] provides a unified, SDK-agnostic API for interacting with ad units
 * abstracting away the underlying Google Mobile Ads (GMA) SDK so that callers in the domain and UI layers never
 * interact with SDK types directly.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
interface AdManager {

    val interstitialAdsAvailable: StateFlow<Int>

    fun init(adLoader: AdLoader)

    fun getBannerAd(preLoaderID:String): BannerAd?

    fun preLoadBannerAd(adUnit: AdUnit)
    fun clearAllCacheBannerAds(adUnits: List<AdUnit>)

    fun getInterstitialAd(preLoaderID:String): InterstitialAd?

    fun preLoadInterstitialAd(adUnit: AdUnit)
    fun destroyInterstitialAd(adUnit: AdUnit)

}