package com.droiddevtips.nextgenexamples.ads.domain

import android.content.Context
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd

interface BannerAdProvider {

    fun loadBannerAd(context: Context, adUnit: AdUnit, bannerAd: (BannerAd?) -> Unit)
    fun isAvailable(adUnit: AdUnit): Boolean
    fun pollBannerAd(adUnit: AdUnit): BannerAd?

}