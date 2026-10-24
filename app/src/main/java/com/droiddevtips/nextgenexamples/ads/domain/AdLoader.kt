@file:OptIn(ExperimentalApi::class)

package com.droiddevtips.nextgenexamples.ads.domain

import android.content.Context
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.common.ExperimentalApi
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAd
import com.google.android.libraries.ads.mobile.sdk.pip.PictureInPictureAd
import com.google.android.libraries.ads.mobile.sdk.swipeableinterstitial.SwipeableInterstitialAd

/**
 * Loads ads for a given [AdUnit], abstracting away the underlying ad SDK.
 *
 * Implementations are responsible for requesting an ad from the ad network,
 * translating SDK-specific callbacks or listeners into a load results
 * as domain-level types rather than SDK types.
 *
 * An [AdLoader] is intentionally unaware of caching, preloading, or display
 * concerns — those are handled by higher-level components such as
 * BannerAdPreloader or [AdManager]. This keeps ad-loading logic isolated
 * and testable independently of ad lifecycle and presentation.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
interface AdLoader {

    fun init(context: Context, bannerAdProvider: BannerAdProvider, interstitialAdProvider: InterstitialAdProvider, swipeableInterstitialAdProvider: SwipeableInterstitialAdProvider, pictureInPictureAdProvider: PictureInPictureAdProvider)
    fun preLoadBannerAd(adUnit: AdUnit.BannerAd)
    fun getBannerAd(preLoaderID: String): BannerAd?
    fun removeCacheBannerAd(adUnit: AdUnit): Boolean
    fun removeAllCacheBannerAds(adUnits: List<AdUnit>)
    fun preLoadInterstitialAd(adUnit: AdUnit.InterstitialAd, interstitialAdCount: (Int) -> Unit)
    fun getInterstitialAd(preLoaderID: String): InterstitialAd?
    fun destroyInterstitialAd(adUnit: AdUnit): Boolean
    suspend fun loadSwipeableInterstitialAd(adUnit: AdUnit): SwipeableInterstitialAd?
    suspend fun loadPictureInPictureAd(adUnit: AdUnit): PictureInPictureAd?

}