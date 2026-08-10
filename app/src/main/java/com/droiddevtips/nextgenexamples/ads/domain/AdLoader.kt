package com.droiddevtips.nextgenexamples.ads.domain

import android.content.Context
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd

interface AdLoader {

    val context: Context
    fun init(context: Context)
    fun preLoadBannerAd(adUnit: AdUnit)
    fun preLoadBannerAd(adUnit: AdUnit, result: (BannerAd?) -> Unit)
    fun loadBannerAd(key: String): BannerAd?
    fun removeCacheBannerAd(adUnit: AdUnit): Boolean
    fun removeAllCacheBannerAds(adUnits: List<AdUnit>)

}