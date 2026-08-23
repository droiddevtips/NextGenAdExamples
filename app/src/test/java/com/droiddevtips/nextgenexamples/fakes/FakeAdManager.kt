package com.droiddevtips.nextgenexamples.fakes

import com.droiddevtips.nextgenexamples.ads.domain.AdLoader
import com.droiddevtips.nextgenexamples.ads.domain.AdManager
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAd
import kotlinx.coroutines.flow.StateFlow

/**
 * In-memory [AdManager] fake for unit testing view models that depend on it.
 */
class FakeAdManager : AdManager {

    val preLoadedAdUnits = mutableListOf<AdUnit>()
    val clearedAdUnitBatches = mutableListOf<List<AdUnit>>()
    override val interstitialAdsAvailable: StateFlow<Int>
        get() = TODO("Not yet implemented")

    override fun init(adLoader: AdLoader) = Unit

    override fun getBannerAd(preLoaderID: String): BannerAd? = null

    override fun preLoadBannerAd(adUnit: AdUnit) {
        preLoadedAdUnits.add(adUnit)
    }

    override fun clearAllCacheBannerAds(adUnits: List<AdUnit>) {
        clearedAdUnitBatches.add(adUnits)
    }

    override fun getInterstitialAd(preLoaderID: String): InterstitialAd? {
        TODO("Not yet implemented")
    }

    override fun preLoadInterstitialAd(adUnit: AdUnit) {
        TODO("Not yet implemented")
    }

    override fun destroyInterstitialAd(adUnit: AdUnit) {
        TODO("Not yet implemented")
    }
}
