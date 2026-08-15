package com.droiddevtips.nextgenexamples.ads.domain

import android.content.Context
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd

/**
 * Loads ads for a given [AdUnit], abstracting away the underlying ad SDK.
 *
 * Implementations are responsible for requesting an ad from the ad network,
 * translating SDK-specific callbacks or listeners into a load results
 * as domain-level types rather than SDK types.
 *
 * An [AdLoader] is intentionally unaware of caching, preloading, or display
 * concerns — those are handled by higher-level components such as
 * [BannerAdPreloader] or [AdManager]. This keeps ad-loading logic isolated
 * and testable independently of ad lifecycle and presentation.
 */
interface AdLoader {
    fun init(context: Context, bannerAdProvider: BannerAdProvider)
    fun preLoadBannerAd(adUnit: AdUnit)
    fun getBannerAd(preLoaderID: String): BannerAd?
    fun removeCacheBannerAd(adUnit: AdUnit): Boolean
    fun removeAllCacheBannerAds(adUnits: List<AdUnit>)
}