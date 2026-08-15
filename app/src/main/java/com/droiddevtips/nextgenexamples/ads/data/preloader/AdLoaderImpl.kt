package com.droiddevtips.nextgenexamples.ads.data.preloader

import android.annotation.SuppressLint
import android.content.Context
import com.droiddevtips.nextgenexamples.ads.domain.AdLoader
import com.droiddevtips.nextgenexamples.ads.domain.BannerAdProvider
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdPreloader
import java.util.concurrent.ConcurrentHashMap

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
                cachedBannerAd[preLoaderID]?.destroy()
                cachedBannerAd[preLoaderID] = it
                it
            }?: cachedBannerAd[preLoaderID]
        }

        return cachedBannerAd[preLoaderID]
    }

    override fun removeCacheBannerAd(adUnit: AdUnit): Boolean {
        cachedBannerAd[adUnit.key]?.destroy()
        cachedBannerAd.remove(adUnit.key)
        return BannerAdPreloader.destroy(adUnit.key)
    }

    override fun removeAllCacheBannerAds(adUnits: List<AdUnit>) {
        adUnits.forEach { bannerAd ->
            removeCacheBannerAd(adUnit = bannerAd)
        }
    }
}