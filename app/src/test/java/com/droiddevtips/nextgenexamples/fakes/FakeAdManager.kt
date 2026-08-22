package com.droiddevtips.nextgenexamples.fakes

import com.droiddevtips.nextgenexamples.ads.domain.AdLoader
import com.droiddevtips.nextgenexamples.ads.domain.AdManager
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd

/**
 * In-memory [AdManager] fake for unit testing view models that depend on it.
 */
class FakeAdManager : AdManager {

    val preLoadedAdUnits = mutableListOf<AdUnit>()
    val clearedAdUnitBatches = mutableListOf<List<AdUnit>>()

    override fun init(adLoader: AdLoader) = Unit

    override fun getBannerAd(preLoaderID: String): BannerAd? = null

    override fun preLoadBannerAd(adUnit: AdUnit) {
        preLoadedAdUnits.add(adUnit)
    }

    override fun clearAllCacheBannerAds(adUnits: List<AdUnit>) {
        clearedAdUnitBatches.add(adUnits)
    }
}
