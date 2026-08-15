package com.droiddevtips.nextgenexamples.ads.domain

import android.content.Context
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd

interface AdLoader {
    fun init(context: Context, bannerAdProvider: BannerAdProvider)
    fun preLoadBannerAd(adUnit: AdUnit)
    fun getBannerAd(preLoaderID: String): BannerAd?
    fun removeCacheBannerAd(adUnit: AdUnit): Boolean
    fun removeAllCacheBannerAds(adUnits: List<AdUnit>)
}