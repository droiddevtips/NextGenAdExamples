package com.droiddevtips.nextgenexamples.ads.data.manager

import com.droiddevtips.nextgenexamples.ads.domain.AdLoader
import com.droiddevtips.nextgenexamples.ads.domain.AdManager
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.droiddevtips.nextgenexamples.googleAdsConsentManager.GoogleAdsConsentManager
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAd
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAdPreloader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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

    private val _interstitialAdsAvailable = MutableStateFlow(0)
    override val interstitialAdsAvailable: StateFlow<Int>
        get() = _interstitialAdsAvailable.asStateFlow()

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
        require(adUnit is AdUnit.BannerAd) { "adUnit must be an AdUnit.BannerAd" }
        adLoader?.preLoadBannerAd(adUnit = adUnit)
    }

    override fun clearAllCacheBannerAds(adUnits: List<AdUnit>) {
        checkIfAdLoaderIsInitialized()
        adLoader?.removeAllCacheBannerAds(adUnits)
    }

    override fun getInterstitialAd(preLoaderID: String): InterstitialAd? {

        checkIfAdLoaderIsInitialized()

        if (!GoogleAdsConsentManager.canRequestAds()) {
            return null
        }

        return adLoader?.getInterstitialAd(preLoaderID = preLoaderID)
    }

    override fun preLoadInterstitialAd(adUnit: AdUnit) {
        checkIfAdLoaderIsInitialized()
        require(adUnit is AdUnit.InterstitialAd) { "adUnit must be an AdUnit.InterstitialAd" }
        adLoader?.preLoadInterstitialAd(adUnit = adUnit) { adCount ->
            _interstitialAdsAvailable.value = adCount
        }
    }

    override fun destroyInterstitialAd(adUnit: AdUnit) {
        checkIfAdLoaderIsInitialized()
        adLoader?.destroyInterstitialAd(adUnit = adUnit)
        _interstitialAdsAvailable.value = InterstitialAdPreloader.getNumAdsAvailable(preloadId = adUnit.key)
    }

    private fun checkIfAdLoaderIsInitialized() {
        requireNotNull(this.adLoader) { "AdManager.init() not called!" }
    }
}