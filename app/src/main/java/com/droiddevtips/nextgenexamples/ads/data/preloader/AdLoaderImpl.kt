package com.droiddevtips.nextgenexamples.ads.data.preloader

import android.annotation.SuppressLint
import android.content.Context
import com.droiddevtips.nextgenexamples.ads.domain.AdLoader
import com.droiddevtips.nextgenexamples.ads.domain.BannerAdProvider
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdPreloader
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
    private val cachedBannerAd = ConcurrentHashMap<String, BannerAd>()
    private var context: Context? = null

    override fun init(context: Context, bannerAdProvider: BannerAdProvider) {
        this.context = context
        this.bannerAdProvider = bannerAdProvider
    }

    override fun preLoadBannerAd(
        adUnit: AdUnit
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
                destroyCacheBannerAd(preloadID = preLoaderID)
                updateCacheBannerAd(preloadID = preLoaderID, bannerAd = it)
                it
            }?: retrieveCacheBannerAd(preloadID = preLoaderID)
        }

        return retrieveCacheBannerAd(preloadID = preLoaderID)
    }

    override fun removeCacheBannerAd(adUnit: AdUnit): Boolean {
        destroyCacheBannerAd(preloadID = adUnit.key)
        removeCacheBannerAd(preloadID = adUnit.key)
        return BannerAdPreloader.destroy(adUnit.key)
    }

    override fun removeAllCacheBannerAds(adUnits: List<AdUnit>) {
        adUnits.forEach { bannerAd ->
            removeCacheBannerAd(adUnit = bannerAd)
        }
    }

    private fun destroyCacheBannerAd(preloadID:String) {
        cachedBannerAd[preloadID]?.destroy()
    }

    private fun removeCacheBannerAd(preloadID:String) {
        cachedBannerAd.remove(preloadID)
    }

    private fun updateCacheBannerAd(preloadID:String, bannerAd: BannerAd) {
        cachedBannerAd[preloadID] = bannerAd
    }

    private fun retrieveCacheBannerAd(preloadID:String): BannerAd? {
        return cachedBannerAd[preloadID]
    }
}