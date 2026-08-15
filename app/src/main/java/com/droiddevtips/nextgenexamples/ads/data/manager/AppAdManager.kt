package com.droiddevtips.nextgenexamples.ads.data.manager

import com.droiddevtips.nextgenexamples.ads.domain.AdLoader
import com.droiddevtips.nextgenexamples.ads.domain.AdManager
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.droiddevtips.nextgenexamples.googleAdsConsentManager.GoogleAdsConsentManager
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd

/**
 * Singleton implementation of [AdManager] responsible for managing the lifecycle of
 * banner ads throughout the application.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
object AppAdManager: AdManager {

    private var adLoader: AdLoader? = null

    val APP_ID = "ca-app-pub-3940256099942544~3347511713"

    override fun init(adLoader: AdLoader) {
        this.adLoader = adLoader
    }

    override fun getBannerAd(
        preLoaderID: String
    ): BannerAd? {

        checkIfAdLoaderIsInitialized()

        if (!GoogleAdsConsentManager.canRequestAds()) {
            return null
        }

        return adLoader?.getBannerAd(preLoaderID = preLoaderID)
    }

    override fun preLoadBannerAd(adUnit: AdUnit) {
        checkIfAdLoaderIsInitialized()
        adLoader?.preLoadBannerAd(adUnit = adUnit)
    }

    override fun clearAllCacheBannerAds(adUnits: List<AdUnit>) {
        checkIfAdLoaderIsInitialized()
        adLoader?.removeAllCacheBannerAds(adUnits)
    }

    private fun checkIfAdLoaderIsInitialized() {
        requireNotNull(this.adLoader) { "AdManager.init() not called!" }
    }
}