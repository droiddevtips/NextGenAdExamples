package com.droiddevtips.nextgenexamples.ads.data.preloader

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.droiddevtips.nextgenexamples.ads.data.provider.BannerAdProviderImpl
import com.droiddevtips.nextgenexamples.ads.domain.AdLoader
import com.droiddevtips.nextgenexamples.ads.domain.BannerAdProvider
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdPreloader
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.set

@SuppressLint("StaticFieldLeak")
object AdLoaderImpl: AdLoader, BannerAdProvider by BannerAdProviderImpl() {

    private val cachedBannerAd = ConcurrentHashMap<String, BannerAd>()

    private var _context: Context? = null
    override val context: Context
        get() = _context ?: error(message = "AdLoaderImpl.init() not called!")
    
    override fun init(context: Context) {
        this._context = context.applicationContext
    }

    override fun preLoadBannerAd(
        adUnit: AdUnit
    ) {
        loadBannerAd(context = context, adUnit = adUnit, bannerAd = { bannerAd ->
            bannerAd?.let {
                cachedBannerAd[adUnit.key] = it
            }
        })
    }

    override fun preLoadBannerAd(
        adUnit: AdUnit,
        result: (BannerAd?) -> Unit
    ) {
        loadBannerAd(context = context, adUnit = adUnit, bannerAd = { bannerAd ->
            bannerAd?.let {
                cachedBannerAd[adUnit.key] = it
            }
        })
    }

    override fun loadBannerAd(key: String): BannerAd? {

        val bannerAd = cachedBannerAd[key]
        Log.i("TAG12","Banner ad ($key) -> $bannerAd")
        return bannerAd

    }

    override fun removeCacheBannerAd(adUnit: AdUnit): Boolean = BannerAdPreloader.destroy(adUnit.key)

    override fun removeAllCacheBannerAds(adUnits: List<AdUnit>) {
        adUnits.forEach { bannerAd ->
            removeCacheBannerAd(adUnit = bannerAd)
        }
    }
}