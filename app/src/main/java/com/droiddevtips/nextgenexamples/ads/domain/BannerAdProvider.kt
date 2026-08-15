package com.droiddevtips.nextgenexamples.ads.domain

import android.content.Context
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd

interface BannerAdProvider {

    fun preLoadBannerAd(context: Context, adUnit: AdUnit)
    fun isAvailable(preLoaderID: String): Boolean
    fun pollBannerAd(preLoaderID: String): BannerAd?

}