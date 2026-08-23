package com.droiddevtips.nextgenexamples.ads.data.loader

import android.annotation.SuppressLint
import android.content.Context
import com.droiddevtips.nextgenexamples.ads.domain.AdLoader
import com.droiddevtips.nextgenexamples.ads.domain.BannerAdProvider
import com.droiddevtips.nextgenexamples.ads.domain.InterstitialAdProvider
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdPreloader
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAd
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAdPreloader
import java.util.concurrent.ConcurrentHashMap

/**
 * AdLoaderImpl keeps one shared, continuously-refilling ad pool per
 * preloadId, and BannerAdPreloader.start(...) must only ever be called
 * once per adUnit - it's an ongoing background session, not a one-shot
 * fetch. Every banner slot requesting the same adUnit while that session
 * is already running queues up here and is served in turn as onAdPreloaded
 * fires again, instead of each slot starting (and silently superseding)
 * its own session.
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@SuppressLint("StaticFieldLeak")
object AdLoaderImpl: AdLoader {

    private var bannerAdProvider: BannerAdProvider? = null
    private var interstitialAdProvider: InterstitialAdProvider? = null
    private val cachedBannerAd = ConcurrentHashMap<String, BannerAd>()
    private var interstitialServed: InterstitialAd? = null
    private var context: Context? = null

    override fun init(context: Context, bannerAdProvider: BannerAdProvider, interstitialAdProvider: InterstitialAdProvider) {
        this.context = context
        this.bannerAdProvider = bannerAdProvider
        this.interstitialAdProvider = interstitialAdProvider
    }

    override fun preLoadBannerAd(
        adUnit: AdUnit.BannerAd
    ) {

        requireNotNull(context) { "AdLoaderImpl.init() not called!" }
        requireNotNull(bannerAdProvider) { "AdLoaderImpl.init() not called!" }
        context?.let {
            bannerAdProvider?.preLoadBannerAd(context = it, adUnit = adUnit)
        }
    }

    override fun getBannerAd(preLoaderID: String): BannerAd? {

        if (bannerAdProvider == null)
            throw Exception("AdLoaderImpl.init() not called!")

        if (bannerAdProvider?.isAvailable(preLoaderID = preLoaderID) ?: false) {
            val newBannerAd = bannerAdProvider?.pollBannerAd(preLoaderID = preLoaderID)

            return newBannerAd?.let {
                destroyCacheAd(type = AdType.BannerAd(preloadID = preLoaderID))
                cachedBannerAd[preLoaderID] = it
                it
            }?: cachedBannerAd[preLoaderID]
        }

        return cachedBannerAd[preLoaderID]
    }

    override fun removeCacheBannerAd(adUnit: AdUnit): Boolean {
        destroyCacheAd(type = AdType.BannerAd(preloadID = adUnit.key))
        return BannerAdPreloader.destroy(adUnit.key)
    }

    override fun removeAllCacheBannerAds(adUnits: List<AdUnit>) {
        adUnits.forEach { bannerAd ->
            removeCacheBannerAd(adUnit = bannerAd)
        }
    }

    override fun preLoadInterstitialAd(adUnit: AdUnit.InterstitialAd, interstitialAdCount: (Int) -> Unit) {
        context?.let {
            interstitialAdProvider?.preLoadInterstitialAd(context = it, adUnit = adUnit) { adCount ->
                interstitialAdCount(adCount)
            }
        }
    }

    override fun getInterstitialAd(preLoaderID: String): InterstitialAd? {

        val preLoaderInterstitialAd = interstitialAdProvider?.pollInterstitialAd(preLoaderID = preLoaderID)

        this.interstitialServed = preLoaderInterstitialAd

        return preLoaderInterstitialAd
    }

    override fun destroyInterstitialAd(adUnit: AdUnit): Boolean = InterstitialAdPreloader.destroy(preloadId = adUnit.key)

    private fun destroyCacheAd(type: AdType) {

        when(type) {

            is AdType.BannerAd -> {
                cachedBannerAd.remove(type.preloadID)?.destroy()
            }

            is AdType.InterstitialAd -> {
                interstitialServed?.destroy()
                interstitialServed = null
            }
        }
    }
}